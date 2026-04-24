import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import data.PlanCatalog;
import models.MobilePlan;
import models.Operator;
import services.SaleService;
import context.SaleOrder;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Main Application Server
 * Launches a lightweight HTTP server to serve the web frontend and REST API.
 * The State Pattern for mobile plan sales is demonstrated through the web UI.
 */
public class Main {

    private static final int PORT = 8080;
    private static final SaleService saleService = new SaleService();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/", Main::serveIndex);
        server.createContext("/api/catalog", Main::handleCatalog);
        server.createContext("/api/orders", Main::handleOrders);
        server.createContext("/api/orders/create", Main::handleCreateOrder);
        server.createContext("/api/orders/advance", Main::handleAdvanceOrder);
        server.createContext("/api/orders/cancel", Main::handleCancelOrder);

        server.setExecutor(null);
        server.start();

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE VENTA DE PLANES DE TELEFONÍA MÓVIL     ║");
        System.out.println("║              Patrón de Diseño: STATE                ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  Servidor iniciado en: http://localhost:" + PORT + "         ║");
        System.out.println("║  Operadores disponibles: Claro, Movistar, Tigo      ║");
        System.out.println("║  Planes disponibles: 15 (5 por operadora)           ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("\nAbre tu navegador en: http://localhost:" + PORT);
    }

    // ─── Serve frontend HTML ──────────────────────────────────────────────────

    private static void serveIndex(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        try {
            byte[] content = Files.readAllBytes(Paths.get("web/index.html"));
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, content.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(content);
            }
        } catch (IOException e) {
            String error = "<h1>Error: web/index.html not found</h1>";
            exchange.sendResponseHeaders(404, error.length());
            exchange.getResponseBody().write(error.getBytes());
        }
    }

    // ─── API: Catalog ─────────────────────────────────────────────────────────

    private static void handleCatalog(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        StringBuilder sb = new StringBuilder("{\"operators\":{");
        boolean firstOp = true;
        for (Map.Entry<String, Operator> entry : PlanCatalog.getOperators().entrySet()) {
            if (!firstOp) sb.append(",");
            Operator op = entry.getValue();
            sb.append("\"").append(op.getId()).append("\":{")
              .append("\"id\":\"").append(op.getId()).append("\",")
              .append("\"name\":\"").append(op.getName()).append("\",")
              .append("\"colorPrimary\":\"").append(op.getColorPrimary()).append("\",")
              .append("\"colorSecondary\":\"").append(op.getColorSecondary()).append("\",")
              .append("\"icon\":\"").append(op.getLogoIcon()).append("\",")
              .append("\"slogan\":\"").append(op.getSlogan()).append("\"}");
            firstOp = false;
        }
        sb.append("},\"plans\":[");

        List<MobilePlan> plans = PlanCatalog.getPlans();
        for (int i = 0; i < plans.size(); i++) {
            MobilePlan p = plans.get(i);
            sb.append("{")
              .append("\"id\":\"").append(p.getId()).append("\",")
              .append("\"operatorId\":\"").append(p.getOperatorId()).append("\",")
              .append("\"name\":\"").append(p.getName()).append("\",")
              .append("\"days\":").append(p.getDurationDays()).append(",")
              .append("\"price\":").append(p.getPriceInCOP()).append(",")
              .append("\"data\":\"").append(p.getDataIncluded()).append("\",")
              .append("\"minutes\":\"").append(p.getMinutesIncluded()).append("\",")
              .append("\"sms\":\"").append(p.getSmsIncluded()).append("\",")
              .append("\"badge\":\"").append(p.getBadge()).append("\"")
              .append("}");
            if (i < plans.size() - 1) sb.append(",");
        }
        sb.append("]}");

        sendJson(exchange, 200, sb.toString());
    }

    // ─── API: Get all orders ──────────────────────────────────────────────────

    private static void handleOrders(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        sendJson(exchange, 200, saleService.getAllOrdersAsJson());
    }

    // ─── API: Create order ────────────────────────────────────────────────────

    private static void handleCreateOrder(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        Map<String, String> params = parseBody(exchange);
        try {
            SaleOrder order = saleService.createOrder(
                    params.getOrDefault("customerName", ""),
                    params.getOrDefault("idNumber", ""),
                    params.getOrDefault("phoneNumber", ""),
                    params.getOrDefault("email", ""),
                    params.getOrDefault("planId", "")
            );
            int index = saleService.getAllOrders().size() - 1;
            sendJson(exchange, 201, "{\"success\":true,\"orderIndex\":" + index
                    + ",\"orderId\":\"" + order.getOrderId() + "\"}");
        } catch (Exception e) {
            sendJson(exchange, 400, "{\"success\":false,\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // ─── API: Advance order ───────────────────────────────────────────────────

    private static void handleAdvanceOrder(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        Map<String, String> params = parseBody(exchange);
        int index = Integer.parseInt(params.getOrDefault("orderIndex", "-1"));
        SaleOrder order = saleService.getOrderByIndex(index);
        if (order == null) {
            sendJson(exchange, 404, "{\"success\":false,\"error\":\"Order not found\"}");
            return;
        }
        saleService.advanceOrder(order);
        sendJson(exchange, 200, "{\"success\":true,\"state\":\""
                + order.getCurrentState().getStateName() + "\"}");
    }

    // ─── API: Cancel order ────────────────────────────────────────────────────

    private static void handleCancelOrder(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }
        Map<String, String> params = parseBody(exchange);
        int index = Integer.parseInt(params.getOrDefault("orderIndex", "-1"));
        SaleOrder order = saleService.getOrderByIndex(index);
        if (order == null) {
            sendJson(exchange, 404, "{\"success\":false,\"error\":\"Order not found\"}");
            return;
        }
        saleService.cancelOrder(order);
        sendJson(exchange, 200, "{\"success\":true,\"state\":\""
                + order.getCurrentState().getStateName() + "\"}");
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private static void sendJson(HttpExchange exchange, int code, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static Map<String, String> parseBody(HttpExchange exchange) throws IOException {
        Map<String, String> params = new HashMap<>();
        try (InputStream is = exchange.getRequestBody()) {
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            for (String pair : body.split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    params.put(
                        URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(kv[1], StandardCharsets.UTF_8)
                    );
                }
            }
        }
        return params;
    }
}

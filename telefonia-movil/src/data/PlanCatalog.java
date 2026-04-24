package data;

import models.MobilePlan;
import models.Operator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data: PlanCatalog
 * Contains the catalog of all operators and their available mobile plans.
 * 5 plans per operator (1 to 5 days), all with different prices.
 */
public class PlanCatalog {

    private static final Map<String, Operator> operators = new LinkedHashMap<>();
    private static final List<MobilePlan> plans = new ArrayList<>();

    static {
        // ─── Operators ────────────────────────────────────────────────────────
        operators.put("CLARO", new Operator(
                "CLARO", "Claro", "#E30613", "#FF4444",
                "📶", "Más red, más conexión"
        ));
        operators.put("MOVISTAR", new Operator(
                "MOVISTAR", "Movistar", "#019DF4", "#0056A8",
                "🌐", "Juntos podemos más"
        ));
        operators.put("TIGO", new Operator(
                "TIGO", "Tigo", "#00A0DC", "#003087",
                "⚡", "Más de lo que imaginas"
        ));

        // ─── CLARO Plans (1–5 days) ───────────────────────────────────────────
        plans.add(new MobilePlan("CLR-D1", "CLARO", "Claro Diario",
                1, 2000, "200 MB", "30 min", "50 SMS", "BÁSICO"));
        plans.add(new MobilePlan("CLR-D2", "CLARO", "Claro Plus 2",
                2, 3500, "500 MB", "60 min", "100 SMS", "POPULAR"));
        plans.add(new MobilePlan("CLR-D3", "CLARO", "Claro Connect 3",
                3, 5000, "1 GB", "100 min", "200 SMS", "RECOMENDADO"));
        plans.add(new MobilePlan("CLR-D4", "CLARO", "Claro Full 4",
                4, 7500, "2 GB", "150 min", "Ilimitados", "PREMIUM"));
        plans.add(new MobilePlan("CLR-D5", "CLARO", "Claro Max 5",
                5, 10000, "3 GB", "Ilimitados", "Ilimitados", "ELITE"));

        // ─── MOVISTAR Plans (1–5 days) ────────────────────────────────────────
        plans.add(new MobilePlan("MOV-D1", "MOVISTAR", "Movistar Inicial",
                1, 2500, "150 MB", "20 min", "30 SMS", "BÁSICO"));
        plans.add(new MobilePlan("MOV-D2", "MOVISTAR", "Movistar Básico 2",
                2, 4000, "400 MB", "50 min", "80 SMS", "POPULAR"));
        plans.add(new MobilePlan("MOV-D3", "MOVISTAR", "Movistar Smart 3",
                3, 5500, "900 MB", "80 min", "150 SMS", "RECOMENDADO"));
        plans.add(new MobilePlan("MOV-D4", "MOVISTAR", "Movistar Pro 4",
                4, 8000, "2.5 GB", "120 min", "Ilimitados", "PREMIUM"));
        plans.add(new MobilePlan("MOV-D5", "MOVISTAR", "Movistar Ilimitado 5",
                5, 9500, "4 GB", "Ilimitados", "Ilimitados", "ELITE"));

        // ─── TIGO Plans (1–5 days) ────────────────────────────────────────────
        plans.add(new MobilePlan("TGO-D1", "TIGO", "Tigo Arranca",
                1, 2200, "250 MB", "25 min", "40 SMS", "BÁSICO"));
        plans.add(new MobilePlan("TGO-D2", "TIGO", "Tigo Actívate 2",
                2, 3800, "600 MB", "70 min", "120 SMS", "POPULAR"));
        plans.add(new MobilePlan("TGO-D3", "TIGO", "Tigo Total 3",
                3, 6000, "1.5 GB", "120 min", "180 SMS", "RECOMENDADO"));
        plans.add(new MobilePlan("TGO-D4", "TIGO", "Tigo Potencia 4",
                4, 7000, "3 GB", "200 min", "Ilimitados", "PREMIUM"));
        plans.add(new MobilePlan("TGO-D5", "TIGO", "Tigo Élite 5",
                5, 9000, "5 GB", "Ilimitados", "Ilimitados", "ELITE"));
    }

    public static Map<String, Operator> getOperators() {
        return operators;
    }

    public static List<MobilePlan> getPlans() {
        return new ArrayList<>(plans);
    }

    public static List<MobilePlan> getPlansByOperator(String operatorId) {
        List<MobilePlan> result = new ArrayList<>();
        for (MobilePlan p : plans) {
            if (p.getOperatorId().equals(operatorId)) {
                result.add(p);
            }
        }
        return result;
    }

    public static MobilePlan findPlanById(String planId) {
        for (MobilePlan p : plans) {
            if (p.getId().equals(planId)) {
                return p;
            }
        }
        return null;
    }

    public static Operator findOperatorById(String operatorId) {
        return operators.get(operatorId);
    }
}

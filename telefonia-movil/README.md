# TeleVenta - Sistema de Venta de Planes Móviles
### Patrón de Diseño: STATE | Patrones de Software - 4to Semestre

---

## 📋 Descripción del Proyecto

Sistema de simulación de ventas de planes de telefonía móvil para las operadoras
**Claro**, **Movistar** y **Tigo**, implementando el **Patrón de Diseño State** en Java.

Cada orden de venta pasa por los siguientes estados:

```
PENDING → VALIDATED → PAYMENT_PROCESSED → ACTIVE
                                         → CANCELLED (desde cualquier estado anterior a ACTIVE)
```

---

## 🏗️ Estructura del Proyecto

```
telefonia-movil/
├── src/
│   ├── states/
│   │   ├── SaleState.java            ← Interfaz del estado (State Interface)
│   │   ├── PendingState.java         ← Estado: Pendiente
│   │   ├── ValidationState.java      ← Estado: Validado
│   │   ├── PaymentProcessedState.java← Estado: Pago procesado
│   │   ├── ActiveState.java          ← Estado: Activo
│   │   └── CancelledState.java       ← Estado: Cancelado
│   ├── models/
│   │   ├── MobilePlan.java           ← Modelo de plan móvil
│   │   ├── Operator.java             ← Modelo de operadora
│   │   ├── Customer.java             ← Modelo de cliente
│   │   └── SaleLog.java              ← Modelo de log de actividad
│   ├── context/
│   │   └── SaleOrder.java            ← CONTEXTO del Patrón State
│   ├── data/
│   │   └── PlanCatalog.java          ← Catálogo con 15 planes (5 x operadora)
│   ├── services/
│   │   └── SaleService.java          ← Lógica de negocio
│   └── Main.java                     ← Servidor HTTP + punto de entrada
├── web/
│   └── index.html                    ← Frontend premium (HTML/CSS/JS)
├── run.sh                            ← Script de compilación y ejecución
└── README.md
```

---

## 🎯 Componentes del Patrón State

| Componente | Clase | Descripción |
|---|---|---|
| **Interfaz State** | `SaleState` | Define los métodos que todos los estados implementan |
| **Contexto** | `SaleOrder` | Mantiene referencia al estado actual y delega comportamiento |
| **Estado Concreto 1** | `PendingState` | Orden creada, esperando validación |
| **Estado Concreto 2** | `ValidationState` | Cliente validado, esperando pago |
| **Estado Concreto 3** | `PaymentProcessedState` | Pago confirmado, listo para activar |
| **Estado Concreto 4** | `ActiveState` | Plan activo, proceso completado |
| **Estado Concreto 5** | `CancelledState` | Orden cancelada, sin más acciones |

---

## 📦 Planes Disponibles (15 en total)

### Claro
| ID | Plan | Días | Precio | Datos | Minutos |
|---|---|---|---|---|---|
| CLR-D1 | Claro Diario | 1 | $2.000 | 200 MB | 30 min |
| CLR-D2 | Claro Plus 2 | 2 | $3.500 | 500 MB | 60 min |
| CLR-D3 | Claro Connect 3 | 3 | $5.000 | 1 GB | 100 min |
| CLR-D4 | Claro Full 4 | 4 | $7.500 | 2 GB | 150 min |
| CLR-D5 | Claro Max 5 | 5 | $10.000 | 3 GB | Ilimitados |

### Movistar
| ID | Plan | Días | Precio | Datos | Minutos |
|---|---|---|---|---|---|
| MOV-D1 | Movistar Inicial | 1 | $2.500 | 150 MB | 20 min |
| MOV-D2 | Movistar Básico 2 | 2 | $4.000 | 400 MB | 50 min |
| MOV-D3 | Movistar Smart 3 | 3 | $5.500 | 900 MB | 80 min |
| MOV-D4 | Movistar Pro 4 | 4 | $8.000 | 2.5 GB | 120 min |
| MOV-D5 | Movistar Ilimitado 5 | 5 | $9.500 | 4 GB | Ilimitados |

### Tigo
| ID | Plan | Días | Precio | Datos | Minutos |
|---|---|---|---|---|---|
| TGO-D1 | Tigo Arranca | 1 | $2.200 | 250 MB | 25 min |
| TGO-D2 | Tigo Actívate 2 | 2 | $3.800 | 600 MB | 70 min |
| TGO-D3 | Tigo Total 3 | 3 | $6.000 | 1.5 GB | 120 min |
| TGO-D4 | Tigo Potencia 4 | 4 | $7.000 | 3 GB | 200 min |
| TGO-D5 | Tigo Élite 5 | 5 | $9.000 | 5 GB | Ilimitados |

---

## ▶️ Cómo ejecutar

### Requisitos
- Java 11 o superior
- Un navegador web moderno

### Pasos

1. Abrir una terminal y navegar a la carpeta del proyecto:
   ```bash
   cd telefonia-movil
   ```

2. Dar permisos al script y ejecutarlo:
   ```bash
   chmod +x run.sh
   ./run.sh
   ```

   O compilar y ejecutar manualmente:
   ```bash
   mkdir -p out
   javac -d out src/states/*.java src/models/*.java src/context/*.java src/data/*.java src/services/*.java src/Main.java
   java -cp out Main
   ```

3. Abrir el navegador en: **http://localhost:8080**

---

## 🔄 Flujo de uso

1. **Nueva Venta** → Selecciona operador → Selecciona plan → Ingresa datos del cliente → Solicitar
2. **Mis Órdenes** → Ver todas las órdenes → Hacer clic en una orden → Avanzar estados con los botones

---

*Patrones de Software | Semestre 4 | Docente: Jhonatan Andres Mideros Narvaez*

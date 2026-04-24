#!/bin/bash
# ══════════════════════════════════════════════════════
#  TeleVenta - Sistema de Venta de Planes Móviles
#  Patrón de Diseño: State
# ══════════════════════════════════════════════════════

echo "╔══════════════════════════════════════════════╗"
echo "║  Compilando TeleVenta - Patrón State         ║"
echo "╚══════════════════════════════════════════════╝"

# Create output directory
mkdir -p out

# Compile all Java files
javac -d out \
  src/states/SaleState.java \
  src/models/MobilePlan.java \
  src/models/Operator.java \
  src/models/Customer.java \
  src/models/SaleLog.java \
  src/context/SaleOrder.java \
  src/states/PendingState.java \
  src/states/ValidationState.java \
  src/states/PaymentProcessedState.java \
  src/states/ActiveState.java \
  src/states/CancelledState.java \
  src/data/PlanCatalog.java \
  src/services/SaleService.java \
  src/Main.java 2>&1

if [ $? -eq 0 ]; then
  echo "✅ Compilación exitosa."
  echo ""
  echo "Iniciando servidor en http://localhost:8080 ..."
  echo "Presiona Ctrl+C para detener."
  echo ""
  java -cp out Main
else
  echo "❌ Error en la compilación. Revisa los mensajes anteriores."
fi

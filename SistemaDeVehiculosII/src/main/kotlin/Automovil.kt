class Automovil (marca: String,
                 modelo: String,
                 capacidadCombustible: Float,
                 combustibleActual: Float,
                 kilometrosActuales: Int,
                 val tipo: String,
                 val esElectrico: Boolean): Vehiculo(marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {

    override fun toString(): String {
        return "Automovil: (Marca = $marca, Modelo = $modelo, Capacidad Combustible = ${capacidadCombustible}l," +
                " Combustible Actual = ${"%2f".format(combustibleActual)}l, Kilómetros Actuales =" +
                " ${kilometrosActuales}km, Tipo = $tipo, Eléctrico = ${if (esElectrico) { return "Sí" } else { return "No" }}," +
                "Británico = ${if (condicionBritania) { return "Sí" } else { return "No" }})"
    }

    override fun calcularAutonomia(): Int {
        return if (esElectrico) {
            val autonomia = (capacidadCombustible * 5).toInt()
            autonomia
        } else {
            val autonomia = (capacidadCombustible * 10).toInt()
            autonomia
        }
    }

    fun realizarDerrape(): Float {
        val kilometrosRecorribles = calcularAutonomia()
        if (5 <= kilometrosRecorribles) {
            kilometrosActuales += 5
            val kilometrosRecorridos = kilometrosRecorribles - 5
            return if (esElectrico) {
                combustibleActual -= (kilometrosRecorridos / 5)
                println("Derrape realizado con exito.")
                combustibleActual
            } else {
                combustibleActual -= (kilometrosRecorridos / 10)
                println("Viaje realizado con exito.")
                combustibleActual
            }
        }
        else {
            val kilometrosRestantes = 5 - kilometrosRecorribles
            val kilometrosRecorridos = 5 - kilometrosRestantes
            kilometrosActuales += kilometrosRecorridos
            return if (esElectrico) {
                combustibleActual -= (kilometrosRecorridos / 5)
                println("Derrape no completado.")
                combustibleActual
            } else {
                combustibleActual -= (kilometrosRecorridos / 10)
                println("Derrape no completado.")
                combustibleActual
            }
        }
    }

    companion object {
        var condicionBritania: Boolean = false

        fun cambiarCondicionBritania(nuevaCondicion: Boolean) {
            condicionBritania = nuevaCondicion
        }
    }
}
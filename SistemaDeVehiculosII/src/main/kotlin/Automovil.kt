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
                " ${kilometrosActuales}km, Tipo = $tipo, Eléctrico = ${if (esElectrico) { println("Sí") } else { println("No") }}," +
                " Británico = ${if (condicionBritania) { println("Sí") } else { println("No") }})"
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
        if (DERRAPE <= kilometrosRecorribles) {
            val kilometrosRecorridos = kilometrosRecorribles - DERRAPE
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
            val kilometrosRestantes = DERRAPE - kilometrosRecorribles
            val kilometrosRecorridos = DERRAPE - kilometrosRestantes
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

        const val DERRAPE = 5
    }
}
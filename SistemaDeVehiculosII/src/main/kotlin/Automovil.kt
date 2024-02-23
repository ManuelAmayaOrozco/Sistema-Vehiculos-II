class Automovil (nombre: String,
                 marca: String,
                 modelo: String,
                 capacidadCombustible: Float,
                 combustibleActual: Float,
                 kilometrosActuales: Float,
                 val esHibrido: Boolean): Vehiculo(nombre, marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {

    override fun toString(): String {
        return "Automovil: (Marca = $marca, Modelo = $modelo, Capacidad Combustible = ${capacidadCombustible}l," +
                " Combustible Actual = ${"%2f".format(combustibleActual)}l, Kilómetros Actuales =" +
                " ${kilometrosActuales}km, Híbrido = ${if (esHibrido) { println("Sí") } else { println("No") }}," +
                " Británico = ${if (condicionBritania) { println("Sí") } else { println("No") }})"
    }

    override fun calcularAutonomia(): Int {
        return if (esHibrido) {
            val autonomia = (capacidadCombustible * KILOMETRO_POR_LITRO_ELECTRICO).toInt()
            autonomia
        } else {
            val autonomia = (capacidadCombustible * KILOMETRO_POR_LITRO).toInt()
            autonomia
        }
    }

    override fun realizaViaje(distancia: Float): Float {
        if (esHibrido) {
            val kilometrosRecorribles = calcularAutonomia()
            return if (distancia <= kilometrosRecorribles) {
                kilometrosActuales += distancia
                val kilometrosRecorridos = kilometrosRecorribles - distancia
                combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO_ELECTRICO)
                0f
            } else {
                val kilometrosRestantes = distancia - kilometrosRecorribles
                val kilometrosRecorridos = distancia - kilometrosRestantes
                kilometrosActuales += kilometrosRecorridos
                combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO_ELECTRICO)
                kilometrosRestantes
            }
        }
        else {
            return super.realizaViaje(distancia)
        }
    }

    fun realizarDerrape(): Float {
        val kilometrosRecorribles = calcularAutonomia()
        if (esHibrido) {
            return if (DERRAPE_ELECTRICO <= kilometrosRecorribles) {
                val kilometrosRecorridos = kilometrosRecorribles - DERRAPE_ELECTRICO
                combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO_ELECTRICO)
                combustibleActual
            } else {
                val kilometrosRestantes = DERRAPE_ELECTRICO - kilometrosRecorribles
                val kilometrosRecorridos = DERRAPE_ELECTRICO - kilometrosRestantes
                combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO_ELECTRICO)
                combustibleActual
            }
        }
        else {
            return if (DERRAPE_NORMAL <= kilometrosRecorribles) {
                val kilometrosRecorridos = kilometrosRecorribles - DERRAPE_NORMAL
                combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO)
                combustibleActual
            } else {
                val kilometrosRestantes = DERRAPE_NORMAL - kilometrosRecorribles
                val kilometrosRecorridos = DERRAPE_NORMAL - kilometrosRestantes
                combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO)
                combustibleActual
            }
        }
    }

    companion object {
        var condicionBritania: Boolean = false

        fun cambiarCondicionBritania(nuevaCondicion: Boolean) {
            condicionBritania = nuevaCondicion
        }

        const val DERRAPE_ELECTRICO = 6.25f
        const val DERRAPE_NORMAL = 7.5f
        const val KILOMETRO_POR_LITRO_ELECTRICO = 15f
    }
}
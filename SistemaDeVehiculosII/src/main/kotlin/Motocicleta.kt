class Motocicleta(nombre: String,
                  marca: String,
                  modelo: String,
                  capacidadCombustible: Float,
                  combustibleActual: Float,
                  kilometrosActuales: Float,
                  val cilindrada: Int): Vehiculo(nombre, marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {

    init {
        require(cilindrada in 125..1000)
    }

    override fun toString(): String {
        return "Motocicleta: (Marca = $marca, Modelo = $modelo, Capacidad Combustible = ${capacidadCombustible}l, " +
                "Combustible Actual = ${"%2f".format(combustibleActual)}l, " +
                "Kilómetros Actuales = ${kilometrosActuales}km, Cilindrada = $cilindrada)"
    }

    override fun calcularAutonomia(): Int {
        return if (cilindrada == 1000) {
            (capacidadCombustible * KILOMETRO_POR_LITRO_MOTO).toInt()
        } else {
            val resta = cilindrada / 1000
            (capacidadCombustible * (KILOMETRO_POR_LITRO_MOTO - resta)).toInt()
        }
    }

    override fun realizaViaje(distancia: Float): Float {
        val kilometrosRecorribles = calcularAutonomia()
        return if (distancia <= kilometrosRecorribles) {
            kilometrosActuales += distancia
            val kilometrosRecorridos = kilometrosRecorribles - distancia
            combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO_MOTO)
            0f
        } else {
            val kilometrosRestantes = distancia - kilometrosRecorribles
            val kilometrosRecorridos = distancia - kilometrosRestantes
            kilometrosActuales += kilometrosRecorridos
            combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO_MOTO)
            kilometrosRestantes
        }
    }

    fun realizarCaballito(): Float {
        val kilometrosRecorribles = calcularAutonomia()
        return if (CABALLITO <= kilometrosRecorribles) {
            val kilometrosRecorridos = kilometrosRecorribles - CABALLITO
            combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO_MOTO)
            combustibleActual
        } else {
            val kilometrosRestantes = CABALLITO - kilometrosRecorribles
            val kilometrosRecorridos = CABALLITO - kilometrosRestantes
            combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO_MOTO)
            combustibleActual
        }
    }

    companion object {
        const val CABALLITO = 6.5F
        const val KILOMETRO_POR_LITRO_MOTO = 20f
    }
}
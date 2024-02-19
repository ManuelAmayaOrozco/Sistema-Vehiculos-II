class Motocicleta(marca: String,
                  modelo: String,
                  capacidadCombustible: Float,
                  combustibleActual: Float,
                  kilometrosActuales: Int,
                  val cilindrada: Int): Vehiculo(marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {

    override fun toString(): String {
        return "Motocicleta: (Marca = $marca, Modelo = $modelo, Capacidad Combustible = ${capacidadCombustible}l, " +
                "Combustible Actual = ${"%2f".format(combustibleActual)}l, " +
                "Kilómetros Actuales = ${kilometrosActuales}km, Cilindrada = $cilindrada)"
    }

    override fun calcularAutonomia(): Int {
        val autonomia = (capacidadCombustible * 20).toInt()
        return autonomia
    }

    override fun realizaViaje(distancia: Int): Int {
        val kilometrosRecorribles = calcularAutonomia()
        if (distancia <= kilometrosRecorribles) {
            kilometrosActuales += distancia
            val kilometrosRecorridos = kilometrosRecorribles - distancia
            combustibleActual -= (kilometrosRecorridos / 20)
            println("Viaje realizado con exito.")
            return 0
        }
        else {
            val kilometrosRestantes = distancia - kilometrosRecorribles
            val kilometrosRecorridos = distancia - kilometrosRestantes
            kilometrosActuales += kilometrosRecorridos
            combustibleActual -= (kilometrosRecorridos / 20)
            println("Viaje no completado. (Distancia restante = ${kilometrosRestantes}km")
            return kilometrosRestantes
        }
    }

    fun realizarCaballito(): Float {
        val kilometrosRecorribles = calcularAutonomia()
        return if (5 <= kilometrosRecorribles) {
            kilometrosActuales += 5
            val kilometrosRecorridos = kilometrosRecorribles - 5
            combustibleActual -= (kilometrosRecorridos / 20)
            println("Caballito realizado con exito.")
            combustibleActual
        } else {
            val kilometrosRestantes = 5 - kilometrosRecorribles
            val kilometrosRecorridos = 5 - kilometrosRestantes
            kilometrosActuales += kilometrosRecorridos
            combustibleActual -= (kilometrosRecorridos / 20)
            println("Caballito no completado.")
            combustibleActual
        }
    }
}
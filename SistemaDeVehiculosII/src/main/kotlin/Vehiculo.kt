open class Vehiculo (val marca: String,
                     val modelo: String,
                     val capacidadCombustible: Float,
                     var combustibleActual: Float,
                     var kilometrosActuales: Int) {

    override fun toString(): String {
        return "Vehículo: (Marca = $marca, Modelo = $modelo, Capacidad Combustible = ${capacidadCombustible}l" +
                ", Combustible Actual = ${"%2f".format(combustibleActual)}l, Kilómetros Actuales = " +
                "${kilometrosActuales}km)"
    }

    open fun obtenerInformacion(): String {
        val kilometrosRecorribles = combustibleActual * 10
        return "El vehículo puede recorrer ${"%2f".format(kilometrosRecorribles)}km actualmente."
    }

    open fun realizaViaje(distancia: Int): Int {
        val kilometrosRecorribles = (combustibleActual * 10).toInt()
        if (distancia <= kilometrosRecorribles) {
            kilometrosActuales += distancia
            val kilometrosRecorridos = kilometrosRecorribles - distancia
            combustibleActual -= (kilometrosRecorridos / 10)
            println("Viaje realizado con exito.")
            return 0
        }
        else {
            val kilometrosRestantes = distancia - kilometrosRecorribles
            val kilometrosRecorridos = distancia - kilometrosRestantes
            kilometrosActuales += kilometrosRecorridos
            combustibleActual -= (kilometrosRecorridos / 10)
            println("Viaje no completado. (Distancia restante = ${kilometrosRestantes}km")
            return kilometrosRestantes
        }
    }

    open fun repostar(cantidad: Float): Float {
        val resultadoRepostaje = combustibleActual + cantidad
        if (resultadoRepostaje > combustibleActual) {
            val cantidadRepostada = capacidadCombustible - cantidad
            combustibleActual += cantidadRepostada
            return cantidadRepostada
        }
        else{
            val cantidadRepostada = capacidadCombustible - combustibleActual
            combustibleActual += cantidadRepostada
            return cantidadRepostada
        }
    }

    open fun calcularAutonomia(): Float {
        val autonomia = capacidadCombustible * 10
        return autonomia
    }
}
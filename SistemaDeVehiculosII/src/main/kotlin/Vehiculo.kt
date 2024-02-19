abstract class Vehiculo (val marca: String,
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
        return "El vehículo puede recorrer ${calcularAutonomia()}km actualmente."
    }

    open fun realizaViaje(distancia: Int): Int {
        val kilometrosRecorribles = calcularAutonomia()
        return if (distancia <= kilometrosRecorribles) {
            kilometrosActuales += distancia
            val kilometrosRecorridos = kilometrosRecorribles - distancia
            combustibleActual -= (kilometrosRecorridos / 10)
            println("Viaje realizado con exito.")
            0
        } else {
            val kilometrosRestantes = distancia - kilometrosRecorribles
            val kilometrosRecorridos = distancia - kilometrosRestantes
            kilometrosActuales += kilometrosRecorridos
            combustibleActual -= (kilometrosRecorridos / 10)
            println("Viaje no completado. (Distancia restante = ${kilometrosRestantes}km)")
            kilometrosRestantes
        }
    }

    open fun repostar(cantidad: Float = 0f): Float {
        return if (cantidad <= 0 || cantidad + combustibleActual > capacidadCombustible) {
            val cantidadRepostada = capacidadCombustible - combustibleActual
            combustibleActual = capacidadCombustible
            cantidadRepostada
        } else{
            combustibleActual += cantidad
            cantidad
        }
    }

    open fun calcularAutonomia(): Int {
        val autonomia = (combustibleActual * 10).toInt()
        return autonomia
    }
}
abstract class Vehiculo (val nombre: String,
                     val marca: String,
                     val modelo: String,
                     val capacidadCombustible: Float,
                     var combustibleActual: Float = capacidadCombustible,
                     var kilometrosActuales: Int = 0) {

    init {
        require(nombre.lowercase() !in listaNombres) { "El nombre no puede ser repetido." }
        listaNombres.add(nombre.lowercase())
    }

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
            combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO)
            0
        } else {
            val kilometrosRestantes = distancia - kilometrosRecorribles
            val kilometrosRecorridos = distancia - kilometrosRestantes
            kilometrosActuales += kilometrosRecorridos
            combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO)
            kilometrosRestantes
        }
    }

    open fun repostar(cantidad: Float = 0f): Float {
        val cantidadRepostada = capacidadCombustible - combustibleActual
        return if (cantidad <= 0 || cantidad + combustibleActual > capacidadCombustible) {
            combustibleActual = capacidadCombustible
            cantidadRepostada
        } else{
            combustibleActual += cantidad
            cantidad
        }
    }

    open fun calcularAutonomia(): Int {
        val autonomia = (combustibleActual * KILOMETRO_POR_LITRO).toInt()
        return autonomia
    }

    companion object {
        val listaNombres = mutableSetOf<String>()
        const val KILOMETRO_POR_LITRO = 10f
    }
}
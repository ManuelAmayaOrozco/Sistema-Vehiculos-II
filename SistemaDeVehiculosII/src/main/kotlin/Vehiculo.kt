/**
 * Clase abstracta que construye vehiculos en general.
 *
 * @param nombre El nombre del vehículo.
 * @param marca La marca del vehículo.
 * @param modelo El modelo del vehículo.
 * @param capacidadCombustible La capacidad máxima de combustible que puede tener
 * el vehículo.
 * @param combustibleActual El combustible que tiene actualmente el vehículo.
 * @param kilometrosActuales La cantidad de kilometros recorridos por el vehículo.
 */
abstract class Vehiculo (val nombre: String,
                         val marca: String,
                         val modelo: String,
                         val capacidadCombustible: Float,
                         var combustibleActual: Float = capacidadCombustible,
                         var kilometrosActuales: Float = 0f) {

    init {
        require(nombre.lowercase() !in listaNombres) { "El nombre no puede ser repetido." }
        listaNombres.add(nombre.lowercase())
    }

    override fun toString(): String {
        return "Vehículo: (Marca = $marca, Modelo = $modelo, Capacidad Combustible = ${capacidadCombustible}l" +
                ", Combustible Actual = ${"%2f".format(combustibleActual)}l, Kilómetros Actuales = " +
                "${kilometrosActuales}km)"
    }

    /**
     * Función que muestra la autonomía del vehículo.
     *
     * @return Muestra la autonomía del vehículo.
     */
    open fun obtenerInformacion(): String {
        return "El vehículo puede recorrer ${calcularAutonomia()}km actualmente."
    }

    /**
     * Función que hace que el vehículo avance la distancia indicada, devolviendo los
     * kilometros restantes para finalizar el viaje. Si no tiene combustible suficiente
     * para finalizar el viaje, tendrá que repostar. Los kilómetros recorridos se añaden
     * a su contador.
     *
     * @param distancia La distancia a recorrer.
     * @return Los kilómetros restantes por recorrer.
     */
    open fun realizaViaje(distancia: Float): Float {
        val kilometrosRecorribles = calcularAutonomia()
        return if (distancia <= kilometrosRecorribles) {
            kilometrosActuales += distancia
            val kilometrosRecorridos = kilometrosRecorribles - distancia
            combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO)
            0f
        } else {
            val kilometrosRestantes = distancia - kilometrosRecorribles
            val kilometrosRecorridos = distancia - kilometrosRestantes
            kilometrosActuales += kilometrosRecorridos
            combustibleActual -= (kilometrosRecorridos / KILOMETRO_POR_LITRO)
            kilometrosRestantes
        }
    }

    /**
     * Función que ayuda al coche a repostar. Si la cantidad para repostar es igual
     * o menor que 0 o sobrepasa la [capacidadCombustible], entonces se llenará hasta
     * su cantidad máxima, de lo contrario se llenará lo necesario. Retorna la cantidad
     * de combustible que se ha usado.
     *
     * @param cantidad La cantidad de combustible para llenar el vehículo.
     * @return La cantidad repostada.
     */
    open fun repostar(cantidad: Float = 0f): Float {
        val cantidadRepostada = capacidadCombustible - combustibleActual
        return if (cantidad <= 0f || cantidad + combustibleActual > capacidadCombustible) {
            combustibleActual = capacidadCombustible
            cantidadRepostada
        } else{
            combustibleActual += cantidad
            cantidad
        }
    }

    /**
     * Función que calcula la autonomía del vehículo, para saber cuantos kilómetros
     * es capaz de recorrer actualmente.
     *
     * @return La autonomía del vehículo.
     */
    open fun calcularAutonomia(): Int {
        val autonomia = (combustibleActual * KILOMETRO_POR_LITRO).toInt()
        return autonomia
    }

    companion object {
        val listaNombres = mutableSetOf<String>()
        const val KILOMETRO_POR_LITRO = 10f
    }
}
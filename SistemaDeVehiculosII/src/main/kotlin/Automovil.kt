/**
 * Clase derivada de la clase [Vehiculo], usada únicamente para coches y otros automóviles.
 *
 * @param nombre El nombre del automovil.
 * @param marca La marca del automovil.
 * @param modelo El modelo del automovil.
 * @param capacidadCombustible La capacidad máxima de combustible que puede tener
 * el automovil.
 * @param combustibleActual El combustible que tiene actualmente el automovil.
 * @param kilometrosActuales La cantidad de kilometros recorridos por el automovil.
 * @param esHibrido Indica si el automovil es híbrido (true) o no (false).
 * @param condicionBritania Indica si el automovil conduce por la izquierda o la derecha.
 */
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

    /**
     * Función que calcula la autonomía del automovil, para saber cuantos kilómetros
     * es capaz de recorrer actualmente. Si es híbrido, se utilizará otro valor para
     * calcular su autonomía.
     *
     * @return La autonomía del automovil.
     */
    override fun calcularAutonomia(): Int {
        return if (esHibrido) {
            val autonomia = (capacidadCombustible * KILOMETRO_POR_LITRO_ELECTRICO).toInt()
            autonomia
        } else {
            val autonomia = (capacidadCombustible * KILOMETRO_POR_LITRO).toInt()
            autonomia
        }
    }

    /**
     * Función que hace que el automovil avance la distancia indicada, devolviendo los
     * kilometros restantes para finalizar el viaje. Si no tiene combustible suficiente
     * para finalizar el viaje, tendrá que repostar. Los kilómetros recorridos se añaden
     * a su contador. Si es híbrido se utilizarán valores distintos para calcular su combustible.
     *
     * @param distancia La distancia a recorrer.
     * @return Los kilómetros restantes por recorrer.
     */
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

    /**
     * Función en el que el automovil realiza una filigrana, en este caso un derrape.
     * Sirve para gastar combustible más que nada. Si es híbrido se utilizarán otros
     * valores para calcular su combustible. Devuelve el combustible actual tras la
     * filigrana.
     *
     * @return Combustible actual tras la filigrana.
     */
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

        /**
         * Función que cambia [condicionBritania] para todos los coches.
         *
         * @param True para activar la condición, y false para desactivarla.
         */
        fun cambiarCondicionBritania(nuevaCondicion: Boolean) {
            condicionBritania = nuevaCondicion
        }

        const val DERRAPE_ELECTRICO = 6.25f
        const val DERRAPE_NORMAL = 7.5f
        const val KILOMETRO_POR_LITRO_ELECTRICO = 15f
    }
}
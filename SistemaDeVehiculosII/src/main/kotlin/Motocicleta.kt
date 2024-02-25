
/**
 * Clase derivada de la clase [Vehiculo], usada únicamente para motocicletas.
 *
 * @param nombre El nombre de la motocicleta.
 * @param marca La marca de la motocicleta.
 * @param modelo El modelo de la motocicleta.
 * @param capacidadCombustible La capacidad máxima de combustible que puede tener
 * la motocicleta.
 * @param combustibleActual El combustible que tiene actualmente la motocicleta.
 * @param kilometrosActuales La cantidad de kilometros recorridos por la motocicleta.
 * @param cilindrada La cilindrada de la motocicleta.
 */
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

    /**
     * Función que calcula la autonomía de la motocicleta, para saber cuantos kilómetros
     * es capaz de recorrer actualmente. Si la cilindrada es de 1000 se multiplicará por 20,
     * si tiene una cilindrada menor esta cantidad será dividida entre 1000 y restado a 20.
     *
     * @return La autonomía del automovil.
     */
    override fun calcularAutonomia(): Int {
        return if (cilindrada == 1000) {
            (capacidadCombustible * KILOMETRO_POR_LITRO_MOTO).toInt()
        } else {
            val resta = cilindrada / 1000
            (capacidadCombustible * (KILOMETRO_POR_LITRO_MOTO - resta)).toInt()
        }
    }

    /**
     * Función que hace que la motocicleta avance la distancia indicada, devolviendo los
     * kilometros restantes para finalizar el viaje. Si no tiene combustible suficiente
     * para finalizar el viaje, tendrá que repostar. Los kilómetros recorridos se añaden
     * a su contador.
     *
     * @param distancia La distancia a recorrer.
     * @return Los kilómetros restantes por recorrer.
     */
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

    /**
     * Función en el que la motocicleta realiza una filigrana, en este caso un caballito.
     * Sirve para gastar combustible más que nada. Devuelve el combustible actual tras la
     * filigrana.
     *
     * @return Combustible actual tras la filigrana.
     */
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
        const val CABALLITO = 6.5f
        const val KILOMETRO_POR_LITRO_MOTO = 20f
    }
}
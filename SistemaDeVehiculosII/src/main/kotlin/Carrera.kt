import kotlin.random.Random

class Carrera(val nombreCarrera: String,
              val distanciaTotal: Float,
              val participantes: List<Vehiculo>,
              private var estadoCarrera: Boolean,
              val historialAcciones: MutableMap<String, MutableList<String>>,
              val posiciones: MutableList<Pair<String, Int>>) {

    fun rand(start: Int, end: Int): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }

    fun iniciarCarrera() {
        estadoCarrera = true
    }

    fun avanzarVehiculo(vehiculo: Vehiculo) {
        val distancia = rand(10, 200)
        if (distancia > vehiculo.calcularAutonomia()) {
            repostarVehiculo(vehiculo, 0F)
        }
        realizarFiligrana(vehiculo)
        vehiculo.realizaViaje(distancia)
    }

    fun repostarVehiculo(vehiculo: Vehiculo, cantidad: Float) {
        vehiculo.repostar(cantidad)
        historialAcciones[vehiculo.nombre]?.add("${vehiculo.nombre} ha repostado.")
    }

    fun realizarFiligrana(vehiculo: Vehiculo) {
        val eleccion = rand(0, 1)
        if (eleccion == 1) {
            if(vehiculo is Automovil) {
                vehiculo.realizarDerrape()
                historialAcciones[vehiculo.nombre]?.add("${vehiculo.nombre} ha realizado un derrape.")
            }
            else if (vehiculo is Motocicleta) {
                vehiculo.realizarCaballito()
                historialAcciones[vehiculo.nombre]?.add("${vehiculo.nombre} ha realizado un caballito.")
            }
        }
    }

}
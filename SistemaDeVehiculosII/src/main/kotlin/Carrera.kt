import kotlin.random.Random

class Carrera(val nombreCarrera: String,
              val distanciaTotal: Float,
              val participantes: List<Vehiculo>) {

    private var estadoCarrera: Boolean = true

    val historialAcciones: MutableMap<String, MutableList<String>> = mutableMapOf<String, MutableList<String>>()

    var posiciones: MutableList<Pair<String, Int>> = mutableListOf<Pair<String, Int>>()

    var paradasRepostaje: MutableList<Pair<String, Int>> = mutableListOf<Pair<String, Int>>()


    fun iniciarCarrera() {
        estadoCarrera = true
    }

    fun avanzarVehiculo(vehiculo: Vehiculo) {
        val distancia = Random.nextInt(10, 201)
        if (distancia > vehiculo.calcularAutonomia()) {
            repostarVehiculo(vehiculo, 0f)
            actualizarParadas(vehiculo)
        }
        else {
            val optFiligrana = Random.nextInt(0, 2)
            if (optFiligrana == 0) {
                realizarFiligrana(vehiculo)
            }
            else {
                realizarFiligrana(vehiculo)
                realizarFiligrana(vehiculo)
            }
            vehiculo.realizaViaje(distancia)
            registrarAccion(vehiculo.nombre, "${vehiculo.nombre} ha recorrido ${distancia}km.")
        }
    }

    fun repostarVehiculo(vehiculo: Vehiculo, cantidad: Float) {
        vehiculo.repostar(cantidad)
        registrarAccion(vehiculo.nombre, "${vehiculo.nombre} ha repostado.")
    }

    fun realizarFiligrana(vehiculo: Vehiculo) {
        val eleccion = Random.nextInt(0, 2)
        if (eleccion == 1) {
            if(vehiculo is Automovil) {
                vehiculo.realizarDerrape()
                registrarAccion(vehiculo.nombre, "${vehiculo.nombre} ha realizado un derrape.")
            }
            else if (vehiculo is Motocicleta) {
                vehiculo.realizarCaballito()
                registrarAccion(vehiculo.nombre, "${vehiculo.nombre} ha realizado un caballito.")
            }
        }
    }

    fun actualizarPosiciones() {
        val listaPos = mutableListOf<Pair<String,Int>>()
        for (vehiculo in participantes) {
            listaPos.add(Pair(vehiculo.nombre, vehiculo.kilometrosActuales))
        }
        listaPos.sortedBy { it.second }

        var posicion = participantes.size

        for (vehiculo in listaPos) {
            val encVehiculo = posiciones.find { it.first == vehiculo.first }
            val posVehiculo = posiciones.indexOfFirst { it.first == vehiculo.first }
            if (encVehiculo != null) {
                posiciones[posVehiculo] = Pair(encVehiculo.first, posicion)
            }
            posicion--
        }
    }

    fun actualizarParadas(vehiculo: Vehiculo) {
        val encVehiculo = paradasRepostaje.find { it.first == vehiculo.nombre }
        val posVehiculo = paradasRepostaje.indexOfFirst { it.first == vehiculo.nombre }
        var paradasActuales = encVehiculo?.second
        paradasActuales = paradasActuales!! + 1
        if (encVehiculo != null) {
            paradasRepostaje[posVehiculo] = Pair(encVehiculo.first, paradasActuales)
        }
    }

    fun registrarAccion(vehiculo: String, accion: String) {
        historialAcciones[vehiculo]?.add(accion)
    }

    fun determinarGanador() {
        for (vehiculo in participantes) {
            if (vehiculo.kilometrosActuales >= distanciaTotal) {
                estadoCarrera = false
            }
        }
    }

    fun obtenerResultados(): MutableList<ResultadoCarrera> {
        var listaResultados = mutableListOf<ResultadoCarrera>()
        for (vehiculo in participantes) {
            val posicion = (posiciones.find { it.first == vehiculo.nombre })?.second
            val paradasRepostaje = (paradasRepostaje.find { it.first == vehiculo.nombre })?.second
            val resultadoVehiculo = posicion?.let {
                historialAcciones[vehiculo.nombre]?.let { it1 ->
                    if (paradasRepostaje != null) {
                        ResultadoCarrera(vehiculo = vehiculo,
                            posicion = it,
                            kilometraje = vehiculo.kilometrosActuales,
                            paradasRepostaje = paradasRepostaje,
                            historialAcciones = it1
                        )
                    }
                }
            }
            listaResultados.add(resultadoVehiculo)
        }
        return listaResultados
    }

    /**
     * Representa el resultado final de un vehículo en la carrera, incluyendo su posición final, el kilometraje total recorrido,
     * el número de paradas para repostar, y un historial detallado de todas las acciones realizadas durante la carrera.
     *
     * @property vehiculo El [Vehiculo] al que pertenece este resultado.
     * @property posicion La posición final del vehículo en la carrera, donde una posición menor indica un mejor rendimiento.
     * @property kilometraje El total de kilómetros recorridos por el vehículo durante la carrera.
     * @property paradasRepostaje El número de veces que el vehículo tuvo que repostar combustible durante la carrera.
     * @property historialAcciones Una lista de cadenas que describen las acciones realizadas por el vehículo a lo largo de la carrera, proporcionando un registro detallado de su rendimiento y estrategias.
     */
    data class ResultadoCarrera(
        val vehiculo: Vehiculo,
        val posicion: Int,
        val kilometraje: Int,
        val paradasRepostaje: Int,
        val historialAcciones: List<String>
    )
}
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
        for (vehiculo in participantes) {
            historialAcciones[vehiculo.nombre] = mutableListOf<String>()
            posiciones.add(Pair(vehiculo.nombre, 0))
            paradasRepostaje.add(Pair(vehiculo.nombre, 0))
        }

        while (estadoCarrera) {
            for (vehiculo in participantes) {
                avanzarVehiculo(vehiculo)
                determinarGanador()
                if (vehiculo.kilometrosActuales >= distanciaTotal) {
                    println("EL GANADOR ES ${vehiculo.nombre}!!!")
                }
            }
        }

        val resultados = obtenerResultados()
        for (resultado in resultados) {
            println("============================================")
            println("Nombre: ${resultado.vehiculo.nombre}")
            println("Posicion: ${resultado.posicion}")
            println("Kilometraje: ${resultado.kilometraje}")
            println("NºRepostaje: ${resultado.paradasRepostaje}")
            println("Acciones: \n${resultado.historialAcciones}")
            println("============================================")
        }
    }

    fun avanzarTramo(vehiculo: Vehiculo, kmTramo: Float) {


        //Utilizar  distanciaNorecorrida = vehiculo.realizaViaje()
        val distanciaNoRecorrida = vehiculo.realizaViaje(kmTramo)

        //Si no pudo realizar toda la distancia => repostar()
             //registrarAccion(L repostados)
        if (distanciaNoRecorrida == 0f) {
            repostarVehiculo(vehiculo, 0f)
        }

        //registrarAccion(km avanzados)
        registrarAccion(vehiculo.nombre, "${vehiculo.nombre} recorre un tramo de ${kmTramo}km.")
    }

    fun avanzarVehiculo(vehiculo: Vehiculo) {
        val distancia = Random.nextInt(10, 201)
        //11 -> 1 tramo: 11
        //45 -> 3 tramos: 20+fil(1 o 2)+20+fil(1 o 2)+5

        //Calcular tramos
        val cantidadTramos = (distancia / 20)

        //Calcular kmtramo y restar distancia
        val distanciaTramoRestante = distancia - (20 * cantidadTramos - 1)

        //registrarAccion(kmtotales a recorrer)
        registrarAccion(vehiculo.nombre, "${vehiculo.nombre} realiza un viaje de ${distancia}km.")

        //Logica para enviar distancia a avanzarTramo(vehiculo, kmtramo)
        while (cantidadTramos != 0) {
            if (cantidadTramos != 1) {
                avanzarTramo(vehiculo, 20f)
                val optFiligrana = Random.nextInt(0, 2)
                if (optFiligrana == 0) {
                    realizarFiligrana(vehiculo)
                } else {
                    realizarFiligrana(vehiculo)
                    realizarFiligrana(vehiculo)
                }
            } else {
                avanzarTramo(vehiculo, (distanciaTramoRestante).toFloat())
            }
        }

        //avanzartramo()

        //si eran 20km realizar filigrana

        //realizarFiligrana()

        //Al final : registrarAccion(km recorridos)
    }

    fun repostarVehiculo(vehiculo: Vehiculo, cantidad: Float) {
        val cantidadRepostada = vehiculo.repostar(cantidad)
        registrarAccion(vehiculo.nombre, "${vehiculo.nombre} ha repostado ${cantidadRepostada}l.")
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
        val listaPos = mutableListOf<Pair<String,Float>>()
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
            val posicion = (posiciones.find { it.first == vehiculo.nombre })!!.second
            val historialAcciones = historialAcciones[vehiculo.nombre]!!
            val paradasRepostaje = (paradasRepostaje.find { it.first == vehiculo.nombre })!!.second
            val resultadoVehiculo = ResultadoCarrera(vehiculo = vehiculo,
                                                     posicion = posicion,
                                                     kilometraje = vehiculo.kilometrosActuales,
                                                     paradasRepostaje = paradasRepostaje,
                                                     historialAcciones = historialAcciones)
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
        val kilometraje: Float,
        val paradasRepostaje: Int,
        val historialAcciones: List<String>
    )
}
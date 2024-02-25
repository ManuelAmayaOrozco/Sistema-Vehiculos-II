import kotlin.random.Random


/**
 * Clase que representa la simulación de la carrera en sí.
 *
 * @property nombreCarrera El nombre proporcionado a la carrera.
 * @property distanciaTotal La distancia total que han de recorrer los vehiculos para
 * finalizar la carrera.
 * @property participantes Lista de elementos de la clase [Vehiculo] que son los
 * diferentes vehiculos que participaran en la carrera.
 * @property estadoCarrera El estado actual de la carrera siendo true cuando está
 * activa y false cuando termina.
 * @property historialAcciones El historial de todas las acciones de cada vehículo.
 * @property posiciones Lista de todos los nombres de los vehiculos y sus posiciones
 * actuales.
 * @property paradasRepostaje Lista de todos los nombres de los vehiculos y la cantidad
 * de veces que ha repostado cada uno.
 */
class Carrera(val nombreCarrera: String,
              val distanciaTotal: Float,
              val participantes: List<Vehiculo>) {

    private var estadoCarrera: Boolean = true

    val historialAcciones: MutableMap<String, MutableList<String>> = mutableMapOf<String, MutableList<String>>()

    var posiciones: MutableList<Pair<String, Int>> = mutableListOf<Pair<String, Int>>()

    var paradasRepostaje: MutableList<Pair<String, Int>> = mutableListOf<Pair<String, Int>>()


    /**
     * Función que simula la carrera en sí. Comienza inicializando el historial,
     * las posiciones y las paradas, para despues comenzar la carrera, llamando
     * uno a uno a cada vehiculo, haciendo que avancen, se actulicen las posiciones y
     * se determine el ganador en caso de victoria. Al final se muestran todos los resultados
     * obtenidos de cada participante.
     */
    fun iniciarCarrera() {
        estadoCarrera = true
        var countPos = 1
        for (vehiculo in participantes) {
            historialAcciones[vehiculo.nombre] = mutableListOf<String>()
            posiciones.add(Pair(vehiculo.nombre, countPos))
            countPos++
            paradasRepostaje.add(Pair(vehiculo.nombre, 0))
        }

        while (estadoCarrera) {
            for (vehiculo in participantes) {
                avanzarVehiculo(vehiculo)
                actualizarPosiciones()
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

    /**
     * Funcion que avanza el tramo proporcionado por la funcion [avanzarVehiculo],
     * si necesita repostar tambien llamará a la función [repostarVehiculo] y actualizará
     * el número de paradas.
     *
     * @param vehiculo El vehiculo elegido para avanzar el tramo.
     * @param kmTramo Los kilometros del tramo a avanzar.
     */
    private fun avanzarTramo(vehiculo: Vehiculo, kmTramo: Float) {


        //Utilizar  distanciaNorecorrida = vehiculo.realizaViaje()
        val distanciaNoRecorrida = vehiculo.realizaViaje(kmTramo)

        //Si no pudo realizar toda la distancia => repostar()
        //registrarAccion(L repostados)
        if (distanciaNoRecorrida == 0f) {
            repostarVehiculo(vehiculo)
            actualizarParadas(vehiculo)
        }

        //registrarAccion(km avanzados)
        registrarAccion(vehiculo.nombre, "${vehiculo.nombre} recorre un tramo de ${kmTramo}km.")
    }

    /**
     * Función que avanza el vehículo una distancia aleatoria entre 10 y 200km, separando
     * dicha distancia entre tramos que serán recorridos en [avanzarTramo], dicha acción
     * será registrada en el historial; cada 20km el vehiculo intentará realizar una o
     * dos filigranas que gastarán combustible.
     *
     * @param vehiculo El vehículo que avanzará la distancia elegida.
     */
    private fun avanzarVehiculo(vehiculo: Vehiculo) {
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

    /**
     * Función que se encarga de repostar el vehículo elegido, registrándolo en el historial.
     *
     * @param vehiculo El vehículo elegido para ser repostado.
     */
    private fun repostarVehiculo(vehiculo: Vehiculo) {
        val cantidadRepostada = vehiculo.repostar()
        registrarAccion(vehiculo.nombre, "${vehiculo.nombre} ha repostado ${cantidadRepostada}l.")
    }

    /**
     * Función que hace que el vehículo realice (o no) una filigrana aleatoriamente,
     * registrándola en el historial del vehículo.
     *
     * @param vehiculo El vehículo elegido para realizar la filigrana.
     */
    private fun realizarFiligrana(vehiculo: Vehiculo) {
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

    /**
     * Función que actualiza las posiciones actuales de todos los vehiculos, creando
     * una lista de posiciones actuales basándolas en los kilometros recorridos, y después
     * asignando cada posición a los vehículos correspondientes.
     */
    private fun actualizarPosiciones() {
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

    /**
     * Función que actualiza el número de paradas de un vehículo específico.
     *
     * @param vehiculo El vehículo elegido para ser actualizado.
     */
    private fun actualizarParadas(vehiculo: Vehiculo) {
        val encVehiculo = paradasRepostaje.find { it.first == vehiculo.nombre }
        val posVehiculo = paradasRepostaje.indexOfFirst { it.first == vehiculo.nombre }
        var paradasActuales = encVehiculo?.second
        paradasActuales = paradasActuales!! + 1
        if (encVehiculo != null) {
            paradasRepostaje[posVehiculo] = Pair(encVehiculo.first, paradasActuales)
        }
    }

    /**
     * Función que registra una acción en el historial de acciones de un vehiculo.
     *
     * @param vehiculo El nombre del vehiculo elegido.
     * @param accion El texto que será añadido al historial de acciones.
     */
    private fun registrarAccion(vehiculo: String, accion: String) {
        historialAcciones[vehiculo]?.add(accion)
    }

    /**
     * Función que determina cuando aparece el ganador, mirando todos los vehiculos
     * para comprobar que se ha cumplido la condición de victoria. Una vez ocurra,
     * el [estadoCarrera] será falso, indicando el fin de la carrera.
     */
    private fun determinarGanador() {
        for (vehiculo in participantes) {
            if (vehiculo.kilometrosActuales >= distanciaTotal) {
                estadoCarrera = false
            }
        }
    }

    /**
     * Función que obtiene una lista completa de todos los resultados de cada participante
     * tras el fin de la carrera, devolviendo dicha lista para que pueda ser impresa.
     *
     * @return La lista completa de todos los resultados.
     */
    private fun obtenerResultados(): MutableList<ResultadoCarrera> {
        val listaResultados = mutableListOf<ResultadoCarrera>()
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
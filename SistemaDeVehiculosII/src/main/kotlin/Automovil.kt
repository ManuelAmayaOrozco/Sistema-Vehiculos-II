class Automovil (marca: String, modelo: String, capacidadCombustible: Int, val tipo: String): Vehiculo(marca, modelo, capacidadCombustible) {

    override fun toString(): String {
        return "Automovil: (marca = $marca, modelo = $modelo, capacidad combustible = $capacidadCombustible, tipo = $tipo)"
    }

    override fun calcularAutonomia(): Int {
        return (super.calcularAutonomia() + 100)
    }
}
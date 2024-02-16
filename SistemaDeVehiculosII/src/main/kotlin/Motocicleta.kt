class Motocicleta(marca: String, modelo: String, capacidadCombustible: Int, val cilindrada: Int): Vehiculo(marca, modelo, capacidadCombustible) {

    override fun toString(): String {
        return "Motocicleta: (marca = $marca, modelo = $modelo, capacidad combustible = $capacidadCombustible, cilindrada = $cilindrada)"
    }

    override fun calcularAutonomia(): Int {
        return (super.calcularAutonomia() - 40)
    }
}
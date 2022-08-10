class Gravity(private val acceleration: Double = 1.0, private val max: Double = 10.0) {
    val entities = mutableListOf<Entity>()

    fun apply() {
        entities.forEach {
            if (it.weight > 0) {
                it.momentumY += acceleration

                if (it.momentumY > max) {
                    it.momentumY = max
                }
                it.y += it.momentumY
            }
        }
    }
}

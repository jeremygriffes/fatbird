import com.soywiz.korge.view.Sprite

open class Entity : Sprite() {
    var collidesWithSolids = true
    var weight = 1.0
    var airResistance = 1.0
    var momentumX = 0.0
    var momentumY = 0.0
    var isOnGround = false

    open fun land() {
        momentumY = 0.0
        isOnGround = true
        momentumX = 0.0
    }
}
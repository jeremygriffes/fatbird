import com.soywiz.klock.TimeSpan
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

open class Entity(scene: FatBirdScene) : Sprite() {
    var collidesWithSolids = true
    var weight = 1.0
    var airResistance = 1.0
    var momentumX = 0.0
    var momentumY = 0.0
    var isOnGround = false

    init {
        onCollision({ scene.collidables.contains(it) }) { earth ->
            if (collidesWithSolids) {
                if (momentumY > 0) {
                    alignBottomToTopOf(earth)
                    land()
                } else if (momentumY < 0) {
                    alignTopToBottomOf(earth)
                    y++
                }
                momentumY = 0.0
            }
        }
    }

    open fun land() {
        momentumY = 0.0
        isOnGround = true
        momentumX = 0.0
    }

    companion object {
        suspend fun loadAnimation(fileName: String, fromFrame: Int, toFrame: Int, squareSize: Int = 64) =
            SpriteAnimation(
                resourcesVfs[fileName].readBitmap(),
                squareSize,
                squareSize,
                0,
                fromFrame * squareSize,
                toFrame,
                1,
                0,
                0,
                toFrame - fromFrame
            )
    }
}


fun Int.fps() = TimeSpan(1000.0 / this.toDouble())

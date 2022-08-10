import com.soywiz.klock.TimeSpan
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

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

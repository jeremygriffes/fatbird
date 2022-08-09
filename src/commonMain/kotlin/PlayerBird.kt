import com.soywiz.klock.TimeSpan
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

enum class Action {
    Idle,
    RunRight,
    RunLeft,
}

const val runSpeed = 2.0
const val flapSpeed = 10

class PlayerBird(
    private val idleAnimation: SpriteAnimation,
    private val runRightAnimation: SpriteAnimation,
    private val runLeftAnimation: SpriteAnimation
) : Sprite() {
    private var action: Action = Action.Idle
    var verticalMomentum = 0

    fun idle() {
        playAnimationLooped(idleAnimation, spriteDisplayTime = 8.fps())
        action = Action.Idle
    }

    fun runRight() {
        playAnimationLooped(runRightAnimation, spriteDisplayTime = 20.fps())
        x += runSpeed
        action = Action.RunRight
    }

    fun runLeft() {
        playAnimationLooped(runLeftAnimation, spriteDisplayTime = 20.fps())
        x -= runSpeed
        action = Action.RunLeft
    }

    fun flap() {
        playAnimationLooped(idleAnimation, spriteDisplayTime = 8.fps())
        verticalMomentum -= flapSpeed
    }

    companion object {
        suspend fun create() = PlayerBird(
            loadAnimation("bird_idle.png", 3),
            loadAnimation("bird_run_right.png", 7),
            loadAnimation("bird_run_left.png", 7)
        )

        private suspend fun loadAnimation(fileName: String, frames: Int) = SpriteAnimation(
            resourcesVfs[fileName].readBitmap(),
            64,
            64,
            0,
            0,
            frames,
            1
        )
    }

    private fun Int.fps() = TimeSpan(1000.0 / this.toDouble())
}

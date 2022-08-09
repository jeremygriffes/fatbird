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
const val flapSpeed = 9
const val maxUpwardMomentum = -14
const val jumpSpeed = 5.0
const val jumpResistance = 0.1

class PlayerBird(
    private val idleAnimation: SpriteAnimation,
    private val runRightAnimation: SpriteAnimation,
    private val runLeftAnimation: SpriteAnimation,
    private val jumpRightAnimation: SpriteAnimation,
    private val jumpLeftAnimation: SpriteAnimation
) : Sprite() {
    var verticalMomentum = 0
    var isJumping = false
    private var xMomentum = 0.0
    private var canJump = false

    fun idle() {
        playAnimationLooped(idleAnimation, spriteDisplayTime = 8.fps())
    }

    fun runRight() {
        playAnimationLooped(runRightAnimation, spriteDisplayTime = 20.fps())
        x += runSpeed
    }

    fun runLeft() {
        playAnimationLooped(runLeftAnimation, spriteDisplayTime = 20.fps())
        x -= runSpeed
    }

    fun flap() {
        canJump = false
        playAnimationLooped(idleAnimation, spriteDisplayTime = 8.fps())
        verticalMomentum -= flapSpeed
        verticalMomentum = maxOf(verticalMomentum, maxUpwardMomentum)
    }

    fun jumpRight() {
        if (canJump) {
            canJump = false
            isJumping = true
            playAnimationLooped(jumpRightAnimation, spriteDisplayTime = 20.fps())
            xMomentum += jumpSpeed
            verticalMomentum -= flapSpeed
        }
    }

    fun jumpLeft() {
        if (canJump) {
            canJump = false
            isJumping = true
            playAnimationLooped(jumpLeftAnimation, spriteDisplayTime = 20.fps())
            xMomentum -= jumpSpeed
            verticalMomentum -= flapSpeed
        }
    }

    fun continueJumpMomentum() {
        x += xMomentum

        if (xMomentum > 0) {
            xMomentum -= jumpResistance
            xMomentum = maxOf(0.0, xMomentum)
        }

        if (xMomentum < 0) {
            xMomentum += jumpResistance
            xMomentum = minOf(0.0, xMomentum)
        }
    }

    fun land() {
        verticalMomentum = 0
        isJumping = false
        xMomentum = 0.0
        canJump = true
    }

    companion object {
        suspend fun create() = PlayerBird(
            loadAnimation("bird_idle.png", 0, 3),
            loadAnimation("bird_run_right.png", 0, 7),
            loadAnimation("bird_run_left.png", 0, 7),
            loadAnimation("bird_run_right.png", 0, 1),
            loadAnimation("bird_run_left.png", 0, 1),
        )

        private suspend fun loadAnimation(fileName: String, fromFrame: Int, toFrame: Int) = SpriteAnimation(
            resourcesVfs[fileName].readBitmap(),
            64,
            64,
            0,
            0,
            toFrame,
            1,
            0,
            0,
            toFrame - fromFrame
        )
    }

    private fun Int.fps() = TimeSpan(1000.0 / this.toDouble())
}

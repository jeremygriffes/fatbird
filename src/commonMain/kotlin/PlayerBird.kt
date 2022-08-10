import com.soywiz.korge.view.SpriteAnimation

const val runSpeed = 2.0
const val flapSpeed = 9
const val maxUpwardMomentum = -14.0
const val jumpSpeed = 5.0
const val jumpResistance = 0.1

class PlayerBird(
    private val idleAnimation: SpriteAnimation,
    private val runRightAnimation: SpriteAnimation,
    private val runLeftAnimation: SpriteAnimation,
    private val jumpRightAnimation: SpriteAnimation,
    private val jumpLeftAnimation: SpriteAnimation
) : Entity() {
    var isJumping = false
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
        momentumY -= flapSpeed
        momentumY = maxOf(momentumY, maxUpwardMomentum)
    }

    fun jumpRight() {
        if (canJump) {
            canJump = false
            isJumping = true
            playAnimationLooped(jumpRightAnimation, spriteDisplayTime = 20.fps())
            momentumX += jumpSpeed
            momentumY -= flapSpeed
        }
    }

    fun jumpLeft() {
        if (canJump) {
            canJump = false
            isJumping = true
            playAnimationLooped(jumpLeftAnimation, spriteDisplayTime = 20.fps())
            momentumX -= jumpSpeed
            momentumY -= flapSpeed
        }
    }

    fun continueJumpMomentum() {
        x += momentumX

        if (momentumX > 0) {
            momentumX -= jumpResistance
            momentumX = maxOf(0.0, momentumX)
        }

        if (momentumX < 0) {
            momentumX += jumpResistance
            momentumX = minOf(0.0, momentumX)
        }
    }

    override fun land() {
        super.land()
        isJumping = false
        canJump = true
    }

    companion object {
        suspend fun create() = PlayerBird(
            loadAnimation("bird_idle.png", 0, 3),
            loadAnimation("bird_run_right.png", 0, 7),
            loadAnimation("bird_run_left.png", 0, 7),
            loadAnimation("bird_run_right.png", 0, 1),
            loadAnimation("bird_run_left.png", 6, 7),
        )
    }
}

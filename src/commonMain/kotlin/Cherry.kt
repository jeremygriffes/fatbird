import com.soywiz.korge.view.SpriteAnimation

class Cherry(private val animation: SpriteAnimation) : Entity() {

    fun drop() {
        playAnimationLooped(animation, spriteDisplayTime = 8.fps())
    }

    override fun land() {
        super.land()
        stopAnimation()
    }

    companion object {
        suspend fun create() = Cherry(
            loadAnimation("cherry.png", 0, 8, 16)
        )
    }
}

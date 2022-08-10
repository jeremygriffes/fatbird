import com.soywiz.korge.view.SpriteAnimation

class Cherry(scene: FatBirdScene, private val animation: SpriteAnimation) : Entity(scene) {

    fun drop() {
        playAnimationLooped(animation, spriteDisplayTime = 8.fps())
    }

    override fun land() {
        super.land()
        stopAnimation()
    }

    companion object {
        suspend fun create(scene: FatBirdScene) = Cherry(
            scene,
            loadAnimation("cherry.png", 0, 8, 16)
        )
    }
}

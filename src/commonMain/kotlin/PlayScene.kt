import com.soywiz.korev.Key
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*

class PlayScene(
    private val playerBird: PlayerBird,
    private val terrain: Terrain
) : Scene() {
    private var gravityMomentum = 1
    private val maxGravity = 10
    private val isFalling: Boolean
        get() = gravityMomentum > 4

    override suspend fun SContainer.sceneMain() {
        val collidables = terrain.drawBase(this)

        playerBird.addTo(this).xy(centerX - playerBird.width / 2, /*bottomRowGrass.toDouble() - blockSize*/0.0)

        addUpdater {
            when {
                input.keys.pressing(Key.LEFT) && !isFalling -> playerBird.runLeft()
                input.keys.pressing(Key.RIGHT) && !isFalling -> playerBird.runRight()
                else -> playerBird.idle()
            }

            playerBird.y += gravityMomentum
            gravityMomentum += 1
            gravityMomentum = minOf(gravityMomentum, maxGravity)
        }

        playerBird.onCollision({ collidables.contains(it) }) {
            playerBird.alignBottomToTopOf(it)
            gravityMomentum = 1
        }
    }
}

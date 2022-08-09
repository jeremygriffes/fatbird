import com.soywiz.korev.Key
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*

class PlayScene(
    private val playerBird: PlayerBird,
    private val terrain: Terrain
) : Scene() {
    private val maxGravity = 10
    private val isFalling: Boolean
        get() = playerBird.verticalMomentum > 4

    override suspend fun SContainer.sceneMain() {
        val collidables = terrain.drawLand(this)

        playerBird.addTo(this).xy(centerX - playerBird.width / 2, /*bottomRowGrass.toDouble() - blockSize*/0.0)

        addUpdater {
            when {
                playerBird.isJumping -> playerBird.continueJumpMomentum()
                input.keys.pressing(Key.LEFT) && input.keys.pressing(Key.SPACE) && !isFalling -> playerBird.jumpLeft()
                input.keys.pressing(Key.RIGHT) && input.keys.pressing(Key.SPACE) && !isFalling -> playerBird.jumpRight()
                input.keys.pressing(Key.LEFT) && !isFalling -> playerBird.runLeft()
                input.keys.pressing(Key.RIGHT) && !isFalling -> playerBird.runRight()
                input.keys.justPressed(Key.SPACE) -> playerBird.flap()
                else -> playerBird.idle()
            }

            playerBird.y += playerBird.verticalMomentum
            playerBird.verticalMomentum += 1
            playerBird.verticalMomentum = minOf(playerBird.verticalMomentum, maxGravity)
        }

        playerBird.onCollision({ collidables.contains(it) }) {
            if (playerBird.verticalMomentum > 0) {
                playerBird.alignBottomToTopOf(it)
                playerBird.land()
            } else if (playerBird.verticalMomentum < 0) {
                playerBird.alignTopToBottomOf(it)
                playerBird.y++
            }
            playerBird.verticalMomentum = 0
        }
    }
}

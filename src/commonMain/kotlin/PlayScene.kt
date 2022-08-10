import com.soywiz.korev.Key
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*

class PlayScene(
    private val playerBird: PlayerBird,
    private val terrain: Terrain
) : Scene() {
    private val gravity = Gravity()

    override suspend fun SContainer.sceneMain() {
        val collidables = terrain.drawLand(this)

        playerBird.addTo(this).xy(centerX - playerBird.width / 2, /*bottomRowGrass.toDouble() - blockSize*/0.0)

        gravity.entities.add(playerBird)

        addUpdater {
            when {
                playerBird.isJumping -> playerBird.continueJumpMomentum()
                input.keys.pressing(Key.LEFT) && input.keys.justPressed(Key.SPACE) && playerBird.isOnGround -> playerBird.jumpLeft()
                input.keys.pressing(Key.RIGHT) && input.keys.justPressed(Key.SPACE) && playerBird.isOnGround -> playerBird.jumpRight()
                input.keys.pressing(Key.LEFT) && playerBird.isOnGround -> playerBird.runLeft()
                input.keys.pressing(Key.RIGHT) && playerBird.isOnGround -> playerBird.runRight()
                input.keys.justPressed(Key.SPACE) -> playerBird.flap()
                else -> playerBird.idle()
            }

            gravity.apply()
        }

        gravity.entities.forEach { entity ->
            if (entity.collidesWithSolids) {
                entity.onCollision({ collidables.contains(it) }) { earth ->
                    if (entity.momentumY > 0) {
                        entity.alignBottomToTopOf(earth)
                        entity.land()
                    } else if (entity.momentumY < 0) {
                        entity.alignTopToBottomOf(earth)
                        entity.y++
                    }
                    entity.momentumY = 0.0
                }
            }
        }
    }
}

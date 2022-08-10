import com.soywiz.klock.DateTime
import com.soywiz.korev.Key
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korio.async.launchUnscoped
import kotlin.random.Random
import kotlin.random.nextInt

class PlayScene(
    private val playerBird: PlayerBird,
    private val terrain: Terrain
) : Scene() {
    private val random = Random(DateTime.nowUnixLong())
    private val gravity = Gravity()
//    private lateinit var coroutineContext: CoroutineContext

    override suspend fun SContainer.sceneMain() {
        val collidables = terrain.drawLand(this)

        playerBird.addTo(this)
            .xy(centerX - playerBird.width / 2, /*bottomRowGrass.toDouble() - blockSize*/ -blockSize.toDouble())

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

            makeRandomEvents(this)

            gravity.apply()

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

    private fun makeRandomEvents(container: SContainer) {
        val rnd = random.nextInt(1000)
        if (rnd in 0..5) {
            coroutineContext.launchUnscoped {
                dropCherry(container)
            }
        }
    }

    private suspend fun dropCherry(container: SContainer) {
        Cherry.create().apply {
            addTo(container)
            xy(random.nextInt(blockSize..(blocksX - 1) * blockSize).toDouble(), -blockSize.toDouble())
            gravity.entities.add(this)
            drop()
        }
    }
}

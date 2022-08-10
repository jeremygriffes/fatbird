import com.soywiz.klock.DateTime
import com.soywiz.korev.Key
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.addTo
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.xy
import com.soywiz.korio.async.launchUnscoped
import kotlin.random.Random
import kotlin.random.nextInt

class PlayScene(
    private val terrain: Terrain
) : FatBirdScene() {
    private val random = Random(DateTime.nowUnixLong())
    private val gravity = Gravity()

    override suspend fun SContainer.sceneMain() {
        collidables.addAll(terrain.drawLand(this))

        val playerBird = PlayerBird.create(this@PlayScene)

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
        }
    }

    private fun makeRandomEvents(container: SContainer) {
        val rnd = random.nextInt(1000)
        if (rnd in 0..2) {
            coroutineContext.launchUnscoped {
                dropCherry(container)
            }
        }
    }

    private suspend fun dropCherry(container: SContainer) {
        Cherry.create(this).apply {
            addTo(container)
            xy(random.nextInt(blockSize..(blocksX - 1) * blockSize).toDouble(), -blockSize.toDouble())
            gravity.entities.add(this)
            drop()
        }
    }
}

import com.soywiz.korev.Key
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.addTo
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.xy

class PlayScene(
    private val playerBird: PlayerBird,
    private val terrain: Terrain
) : Scene() {
    override suspend fun SContainer.sceneMain() {
        terrain.drawBase(this)

        playerBird.addTo(this).xy(centerX - playerBird.width / 2, bottomRowGrass.toDouble() - blockSize)

        addUpdater {
            when {
                input.keys.pressing(Key.LEFT) -> playerBird.runLeft()
                input.keys.pressing(Key.RIGHT) -> playerBird.runRight()
                else -> playerBird.idle()
            }
        }
    }
}

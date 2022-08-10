import com.soywiz.korge.Korge
import com.soywiz.korge.scene.sceneContainer
import com.soywiz.korim.color.Colors

const val blockSize = 32
const val blocksX = 30
const val blocksY = 20
const val bottomRowEarth = (blocksY - 1) * blockSize
const val centerX = blocksX * blockSize / 2

private val terrain = Terrain(blocksX, blocksY)

suspend fun main() = Korge(
    width = blocksX * blockSize,
    height = blocksY * blockSize,
    virtualWidth = blocksX * blockSize,
    virtualHeight = blocksY * blockSize,
    bgcolor = Colors.LIGHTCYAN
) {
    val sceneContainer = sceneContainer()

    terrain.loadBitmaps()

    sceneContainer.changeTo({ PlayScene(terrain) })
}

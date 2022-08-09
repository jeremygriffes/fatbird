import com.soywiz.klock.DateTime
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.View
import com.soywiz.korge.view.image
import com.soywiz.korge.view.xy
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import kotlin.random.Random
import kotlin.random.nextInt

class Terrain(private val xBlocks: Int, private val yBlocks: Int) {
    private lateinit var earthBitmap: Bitmap
    private lateinit var grassBitmap: Bitmap

    private val random = Random(DateTime.nowUnixLong())

    suspend fun loadBitmaps() {
        earthBitmap = resourcesVfs["earth.png"].readBitmap()
        grassBitmap = resourcesVfs["grass.png"].readBitmap()
    }

    fun drawLand(container: SContainer): List<View> = with(container) {
        val collidables = mutableListOf<View>()

        fun drawPlatform(fromX: Int, toX: Int, height: Int) {
            println("Drawing from $fromX to $toX")

            for (x in fromX until toX) {
                collidables.add(
                    image(earthBitmap).xy(x * blockSize, height)
                )
                image(grassBitmap).xy(x * blockSize, height - blockSize)
            }
        }

        // Establish solid ground across the bottom of the scene.
        drawPlatform(0, xBlocks, bottomRowEarth)

        // Randomly draw some platforms.
        val minDistanceBetweenPlatforms = blockSize * 4
        var y = bottomRowEarth - minDistanceBetweenPlatforms

        while (y > minDistanceBetweenPlatforms) {
            val platforms = when (random.nextInt(0..blocksX / 2)) {
                in 0..4 -> 3
                in 5..7 -> 6
                in 8..9 -> 9
                else -> 0
            }

            var isEarth = random.nextBoolean()
            var x = 0

            println("Drawing $platforms at $y")

            for (index in 0 until platforms) {
                val nextX = x + random.nextInt(2..xBlocks / 3)
                isEarth = !isEarth

                if (isEarth) {
                    drawPlatform(x, nextX, y)
                }

                x = nextX
            }

            y -= minDistanceBetweenPlatforms
        }

        collidables.toList()
    }

}

import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.image
import com.soywiz.korge.view.xy
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Terrain(private val xBlocks: Int, private val yBlocks: Int) {
    private lateinit var earthBitmap: Bitmap
    private lateinit var grassBitmap: Bitmap

    suspend fun loadBitmaps() {
        earthBitmap = resourcesVfs["earth.png"].readBitmap()
        grassBitmap = resourcesVfs["grass.png"].readBitmap()
    }

    fun drawBase(container: SContainer) = with(container) {
        for (x in 0 until xBlocks) {
            image(earthBitmap).xy(x * blockSize, bottomRowEarth)
            image(grassBitmap).xy(x * blockSize, bottomRowGrass)
        }
    }
}

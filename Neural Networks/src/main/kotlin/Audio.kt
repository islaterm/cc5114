import java.io.File
import java.io.IOException
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.UnsupportedAudioFileException

/**
 * Parses an audio file so it can be given as input to a neural network.
 * Partially based on the work found at: http://codeidol.com/java/swing/Audio/Build-an-Audio-Waveform-Display/
 *
 * @param path Relative path of the audio file.
 */
@Throws(UnsupportedAudioFileException::class, IOException::class)
fun processAudio(path: String): DoubleArray {
  // Load the raw data
  val fileIn = File(path)
  val audioInputStream = AudioSystem.getAudioInputStream(fileIn)
  if (audioInputStream.format.sampleSizeInBits != 16) {
    throw UnsupportedAudioFileException("Sample size must be 16 bits.")
  }

  val frameLength = audioInputStream.frameLength.toInt()
  val frameSize = audioInputStream.format.frameSize

  val bytes = ByteArray(frameLength * frameSize)
  audioInputStream.read(bytes)
  // Convert to samples and channels
  val numChannels = audioInputStream.format.channels

  val toReturn = Array(numChannels) { DoubleArray(frameLength) }

  var sampleIndex = 0

  var t = 0
  while (t < bytes.size) {
    for (channel in 0 until numChannels) {
      val low = bytes[t++].toInt()
      val high = bytes[t++].toInt()
      val sample = getSixteenBitSample(high, low)
      try {
        toReturn[channel][sampleIndex] = sample.toDouble()
      } catch (e: ArrayIndexOutOfBoundsException) {
        e.printStackTrace()
      }
    }
    sampleIndex++
  }

  return toReturn[0]
}

private fun getSixteenBitSample(high: Int, low: Int): Int = (high shl 8) + (low and 0x00ff)

fun main(args: Array<String>) {
  processAudio("Data sets/Tests/Wood_049.wav")
}
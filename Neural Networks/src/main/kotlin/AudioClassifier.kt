import neuralNetwork.NeuralNetwork
import java.io.File
import java.nio.file.Files
import java.nio.file.InvalidPathException
import java.nio.file.Paths

/**
 * This class implements the functionalities needed to classify an audio file (.wav) into 2 categories:
 *  - a coin hitting a wooden surface,
 *  - a coin hitting a metal surface.
 *
 *  For this to work, the audio files must last 0.5 seconds, have a sample rate of 6 kHz, 1 channel and a bit depth of
 *  16 bits.
 *
 * @param epoch Number of trainings done by the network. By default 350.
 * @author Ignacio Slater Mu&ntilde;oz
 */
class AudioClassifier(epoch: Int = 350) {

  private val path = try {
    Paths.get("Data sets/audio.train")
  } catch (e: InvalidPathException) {
    null
  }
  private var network: NeuralNetwork? = null

  init {
    println("Creating the network.")
    network = NeuralNetwork(epoch)  //
    network?.build(3000, 1500, 1500, 2)
    assert(path != null) { "Couldn't find the training file." }
    Files.readAllLines(path)
        .map { it.split(";") }
        .forEach {
          network!!.addTrainer(inputs = DoubleArray(3000) { i -> normalize(it[i].toDouble()) },
              answer = DoubleArray(2) { i -> if (i == it.last().toInt()) 1.0 else 0.0 })
        }
    println("Training the network. Please be patient this might take some minutes.")
    network?.train()
  }

  /**
   * Classifies an audio file as a hit on wood (0) or steel (1).
   *
   * @param path Path to an audio file.
   */
  fun predict(path: String): Int = network!!.predict(processAudio(path))

  /**
   * Classifies an audio file as a hit on wood (0) or steel (1).
   *
   * @param samples Array containing the sample data of an audio file.
   */
  private fun predict(samples: DoubleArray): Int = network!!.predict(samples)

  /**
   * Normalizes a value to the range [-32768, 32767].
   *
   * @param d Value that's going to be normalized
   */
  private fun normalize(d: Double): Double =
      network!!.normalize(x = d, min = -32768.0, max = 32767.0) // Audio input is in 16 bits
}

fun main(args: Array<String>) {
  val file = if (args.isNotEmpty()) args[0]      // Input file can be specified by command line
  else "Data sets/Tests/Wood_052.wav" // or it can be coded in here.

  if (!File(file).exists()) {
    println("Couldn't find the specified file.")
    System.exit(-1)
  }
  // AudioClassifier takes an optional argument that indicates the number of trainings to be performed.
  if (AudioClassifier(/*50*/).predict(file) == 0)
    println("The hit was in a wooden surface.")
  else
    println("The hit was in a metal surface.")
}
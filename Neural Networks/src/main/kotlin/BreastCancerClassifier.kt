import neuralNetwork.NeuralNetwork
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException

/**
 * Clasifica como benigno o maligno un cáncer de mama, de acuerdo a una información.
 * Este proceso utiliza una red neuronal.
 *
 * La información utilizada para entrenar y probar la red se puede encontrar en:
 * https://archive.ics.uci.edu/ml/datasets/Breast+Cancer+Wisconsin+%28Original%29
 *
 * @param epoch    Número de entrenamientos que hará la red.
 * @param filename Nombre del archivo que contiene la información para entrenar la red.
 * @author Ignacio Slater Mu&ntilde;oz
 */
class BreastCancerClassifier(private val epoch: Int, filename: String) {

  private val network = NeuralNetwork(epoch)
  private val plot = LinePlot("Learning Rate", "Epoch", "Precision (%)")

  init {
    network.build(9, 6, 2)
    var reader: BufferedReader? = null
    try {
      reader = File(filename).bufferedReader()
      val lines = mutableListOf<String>()
      reader.useLines { line -> line.forEach { lines.add(it) } }

      for (line in lines) {
        val tmp = line.split(",")
        val ans = doubleArrayOf(0.0, 0.0)
        ans[tmp.last().toInt() / 2 - 1] = 1.0
        network.addTrainer(DoubleArray(9, { i -> normalize(tmp[i + 1]) }), ans)
      }

      network.train()
    } catch (e: FileNotFoundException) {
      print("File not found.")
    } finally {
      if (reader != null) reader.close()
      else System.exit(-1)
    }
  }

  /**
   * Crea un gráfico que representa la raza de aprendizaje de la red.
   */
  fun drawResults() {
    plot.yData = network.precision.toDoubleArray()
    plot.draw()
  }

  /**
   * Normaliza un valor del input al rango [0, 1]
   *
   * @param x Valor a normalizar.
   */
  private fun normalize(x: String): Double {
    return network.normalize(x.toDouble(), 1.0, 10.0)
  }

  /**
   * Clasifica un cáncer de mama como benigno o maligno.
   *
   * @param clumpThickness           Evalúa si las celulas son mono- o multi-capa.
   * @param uniformityOfCellSize     Evalúa la consistencia de tamaño en las células de la muestra.
   * @param uniformityOfCellShape    Estima la igualdad de las formas celulares e identifica las varianzas marginales.
   * @param marginalAdhesion         Cuantifica cuántas células en el exterior del epitelio tienden a adherirse.
   * @param singleEpithelialCellSize Se relaciona con la uniformidad celular, determina si las células epiteliales se
   *                                 agrandan significativamente.
   * @param bareNuclei               Calcula la proporción de las células que están rodeadas por citoplasma con las que
   *                                 no.
   * @param blandChromatin           Clasifica la "textura" uniforme del núcleo en un rango de fino a grueso.
   * @param normalNucleoli           Determina si un nucleolo es pequeño y apenas visible o grande y más visible.
   * @param mitoses                  Describe el nivel de actividad mitótica.
   * @return 0 si se identifica como benigno, y 1 si es maligno.
   */
  fun predict(clumpThickness: Double, uniformityOfCellSize: Double, uniformityOfCellShape: Double,
      marginalAdhesion: Double, singleEpithelialCellSize: Double, bareNuclei: Double,
      blandChromatin: Double,
      normalNucleoli: Double, mitoses: Double): Int {
    return network.predict(
        doubleArrayOf(clumpThickness, uniformityOfCellSize, uniformityOfCellShape, marginalAdhesion,
            singleEpithelialCellSize, bareNuclei, blandChromatin, normalNucleoli,
            mitoses))
  }
}

/**
 * Crea y entrena la red y grafica su taza de aprendizaje.
 *
 * @param args No utilizado.
 */
fun main(args: Array<String>) {
  val classifier = BreastCancerClassifier(100, "Data sets/breast-cancer-wisconsin.data")
  classifier.drawResults()
}
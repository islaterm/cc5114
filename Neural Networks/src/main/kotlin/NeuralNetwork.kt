package neuralNetwork

import java.util.Collections.shuffle

/**
 * Red neuronal.
 * @author Ignacio Slater Muñoz
 */
class NeuralNetwork (private val epoch: Int) {

  /**
   * Clase utilizada para almacenar información necesaria para entrenar la red.
   *
   * @param inputs Valores entregados a la red.
   * @param answer Output esperado para la red.
   */
  internal data class Trainer(val inputs: List<Double>, val answer: List<Double>)

  val precision = mutableListOf<Double>()
  internal val trainers = mutableListOf<Trainer>()
  private var outputs: Int = 0
  var successRate = 0.0

  private lateinit var layers: Array<NeuronLayer?>

  /**
   * Agrega un entrenador a la red.
   *
   * @param inputs Valores que se entregarán a la red.
   * @param answer Output esperado para el input.
   */
  fun addTrainer(inputs: DoubleArray, answer: DoubleArray) {
    trainers.add(Trainer(inputs.toList(), answer.toList()))
  }

  /**
   * Construye una red neuronal de acuerdo a un número de neurones.
   *
   * @param numberOfInputs  Cantidad de inputs que recibe la red.
   * @param neuronsPerLayer Cantidad de neurones por capa.
   */
  fun build(numberOfInputs: Int, vararg neuronsPerLayer: Int) {
    val size = neuronsPerLayer.size
    assert(size > 0) { "Network should have at least 1 layer." }

    layers = arrayOfNulls(size) // Se crea una red vacía

    layers[0] = NeuronLayer(neuronsPerLayer[0], null)
    layers[0]!!.build(numberOfInputs)
    for (i in 1 until size) {
      layers[i] = NeuronLayer(neuronsPerLayer[i], layers[i - 1])
      layers[i]!!.build(neuronsPerLayer[i - 1])
      layers[i - 1]!!.nextLayer = layers[i]
    }
  }

  /**
   * Normaliza un valor al rango [0, 1].
   *
   * @param x   Valor a normalizar.
   * @param min Valor mínimo que puede tomar x.
   * @param max Valor máximo que puede tomar x.
   */
  fun normalize(x: Double, min: Double, max: Double) = (x - min) / (max - min)

  /**
   * Hace una predicción.
   *
   * @param inputs Valores entregados a la red.
   * @return Entero que representa la categoría predicha para los datos.
   */
  fun predict(inputs: DoubleArray): Int {
    val outputs = feedForward(inputs)
    return outputs.indexOf(outputs.max()!!)
  }

  /**
   * Entrena la red.
   */
  fun train(epoch: Int = this.epoch) {
    for (i in 0 until epoch) {
      //print(".")
      var correctGuess = 0
      shuffle(trainers)  // This way the trainings aren't always in the same order.
      for ((inputs, answer) in trainers) {
        train(inputs.toDoubleArray(), answer.toDoubleArray())
        if (outputs == answer.indexOf(answer.max())) correctGuess++
      }
      precision.add(correctGuess.toDouble() / trainers.size * 100)
    }
    //println()
  }

  /**
   * Ajusta los parámetros de la red mediante backpropagation.
   *
   * @param expectedOutput Output esperado para la red.
   */
  private fun backPropagateError(expectedOutput: DoubleArray) {
    layers.last()!!.backPropagateError(expectedOutput)
  }

  /**
   * Entrega un input a la primera capa de la red.
   *
   * @param inputs Valores que se le entregarán a la primera capa.
   */
  private fun feedForward(inputs: DoubleArray): DoubleArray {
    return layers[0]!!.feed(inputs)
  }

  /**
   * Entrena la red.
   *
   * @param inputs         Valores que se le entregarán a la red.
   * @param expectedOutput Output esperado de la red.
   */
  private fun train(inputs: DoubleArray, expectedOutput: DoubleArray) {
    outputs = predict(inputs)
    backPropagateError(expectedOutput)
    updateWeight(inputs)
  }

  /**
   * Actualiza los pesos de las neuronas utilizando un input inicial.
   *
   * @param initialInputs Valores que se le entregan a la red.
   */
  private fun updateWeight(initialInputs: DoubleArray) {
    layers[0]!!.updateWeight(initialInputs)
  }
}

/**
 *
 */
private class NeuronLayer(numberOfNeurons: Int, private var previousLayer: NeuronLayer?) {
  private var neurons = arrayOfNulls<Neuron>(numberOfNeurons)
  private val outputs = DoubleArray(numberOfNeurons)
  internal var nextLayer: NeuronLayer? = null

  /**
   * Ajusta los parámetros de las capas mediante backpropagation.
   * Método recursivo.
   *
   * @param expectedOutput Output esperado para la capa.
   */
  internal fun backPropagateError(expectedOutput: DoubleArray) {
    for ((i, neuron) in neurons.withIndex()) {
      val error = expectedOutput[i] - neuron!!.output
      neuron.adjustDeltaWith(error)
    }
    previousLayer?.backPropagateError()
  }

  /**
   *  Entrega un input a una capa de neurons.
   *
   *  @param inputs Valores que se le entregarán a cada neuron de la capa.
   */
  internal fun feed(inputs: DoubleArray): DoubleArray {
    for ((index, neuron) in neurons.withIndex()) outputs[index] = neuron!!.feed(inputs)
    return nextLayer?.feed(outputs) ?: outputs
  }

  /**
   * Actualiza los pesos de las neuronas utilizando un arreglo de inputs iniciales.
   * Se asume que quien recibe el mensaje es la primera capa oculta.
   *
   * @param initialInputs Valores que se le entregan a la red.
   */
  internal fun updateWeight(initialInputs: DoubleArray) {
    val inputs: DoubleArray
    val learningRate = 0.05
    if (previousLayer == null) inputs = initialInputs
    else {
      val previousOutput = DoubleArray(previousLayer!!.neurons.size)
      for ((i, neuron) in previousLayer!!.neurons.withIndex()) previousOutput[i] = neuron!!.output
      inputs = previousOutput
    }
    for (n in neurons) {
      n!!.adjustWeightWithInput(inputs, learningRate)
      n.adjustBiasUsingLearningRate(learningRate)
    }
    nextLayer?.updateWeight(initialInputs)
  }

  /**
   * Ajusta los parámetros de las capas mediante backpropagation.
   * Método recursivo.
   */
  private fun backPropagateError() {
    for ((j, neuron) in neurons.withIndex()) {
      var error = 0.0
      for (nextNeuron in nextLayer!!.neurons) {
        error += (nextNeuron!!.weights[j] * nextNeuron.delta)
        neuron!!.adjustDeltaWith(error)
      }
    }
    previousLayer?.backPropagateError()
  }

  internal fun build(numberOfWeights: Int) {
    for (i in neurons.indices) neurons[i] = Neuron(numberOfWeights)
  }
}

/**
 * Esta clase representa un neuron, la unidad básica de una red neuronal.
 * Neuron(n) crea un neuron con n pesos aleatorios.
 *
 * @param numberOfWeights Número de pesos que posee el neuron.
 * @author Ignacio Slater Muñoz
 */
private class Neuron(numberOfWeights: Int) {

  var output: Double = 0.0
    private set
  var weights = DoubleArray(numberOfWeights)
    private set
  var delta = 0.0
    private set
  private var bias = 1.0

  init {
    // Los pesos serán valores aleatorios entre -1 y 1
    for (i in 0 until numberOfWeights) {
      do {
        weights[i] = Math.random() * 2 - 1
      } while (weights[i] == 0.0)
    }
  }

  internal fun feed(inputs: DoubleArray): Double {
    assert(inputs.size == weights.size) { "Weights and input length doesn't match" }
    val wx = weights.indices.sumByDouble { weights[it] * inputs[it] }
    output = 1 / (1 + Math.exp(-wx - bias))
    return output
  }

  /**
   * Ajusta el bias de la neurona de acuerdo a su tasa de aprendizaje.
   *
   * @param learningRate Tasa de aprendizaje.
   */
  internal fun adjustBiasUsingLearningRate(learningRate: Double) {
    bias += learningRate * delta
  }

  /**
   * Ajusta el delta de la neurona de acuerdo a un error.
   */
  internal fun adjustDeltaWith(error: Double) {
    delta = error * transferDerivative(output)
  }

  /**
   * Ajusta los pesos de una neurona de acuerdo a un input y a su tasa de aprendizaje.
   *
   * @param inputs       Valores entregados a la neurona.
   * @param learningRate Tasa de aprendizaje.
   */
  internal fun adjustWeightWithInput(inputs: DoubleArray, learningRate: Double) {
    for ((index, anInput) in inputs.withIndex()) weights[index] += (learningRate * delta * anInput)
  }

  /**
   * Calcula la derivada de la función de transferencia.
   *
   * @param output Valor respecto al cual se quiere calcular la derivada.
   */
  private fun transferDerivative(output: Double) = output * (1.0 - output)
}
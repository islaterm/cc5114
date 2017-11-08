import neuralNetwork.NeuralNetwork
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Prueba la red neuronal para distintos operadores lógicos.
 *
 * @author Ignacio Slater Muñoz
 */
class NeuralNetworkTest {

  private lateinit var network: NeuralNetwork

  @Before
  fun setUp() {
    network = NeuralNetwork(10000)
    network.build(2, 5, 2)
  }

  @Test
  fun andTest() {
    assertEquals(0, network.trainers.size)
    network.addTrainer(doubleArrayOf(0.0, 0.0), doubleArrayOf(1.0, 0.0))
    network.addTrainer(doubleArrayOf(0.0, 1.0), doubleArrayOf(1.0, 0.0))
    network.addTrainer(doubleArrayOf(1.0, 0.0), doubleArrayOf(1.0, 0.0))
    network.addTrainer(doubleArrayOf(1.0, 1.0), doubleArrayOf(0.0, 1.0))
    assertEquals(4, network.trainers.size)

    network.train()
    assertEquals(0, network.predict(doubleArrayOf(0.0, 0.0)))
    assertEquals(0, network.predict(doubleArrayOf(0.0, 1.0)))
    assertEquals(0, network.predict(doubleArrayOf(1.0, 0.0)))
    assertEquals(1, network.predict(doubleArrayOf(1.0, 1.0)))
  }

  @Test
  fun orTest() {
    network.addTrainer(doubleArrayOf(0.0, 0.0), doubleArrayOf(1.0, 0.0))
    network.addTrainer(doubleArrayOf(0.0, 1.0), doubleArrayOf(0.0, 1.0))
    network.addTrainer(doubleArrayOf(1.0, 0.0), doubleArrayOf(0.0, 1.0))
    network.addTrainer(doubleArrayOf(1.0, 1.0), doubleArrayOf(0.0, 1.0))

    network.train()
    assertEquals(0, network.predict(doubleArrayOf(0.0, 0.0)))
    assertEquals(1, network.predict(doubleArrayOf(0.0, 1.0)))
    assertEquals(1, network.predict(doubleArrayOf(1.0, 0.0)))
    assertEquals(1, network.predict(doubleArrayOf(1.0, 1.0)))
  }

  @Test
  fun xorTest() {
    network.addTrainer(doubleArrayOf(0.0, 1.0), doubleArrayOf(0.0, 1.0))
    network.addTrainer(doubleArrayOf(1.0, 0.0), doubleArrayOf(0.0, 1.0))
    network.addTrainer(doubleArrayOf(1.0, 1.0), doubleArrayOf(1.0, 0.0))
    network.addTrainer(doubleArrayOf(0.0, 0.0), doubleArrayOf(1.0, 0.0))

    network.train()
    assertEquals(0, network.predict(doubleArrayOf(0.0, 0.0)))
    assertEquals(1, network.predict(doubleArrayOf(0.0, 1.0)))
    assertEquals(1, network.predict(doubleArrayOf(1.0, 0.0)))
    assertEquals(0, network.predict(doubleArrayOf(1.0, 1.0)))
  }

  @Test
  fun implyTest() {
    network.addTrainer(doubleArrayOf(0.0, 1.0), doubleArrayOf(0.0, 1.0))
    network.addTrainer(doubleArrayOf(1.0, 0.0), doubleArrayOf(1.0, 0.0))
    network.addTrainer(doubleArrayOf(1.0, 1.0), doubleArrayOf(0.0, 1.0))
    network.addTrainer(doubleArrayOf(0.0, 0.0), doubleArrayOf(0.0, 1.0))

    network.train()
    assertEquals(1, network.predict(doubleArrayOf(0.0, 0.0)))
    assertEquals(1, network.predict(doubleArrayOf(0.0, 1.0)))
    assertEquals(0, network.predict(doubleArrayOf(1.0, 0.0)))
    assertEquals(1, network.predict(doubleArrayOf(1.0, 1.0)))
  }
}
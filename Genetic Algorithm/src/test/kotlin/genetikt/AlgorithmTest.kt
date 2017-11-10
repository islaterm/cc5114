package genetikt

/*import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * @author  Ignacio Slater Muñoz
 * @version 1.0
 * @since   1.0
 */
class AlgorithmTest {
  private lateinit var population: Population

  @Before
  fun setUp() {
    population = Population()
  }

  @Test
  fun evolutionTest() {
    val target1 = "IT'S A SECRET TO EVERYBODY."
    population.target = target1
    population.size = 100
    population.build()
    while (population.getFittest()!!.getFitness(target1.toCharArray()) < 1) {
      population.evolve()
    }
    assertTrue(population.getFittest().toString() == target1)

    val target2 = "010100110100010101000011010100100100010101010100"
    population.target = target2
    population.size = 500
    population.build()
    while (population.getFittest()!!.getFitness(target2.toCharArray()) < 1) {
      population.evolve()
    }
    assertTrue(population.getFittest().toString() == target2)
  }
}*/
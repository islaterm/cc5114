package genetikt

import genetikt.chromosome.CharChromosomeFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @version 1.1
 * @since   1.1
 */
class PopulationTest {

  private lateinit var population: Population

  @Test
  fun evolutionTest() {
    val target = "It's a secret to everybody."
    var factory = IndividualFactory(
        CharChromosomeFactory(target.length, target),
        mutationRate = 0.03
    )
    population = Population(50, factory)
    var fittest = population.getFittest()
    while (fittest.fitness[0] < target.length) {
      population.evolve()
      fittest = population.getFittest()
    }
    assertEquals("It's a secret to everybody.", fittest.toString())

    val target1 = "It's a dangerous business"
    val target2 = "Frodo"
    val target3 = "going out your door."
    factory = IndividualFactory(
        CharChromosomeFactory(target1.length, target1),
        CharChromosomeFactory(target2.length, target2),
        CharChromosomeFactory(target3.length, target3),
        mutationRate = 0.05
    )
    population = Population(100, factory)
    fittest = population.getFittest()

    val expected = target1.length + target2.length + target3.length
    var actual = fittest.fitness.sum()

    while (actual < expected) {
      population.evolve()
      fittest = population.getFittest()
      actual = fittest.fitness.sum()
    }
    assertEquals("It's a dangerous business, Frodo, going out your door.", fittest.toString())
  }
}
package genetikt

import genetikt.chromosome.CharChromosome
import genetikt.gene.CharGene
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@Suppress("UNUSED_PARAMETER")
/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @version 1.1
 * @since   1.1
 */
class IndividualTest {

  private lateinit var ind1: Individual
  private lateinit var ind2: Individual
  private lateinit var ind3: Individual

  @Test
  fun equalsTest() {
    val chromosome = CharChromosome(1, aTarget = "a")
    ind1 = Individual(chromosome, mutationRate = 0.03)
    ind2 = Individual(chromosome, mutationRate = 0.03)
    assertTrue(ind1 == ind2)
    val chromosome2 = CharChromosome(1, aTarget = "a")
    chromosome2.genes[0] = CharGene("ABC")
    ind2 = Individual(chromosome2, mutationRate = 0.03)
    assertFalse(ind1 == ind2)
  }

  @Test
  fun compareToTest() {
    ind1 = Individual(chromosomes = CharChromosome(4), mutationRate = 0.03) { fitness1(it) }
    ind2 = Individual(chromosomes = CharChromosome(4), mutationRate = 0.03) { fitness2(it) }
    ind3 = Individual(chromosomes = CharChromosome(4), mutationRate = 0.03) { fitness3(it) }

    val list = listOf(ind1, ind2, ind3).sorted()
    // Elements get sorted from lowest fitness to highest.
    assertTrue(list[0] == ind3)
    assertTrue(list[1] == ind1)
    assertTrue(list[2] == ind2)
  }

  private fun fitness1(individual: Individual) = doubleArrayOf(2.0, 2.0, 1.0)

  private fun fitness2(individual: Individual) = doubleArrayOf(2.0, 2.0, 2.0)

  private fun fitness3(individual: Individual) = doubleArrayOf(1.0, 3.0, 4.0)
}
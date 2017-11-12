package genetikt.chromosome

import genetikt.gene.CharGene
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @version 1.1
 * @since 1.1
 */
class CharChromosomeTest {

  @Test
  fun equalsTest() {
    val c1 = CharChromosome(2)
    c1.genes[0] = CharGene('A', "ABC")
    c1.genes[1] = CharGene('B', "ABC")
    val c2 = CharChromosome(2)
    c2.genes[0] = CharGene('A', "ABC")
    c2.genes[1] = CharGene('B', "ABC")
    assertTrue(c1 == c2)
    c2.genes[0] = CharGene('C', "ABC")
    assertFalse(c1 == c2)
  }

  @Test
  fun copyTest() {
    val c1 = CharChromosome(4)
    val c2 = c1.copy()
    assertTrue(c1 == c2)
  }
}
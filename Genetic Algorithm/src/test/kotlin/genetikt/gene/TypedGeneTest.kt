package genetikt.gene

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class TypedGeneTest {

  private lateinit var gene1: TypedGene<Int>
  private lateinit var gene2: TypedGene<Int>
  private lateinit var gene3: TypedGene<Int>
  private lateinit var gene4: TypedGene<Int>

  @Before
  fun setUp() {
    gene1 = TypedGene(hashMapOf("1" to 1, "2" to 2, "3" to 3))
    gene2 = TypedGene("1", hashMapOf("1" to 1, "2" to 2, "3" to 3))
    gene3 = TypedGene("1", hashMapOf("1" to 1, "2" to 2, "3" to 3))
    gene4 = TypedGene("1", hashMapOf("1" to 1))
  }

  @Test
  fun geneTest() {
    assertTrue(hashMapOf("1" to 1, "2" to 2, "3" to 3) == gene1.alphabet)
    assertTrue(gene2 == gene3)
    assertFalse(gene2 == gene4)
  }
}
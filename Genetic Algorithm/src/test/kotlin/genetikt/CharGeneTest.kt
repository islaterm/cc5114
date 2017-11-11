package genetikt

import com.sun.corba.se.impl.io.TypeMismatchException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class CharGeneTest {

  private lateinit var gene1: CharGene
  private lateinit var gene2: CharGene
  private lateinit var gene3: CharGene
  private lateinit var gene4: CharGene
  private lateinit var gene5: CharGene

  @Before
  fun setUp() {
    gene1 = CharGene("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !" +
        "\"%\$&/()=?`{[]}\\+~*#';.:,-_<>|@^'")
    gene2 = CharGene('A', "A")
    gene3 = CharGene('A', "A")
    gene4 = CharGene('A', "ABC")
    gene5 = CharGene('あ', "A")
  }

  @Test
  fun propertiesTest() {
    assertEquals(
        "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !\"%\$&/()=?`{[]" +
            "}\\+~*#';.:,-_<>|@^'",
        gene1.alphabet)
    assertEquals("ABC", gene4.alphabet)
    assertTrue(gene1.isValid)
    assertFalse(gene5.isValid)
  }

  @Test
  fun equalsTest() {
    assertTrue(gene2 == gene3)
    assertFalse(gene2 == gene4)
  }

  @Test
  fun copyTest() {
    gene1.copyTo(gene5)
    assert(gene1 == gene5)
  }

  @Test(expected = TypeMismatchException::class)
  fun copyExceptionTest() {
    val other = TypedGene(mapOf(Pair("1", 1)))
    gene1.copyTo(other)
  }
}
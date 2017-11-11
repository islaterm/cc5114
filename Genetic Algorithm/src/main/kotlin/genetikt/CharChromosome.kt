package genetikt

import java.lang.StringBuilder
import java.util.*

/**
 * Chromosome that represents a sequence of characters.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class CharChromosome : IChromosome<CharGene> {

//region Properties
  /** Set of valid characters for the genes of the chromosome. */
  private val alphabet: String

  /** Genes that make up the chromosome. */
  override val genes: Array<CharGene>
  /** Target string of the chromosome. */
  override val target: Array<CharGene>
//endregion

//region Constructors
  /**
   * Create a new chromosome of the given `size`.
   *
   * @param size
   *    Number of genes in the chromosome.
   * @param aTarget
   *    **(Optional)** Target string of the chromosome.
   *    By default the chromosome has no target (target's an empty string).
   * @param alphabet
   *    **(Optional)** Set of valid characters that can take every gene.
   *    By default
   *    `0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !"%$&/()=?`{[]}\+~*#';.:,-_<>|@^'`.
   */
  constructor(
      size: Int,
      aTarget: String = "",
      alphabet: String = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !\"%\$&/()=?`{[]}\\+~*#';.:,-_<>|@^'"
  ) : this(size, aTarget, alphabet, null)

  /**
   * Secondary constructor.
   * Create a new chromosome from a given `genes` array.
   */
  private constructor(
      size: Int,
      aTarget: String,
      alphabet: String,
      genes: Array<CharGene>?
  ) {
    this.alphabet = alphabet
    this.genes =
        if (genes != null) Array(size) { i -> genes[i] }
        else Array(size) { CharGene(alphabet) }
    this.target = Array(aTarget.length) { i -> CharGene(aTarget[i], alphabet) }
  }
//endregion

  override fun mutate(mutationRate: Double) {
    val rand = Random()
    for (i in 0 until size)
      if (rand.nextDouble() < mutationRate) genes[i] = CharGene(alphabet)
  }

  override fun copy(): CharChromosome {
    val genesCopy = Array(genes.size) { i -> genes[i].copy() }
    return CharChromosome(size, toString(target), alphabet, genesCopy)
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?): Boolean {
    if (other !is CharChromosome) return false
    if (other.size != this.size) return false
    return (0 until size).none { genes[it] != other.genes[it] }
  }

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int {
    var result = Arrays.hashCode(genes)
    result = 31 * result + Arrays.hashCode(target)
    return result
  }

  override fun toString(): String {
    val sb = StringBuilder()
    for (gene in genes) sb.append(gene.toString())
    return sb.toString()
  }

  private fun toString(target: Array<CharGene>): String {
    val sb = StringBuilder()
    for (gene in target) sb.append(gene.toString())
    return sb.toString()
  }
}
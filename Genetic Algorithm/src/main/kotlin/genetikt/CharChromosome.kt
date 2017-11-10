package genetikt

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
  private val alphabet: String

  /** Genes that make up the chromosome. */
  override val genes: Array<CharGene>
  /** Target string of the chromosome. */
  private val target: Array<CharGene>

  /**
   * Fitness function.
   * By default it's defined as how "_close_" the chromosome is to the target.
   *
   * @return
   *    Fitness of the chromosome.
   */
  override var fitness: () -> Double
    private set
  //endregion

  //region constructors
  /**
   * @param size
   *    Number of genes in the chromosome.
   * @param aTarget
   *    **(Optional)** Target string of the chromosome.
   *    By default the chromosome has no target (target's an empty string).
   * @param fitnessFunction
   *    **(Optional)** Function that calculates the fitness of the chromosome.
   *     By default it's defined as how close is the chromosome to the target (number of equal
   *     characters).
   * @param alphabet
   *    **(Optional)** Set of valid characters that can take every gene.
   *    By default
   *    `0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !"%$&/()=?`{[]}\+~*#';.:,-_<>|@^'`.
   */
  constructor(
      size: Int,
      aTarget: String = "",
      fitnessFunction: (() -> Double)? = null,
      alphabet: String = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !\"%\$&/()=?`{[]}\\+~*#';.:,-_<>|@^'"
  ) : this(size, aTarget, fitnessFunction, alphabet, null)

  /** Secondary constructor */
  private constructor(
      size: Int,
      aTarget: String,
      fitnessFunction: (() -> Double)?,
      alphabet: String,
      genes: Array<CharGene>?
  ) {
    this.alphabet = alphabet
    this.genes =
        if (genes != null) Array(size) { i -> genes[i] }
        else Array(size) { CharGene(alphabet) }
    this.target = Array(aTarget.length) { i -> CharGene(aTarget[i], alphabet) }
    fitness = fitnessFunction ?: {
      assert(target.size == size) { "Target must be equal in size to chromosome." }
      var score = 0.0
      (0 until target.size)
          .filter { target[it] == genes!![it] }
          .forEach { score++ }
      score / target.size
    }
  }
  //endregion

  override fun mutate(mutationRate: Double) {
    val rand = Random()
    for (i in 0 until size)
      if (rand.nextDouble() < mutationRate) genes[i] = CharGene(alphabet)
  }

  override fun copy() = CharChromosome(size, target.joinToString { "" }, fitness, alphabet, genes)

  override fun equals(other: Any?): Boolean {
    if (other !is CharChromosome) return false
    if (other.size != this.size) return false
    return (0 until size).none { genes[it] != other.genes[it] }
  }

  override fun hashCode(): Int {
    var result = Arrays.hashCode(genes)
    result = 31 * result + Arrays.hashCode(target)
    result = 31 * result + fitness.hashCode()
    return result
  }
}
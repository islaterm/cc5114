package genetikt

/**
 * Chromosome that represents a sequence of characters.
 * The constructor creates a chromosome of `CharGenes` with a given size.
 *
 * @param size
 *    Number of genes in the chromosome.
 * @param aTarget
 *    **(Optional)** Target string of the chromosome.
 *    By default the chromosome has no target (target's an empty string).
 * @param fitnessFunction
 *    **(Optional)** Function that calculates the fitness of the chromosome.
 *     By default it's defined as how close is the chromosome to the target (number of equal
 *     characters).
 * @param anAlphabet
 *    **(Optional)** Set of valid characters that can take every gene.
 *    Default alphabet is defined in `CharGene`.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class CharChromosome(
    size: Int,
    aTarget: String = "",
    fitnessFunction: (() -> Double)? = null,
    anAlphabet: String? = null
) : IChromosome<CharGene> {

  //region Properties
  /** Genes that make up the chromosome. */
  override val genes =
      if (anAlphabet == null) Array(size) { CharGene() }
      else Array(size) { CharGene(anAlphabet) }

  /** Target string of the chromosome. */
  private val target = Array(aTarget.length) { i -> CharGene(aTarget[i]) }

  /**
   * Fitness function.
   * By default it's defined as how "_close_" the chromosome is to the target.
   *
   * @return
   *    Fitness of the chromosome.
   */
  override var fitness: () -> Double = {
    assert(target.size == size) { "Target must be equal in size to chromosome." }
    var score = 0.0
    (0 until target.size)
        .filter { target[it] == genes[it] }
        .forEach { score++ }
    score / target.size
  }
    private set
  //endregion

  init {
    if (fitnessFunction != null) fitness = fitnessFunction
  }
}
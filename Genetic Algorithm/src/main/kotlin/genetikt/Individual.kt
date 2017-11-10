package genetikt

import java.util.*

/**
 * Individual of a population, it is made of a set of chromosomes.
 *
 * @param chromosomes
 *    Chromosomes that make the genotype of the individual.
 * @param fitness
 *    **(Optional)** Fitness function.
 *    By default it's defined as the sum of the fitnesses of the chromosomes of the individual.
 * @property  mutationRate
 *    Mutation rate of the genes of the individual.
 * @constructor
 *    Creates an individual from a set of particular chromosomes.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class Individual(
    vararg chromosomes: IChromosome<*>,
    private val mutationRate: Double,
    fitness: (() -> DoubleArray)? = null
) : Comparable<Individual> {

  //region Properties
  /** Number of chromosomes of the individual. */
  internal val size: Int = chromosomes.size

  /**
   * Fitness function.
   * By default it's defined as the sum of the fitnesses of the chromosomes of the individual.
   *
   * @return
   *    Fitness of the individual.
   */
  internal var getFitness: () -> DoubleArray = {
    doubleArrayOf(genotype.sumByDouble { it.fitness() })
  }

  /** Array of chromosomes of the individual. */
  internal var genotype: Array<IChromosome<*>>

  /** Fitness of the individual. */
  private var fitness: DoubleArray
  //endregion

  init {
    genotype = Array(chromosomes.size) { i -> chromosomes[i] }
    if (fitness != null) getFitness = fitness
    this.fitness = getFitness()
  }

  //region Public functions
  /**
   * Compares this object with the specified object for order. Returns zero if this object is equal
   * to the specified [other] object, a negative number if it's less than [other], or a positive number
   * if it's greater than [other].
   */
  override fun compareTo(other: Individual): Int {
    for (i in 0 until fitness.size) {
      return if (fitness[i] == other.fitness[i])
        continue
      else if (fitness[i] > other.fitness[i])
        1
      else
        -1
    }
    return 0
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Individual) return false
    if (other.size != this.size) return false
    return (0 until size).none { genotype[it] != other.genotype[it] }
  }

  override fun hashCode(): Int {
    var result = Arrays.hashCode(fitness)
    result = 31 * result + size
    result = 31 * result + getFitness.hashCode()
    result = 31 * result + Arrays.hashCode(genotype)
    return result
  }
  //endregion

  //region Internal definitions

  /**
   * Mutates the individual according to it's mutation rate.
   */
  internal fun mutate() {
    for (chromosome in genotype) chromosome.mutate(mutationRate)
  }
  //endregion
}
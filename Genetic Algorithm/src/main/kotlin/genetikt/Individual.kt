package genetikt

import java.lang.StringBuilder
import java.util.*

/**
 * Individual of a population, it is made of a set of chromosomes.
 *
 * @param chromosomes
 *    Chromosomes that make the genotype of the individual.
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
    internal val mutationRate: Double
) : Comparable<Individual> {

//region Properties
  /** Number of chromosomes of the individual. */
  internal val size: Int = chromosomes.size

  /** Array of chromosomes of the individual. */
  internal var genotype = Array(chromosomes.size) { i -> chromosomes[i] }

  /** Fitness of the individual. */
  var fitness: DoubleArray = doubleArrayOf(0.0)
//endregion

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

  override fun toString(): String {
    val sb = StringBuilder()
    for (i in 0 until size) {
      sb.append(genotype[i].toString())
      if (i < size - 1) sb.append(", ")
    }
    return sb.toString()
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Individual) return false
    if (other.size != this.size) return false
    return (0 until size).none { genotype[it] != other.genotype[it] }
  }

  override fun hashCode(): Int {
    var result = Arrays.hashCode(fitness)
    result = 31 * result + size
    result = 31 * result + Arrays.hashCode(genotype)
    return result
  }
//endregion

  /**
   * Mutates the individual according to it's mutation rate.
   */
  internal fun mutate() {
    for (chromosome in genotype) chromosome.mutate(mutationRate)
  }
}
package genetikt

import java.util.*

/**
 * @param size
 *    Number of individuals of the population.
 * @param individualFactory
 *    Factory from which the individuals are going to be created.
 * @property  mutationRate
 *    Probability with which an individual will mutate.
 * @constructor Creates a population of individuals using a factory.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class Population(
    size: Int,
    individualFactory: IndividualFactory,
    private val mutationRate: Double = 0.03
) {

  private val rand = Random()

  /**
   * List of the individuals that make the population.
   * The list is always sorted from lower to higher fitness.
   */
  private var individuals = mutableListOf<Individual>()

  init {
    for (i in 0 until size) {
      individuals.add(individualFactory.build())
    }
    individuals.sort()
  }

  /**
   * Evolves the population to a next generation.
   */
  fun evolve() {
    val childrens = mutableListOf<Individual>()
    while (childrens.size < individuals.size) {
      val parent1 = tournamentSelection()
      val parent2 = tournamentSelection()
      val child = crossover(parent1, parent2)
      child.mutate()
      childrens.add(child)
    }
    individuals = childrens
  }

  //region Private functions
  /** Generates an offspring from 2 parents. */
  private fun crossover(ind1: Individual, ind2: Individual): Individual {
    assert(ind1.size == ind2.size) {
      "Size of the individuals doesn't match. Can't do a crossover."
    }
    val offspringGenotype = Array(ind1.size) { i -> crossover(ind1.genotype[i], ind2.genotype[i]) }
    return Individual(*offspringGenotype, mutationRate = mutationRate, fitness = ind1.getFitness)
  }

  private fun crossover(chrom1: IChromosome<*>, chrom2: IChromosome<*>): IChromosome<*> {
    assert(chrom1.size == chrom2.size) {
      "Size of the chromosomes doesn't match. Can't do a crossover."
    }
    assert(chrom1::class == chrom2::class) {
      "Chromosome classes doesn't match. Can't do a crossover."
    } //  idea Change assertions for chrom1.isCompatibeWith(chrom2)
    val offspring = chrom1.copy()
    val mixingPoint = rand.nextInt(offspring.size)

    (0 until offspring.size)
        .filter { it > mixingPoint }
        .forEach { chrom2.genes[it].copyTo(offspring.genes[it]) }
    return offspring
  }

  /** Selects a random individual prioritizing the ones with greater fitness. */
  private fun tournamentSelection(): Individual {
    val index = maxOf(rand.nextInt(individuals.size), rand.nextInt(individuals.size))
    // The individual with higher index has higher fitness.
    return individuals[index]
  }
  //endregion
}
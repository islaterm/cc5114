package genetikt

import java.util.*

/**
 * A population es a set of individuals.
 *
 * @param size
 *    Number of individuals of the population.
 * @param individualFactory
 *    Factory from which the individuals are going to be created.
 * @constructor
 *    Creates a population of individuals using a factory.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class Population(
    size: Int,
    individualFactory: IndividualFactory
) {

  private val rand = Random()

  /**
   * List of the individuals that make the population.
   * The list is always sorted from lower to higher fitness.
   */
  private var individuals = mutableListOf<Individual>()

  init {
    for (i in 0 until size) {
      val ind = individualFactory.build()
      fitness(ind)
      individuals.add(ind)
    }
    individuals.sort()
  }

  /**
   * Evolves the population to a next generation.
   */
  fun evolve() {
    val childrens = mutableListOf<Individual>()
    val survivors = individuals.size / 4
    var i = 0
    while (childrens.size < individuals.size) {
      if (i < individuals.size - survivors - 1) {
        val parent1 = tournamentSelection()
        val parent2 = tournamentSelection()
        val mixingPoint = rand.nextInt(parent1.size)

        val child1 = crossover(parent1, parent2, mixingPoint)
        child1.mutate()
        fitness(child1)
        childrens.add(child1)

        val child2 = crossover(parent2, parent1, mixingPoint)
        child2.mutate()
        fitness(child2)
        childrens.add(child2)
        i += 2
      } else {
        childrens.add(individuals[i++])
      }
    }
    individuals = childrens
    individuals.sort()
  }

  /** Getter the fittest individual. */
  fun getFittest() = individuals.last()

  //region Private functions
  /** Generates an offspring from 2 parents. */
  private fun crossover(ind1: Individual, ind2: Individual, mixingPoint: Int): Individual {
    assert(ind1.size == ind2.size) {
      "Size of the individuals doesn't match. Can't do a crossover."
    }
    val offspringGenotype = Array(ind1.size) { i ->
      crossover(ind1.genotype[i], ind2.genotype[i], mixingPoint)
    }
    return Individual(
        *offspringGenotype,
        mutationRate = ind1.mutationRate
    )
  }

  /** Generates a new chromosome by doing a crossover of two parents. */
  private fun crossover(chrom1: IChromosome<*>, chrom2: IChromosome<*>,
      mixingPoint: Int): IChromosome<*> {
    val offspring = chrom1.copy()

    (0 until offspring.size)
        .filter { it > mixingPoint }
        .forEach { chrom2.genes[it].copyTo(offspring.genes[it]) }
    return offspring
  }

  private fun fitness(individual: Individual) {
    val fit = individual.genotype
        .map { chromosome ->
          (0 until chromosome.target.size)
              .count { chromosome.genes[it] == chromosome.target[it] }
        }
        .map { it.toDouble() }
    individual.fitness = fit.toDoubleArray()
  }

  /** Selects a random individual prioritizing the ones with greater fitness. */
  private fun tournamentSelection(): Individual {
    val index = maxOf(rand.nextInt(individuals.size), rand.nextInt(individuals.size))
    // The individual with higher index has higher fitness.
    return individuals[index]
  }
  //endregion
}
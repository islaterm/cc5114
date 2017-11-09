package genetikt

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class Individual {

  /**
   * Fitness function.
   * By default it's defined as the sum of the fitnesses of the chromosomes of the individual.
   *
   * @return
   *    Fitness of the individual.
   */
  var fitness: () -> Double = {
    genotype.sumByDouble { it.fitness() }
  }

  /** Array of chromosomes of the individual. */
  private var genotype: Array<IChromosome<*>>

  /**
   * Creates an individual with a uniform type of chromosomes.
   *
   * @param size
   *    Number of chromosomes of the genotype.
   * @param chromosome
   *    Type of chromosomes of the genotype.
   */
  constructor(size: Int, chromosome: IChromosome<*>) {
    genotype = Array(size) { chromosome }
  }

  /**
   * Creates an individual from particular chromosomes.
   *
   * @param chromosomes
   *    Chromosomes that make the
   */
  constructor(vararg chromosomes: IChromosome<*>) {
    genotype = Array(chromosomes.size) { i -> chromosomes[i] }
  }
}
package genetikt

/**
 * @property  factories
 *    Factories for creating chromosomes.
 * @property  mutationRate
 *    Mutation rate of the individual.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class IndividualFactory(
    private vararg val factories: IChromosomeFactory<*>,
    private val mutationRate: Double
) {

  /** Builds an individual. */
  fun build(): Individual {
    val genotype = Array(factories.size) { i -> factories[i].build() }
    return Individual(*genotype, mutationRate = mutationRate)
  }
}
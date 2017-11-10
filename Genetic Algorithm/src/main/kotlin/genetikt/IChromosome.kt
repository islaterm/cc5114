package genetikt

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
interface IChromosome<G : IGene<*>> {

  /** Genes that make up the chromosome. */
  val genes: Array<G>

  /** Number of genes in the chromosome. */
  val size: Int
    get() = genes.size

  /** Fitness of the chromosome. */
  val fitness: () -> Double

  /** Creates a copy of this chromosome. */
  fun copy(): IChromosome<G>

  /**
   * Mutates a chromosome according to it's mutation rate.
   *
   * @param mutationRate  Probability with which a gene will mutate.
   */
  fun mutate(mutationRate: Double)
}
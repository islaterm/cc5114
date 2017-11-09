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
}
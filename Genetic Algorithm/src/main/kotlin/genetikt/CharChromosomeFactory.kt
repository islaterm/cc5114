package genetikt

/**
 * Factory for creating instances of `CharChromosome`.
 *
 * @property  size
 *    Number of genes of the chromosome.
 * @property  aTarget
 *    **(Optional)** Target of the chromosome.
 * @property  fitnessFunction
 *    **(Optional)** Fitness function of the chromosome.
 * @property  anAlphabet
 *    **(Optional)** Alphabet of the chromosome.
 * @see CharChromosome
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class CharChromosomeFactory(
    private var size: Int,
    private var aTarget: String = "",
    private var fitnessFunction: (() -> Double)? = null,
    private var anAlphabet: String = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !\"%\$&/()=?`{[]}\\+~*#';.:,-_<>|@^'"
) : IChromosomeFactory<CharGene> {

  /** Builds a new `CharChromosome`. */
  override fun build(): CharChromosome {
    return CharChromosome(size, aTarget, fitnessFunction, anAlphabet)
  }
}
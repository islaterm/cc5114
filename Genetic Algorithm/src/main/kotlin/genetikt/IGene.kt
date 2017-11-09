package genetikt

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
interface IGene<out DNA> {

  /** DNA of this gene. */
  val dna: DNA
}


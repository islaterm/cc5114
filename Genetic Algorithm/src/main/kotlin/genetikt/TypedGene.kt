package genetikt

import java.util.*

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class TypedGene<DNA> : IGene<DNA> {

  //region Properties
  /** Dictionary that contains all the valid DNA sequences. */
  val alphabet: Map<String, DNA>

  /** DNA of this gene. */
  override val dna: DNA

  //endregion

  //region Constructors
  /**
   * Creates a random gene.
   *
   * @param anAlphabet
   *    Map containing all the valid values that can take a gene.
   */
  constructor(anAlphabet: Map<String, DNA>) {
    alphabet = anAlphabet
    val keys = ArrayList(alphabet.keys)
    dna = alphabet[keys[Random().nextInt(keys.size)]]!!
  }

  /**
   * Creates a gene from a key.
   *
   * @param key
   *    Name of the object this gene represents.
   * @param anAlphabet
   *    Map containing all the valid values that can take the gene.
   *    `key` must be a key contained in the dictionary.
   */
  constructor(key: String, anAlphabet: Map<String, DNA>) {
    alphabet = anAlphabet
    dna = anAlphabet[key]!!
  }
  //endregion

  /**
   * Checks if this gene is equal to another.
   *
   * @param other
   *    Gene to be checked.
   * @return
   *    `true` if the two genes are equal, `false` if not.
   */
  override fun equals(other: Any?): Boolean {
    return other is TypedGene<*> && this.dna?.equals(other.dna) == true
        && this.alphabet == other.alphabet
  }

  override fun hashCode(): Int {
    var result = alphabet.hashCode()
    result = 31 * result + (dna?.hashCode() ?: 0)
    return result
  }
}
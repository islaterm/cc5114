package genetikt

import java.util.*

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.1
 */
class CharGene : IGene<Char> {

  //region Properties
  /** Character set used by this gene. */
  val alphabet: String

  /** DNA of this gene. */
  override val dna: Char

  /** A character is valid if it's contained in the gene alphabet. */
  var isValid: Boolean
    private set
  //endregion

  //region Constructors
  /**
   * Creates a new gene from a random character.
   *
   * @param anAlphabet
   *    **(Optional)** Set of valid characters. By default:
   *    `0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !"%$&/()=?`{[]}\+~*#';.:,-_<>|@^'`.
   */
  constructor(anAlphabet: String = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXY" +
      "Z !\"%\$&/()=?`{[]}\\+~*#';.:,-_<>|@^'") {
    alphabet = anAlphabet
    dna = alphabet[Random().nextInt(alphabet.length)]
    isValid = true
  }

  /**
   * Creates a new gene from a character.
   *
   * @param char
   *    The character this gene represents.
   * @param anAlphabet
   *    **(Optional)** Set of valid characters. By default:
   *    `0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !"%$&/()=?`{[]}\+~*#';.:,-_<>|@^'`.
   */
  constructor(
      char: Char,
      anAlphabet: String = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !\"%\$" +
          "&/()=?`{[]}\\+~*#';.:,-_<>|@^'"
  ) {
    alphabet = anAlphabet
    dna = char
    isValid = alphabet.asSequence().contains(char)
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
    return other is CharGene && this.dna == other.dna && this.alphabet == other.alphabet
  }

  override fun hashCode(): Int {
    var result = alphabet.hashCode()
    result = 31 * result + dna.hashCode()
    result = 31 * result + isValid.hashCode()
    return result
  }
}
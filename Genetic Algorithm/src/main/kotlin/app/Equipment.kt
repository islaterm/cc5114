package app

/**
 * Creates an object that represents an item that can be equiped to the character.
 *
 * @param NAME  Name of the item.
 * @param PRICE Money required to buy the item.
 * @param TYPE  Type of the equipment.
 *              It can be `WEAPON`, `ARMOR`, `ORNAMENT`, `COSTUME` or `ACCESORY`.
 * @param HP    Hit points.
 * @param SP    Skill points.
 * @param STR   Strength.
 * @param VIT   Vitality.
 * @param INT   Inteligence.
 * @param MEN   Mentality.
 * @param AGI   Agility.
 * @param TEC   Technique.
 * @param LUK   Luck.
 * @param MOV   Movility.
 * @param FIR   Fire resistance.
 * @param WND   Wind resistance.
 * @param ICE   Ice resistance.
 * @param THD   Thunder resistance.
 *
 * @author  Ignacio Slater Muñoz
 * @version 1.0
 * @since   1.0
 */
private data class Equipment(
    private val NAME: String,
    private val PRICE: Int,
    private val TYPE: Type,
    private val HP: Int,
    private val SP: Int,
    private val STR: Int,
    private val VIT: Int,
    private val INT: Int,
    private val MEN: Int,
    private val AGI: Int,
    private val TEC: Int,
    private val LUK: Int,
    private val MOV: Int,
    private val FIR: Int,
    private val WND: Int,
    private val ICE: Int,
    private val THD: Int
)

/**
 * @author  Ignacio Slater Muñoz.
 * @version 1.0
 * @since   1.0
 */
private enum class Type {
  WEAPON,
  ARMOR,
  ORNAMENT,
  COSTUME,
  ACCESSORY
}
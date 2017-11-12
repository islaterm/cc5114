package app

/**
 * `Equipment` represents an item that can be equiped to a character.
 * Items stats are based on the ones found in the game _Hyperdimension Neptunia Re;Birth 1_.
 *
 * @constructor
 *    Creates an instance of `Equipment`.
 * @param name
 *    Name of the item.
 * @param price
 *    Money required to buy the item.
 * @param type
 *    EquipmentType of the equipment.
 *    It can be `WEAPON`, `ARMOR`, `ORNAMENT`, `COSTUME` or `ACCESORY`.
 * @param HP
 *    Hit points.
 * @param SP
 *    Skill points.
 * @param STR
 *    Strength.
 * @param VIT
 *    Vitality.
 * @param INT
 *    Inteligence.
 * @param MEN
 *    Mentality.
 * @param AGI
 *    Agility.
 * @param TEC
 *    Technique.
 * @param LUK
 *    Luck.
 * @param MOV
 *    Movility.
 * @param FIR
 *    Fire resistance.
 * @param WND
 *    Wind resistance.
 * @param ICE
 *    Ice resistance.
 * @param THD
 *    Thunder resistance.
 * @param inInventory
 *    **(Optional)** Indicates if the equipment is in the player's inventory.
 *    `false` by default.
 *
 * @author  Ignacio Slater Muñoz
 * @version 1.2
 * @since   1.0
 */
class Equipment(
    val name: String,
    internal val price: Int,
    private val type: EquipmentType,
    HP: Int,
    SP: Int,
    STR: Int,
    VIT: Int,
    INT: Int,
    MEN: Int,
    AGI: Int,
    TEC: Int,
    LUK: Int,
    MOV: Int,
    FIR: Int,
    WND: Int,
    ICE: Int,
    THD: Int,
    val inInventory: Boolean
) {

  val stats = intArrayOf(HP, SP, STR, VIT, INT, MEN, AGI, TEC, LUK, MOV, FIR, WND, ICE, THD)

  companion object {
    /** Represents an empty slot i.e. no equipment. */
    val EMPTY_SLOT = Equipment(name = "NONE", price = 0, type = EquipmentType.NONE, HP = 0, SP = 0,
        STR = 0, VIT = 0, INT = 0, MEN = 0, AGI = 0, TEC = 0, LUK = 0, MOV = 0, FIR = 0,
        WND = 0, ICE = 0, THD = 0,
        inInventory = true)
  }
}


/**
 * @author  Ignacio Slater Muñoz.
 * @version 1.2
 * @since   1.0
 */
enum class EquipmentType {

  WEAPON,
  ARMOR,
  ORNAMENT,
  COSTUME,
  ACCESSORY,
  NONE
}
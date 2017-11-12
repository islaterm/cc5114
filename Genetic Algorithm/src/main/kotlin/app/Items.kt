package app

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Set of equipment available for the player.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.2
 * @version 1.2
 */
class Items(aPath: String) {

  private val path = Paths.get(aPath)
  private val items = Array(5) { hashMapOf(Pair("NONE", Equipment.EMPTY_SLOT)) }
  private val maxArray = Array(14) { Int.MIN_VALUE }
  private val minArray = Array(14) { Int.MAX_VALUE }

  val weapons = items[0]
  val armor = items[1]
  val ornament = items[2]
  val costume = items[3]
  val accessory = items[4]

  init {
    val lines = Files.readAllLines(path)
    for (i in 1 until lines.size) {
      val line = lines[i].split(",")
      if (line.size != 18) continue // skips a line if it's invalid.

      val name = line[0].trim()
      val price = line[1].trim().toInt()
      val type = EquipmentType.valueOf(line[2].trim())

      val hp = line[3].trim().toInt()
      if (hp > maxArray[0]) maxArray[0] = hp
      if (hp < minArray[0]) minArray[0] = hp

      val sp = line[4].trim().toInt()
      if (sp > maxArray[1]) maxArray[1] = sp
      if (sp < minArray[1]) minArray[1] = sp

      val str = line[5].trim().toInt()
      if (str > maxArray[2]) maxArray[2] = str
      if (str < minArray[2]) minArray[2] = str

      val vit = line[6].trim().toInt()
      if (vit > maxArray[3]) maxArray[3] = vit
      if (vit < minArray[3]) minArray[3] = vit

      val int = line[7].trim().toInt()
      if (int > maxArray[4]) maxArray[4] = int
      if (int < minArray[4]) minArray[4] = int

      val men = line[8].trim().toInt()
      if (men > maxArray[5]) maxArray[5] = men
      if (men < minArray[5]) minArray[5] = men

      val agi = line[9].trim().toInt()
      if (agi > maxArray[6]) maxArray[6] = agi
      if (agi < minArray[6]) minArray[6] = agi

      val tec = line[10].trim().toInt()
      if (tec > maxArray[7]) maxArray[7] = tec
      if (tec < minArray[7]) minArray[7] = tec

      val luk = line[11].trim().toInt()
      if (luk > maxArray[8]) maxArray[8] = luk
      if (luk < minArray[8]) minArray[8] = luk

      val mov = line[12].trim().toInt()
      if (mov > maxArray[9]) maxArray[9] = mov
      if (mov < minArray[9]) minArray[9] = mov

      val fir = line[13].trim().toInt()
      if (fir > maxArray[10]) maxArray[10] = fir
      if (fir < minArray[10]) minArray[10] = fir

      val wnd = line[14].trim().toInt()
      if (wnd > maxArray[11]) maxArray[11] = wnd
      if (wnd < minArray[11]) minArray[11] = wnd

      val ice = line[15].trim().toInt()
      if (ice > maxArray[12]) maxArray[12] = ice
      if (ice < minArray[12]) minArray[12] = ice

      val thd = line[16].trim().toInt()
      if (thd > maxArray[13]) maxArray[13] = thd
      if (thd < minArray[13]) minArray[13] = thd

      val inInventory = line[17].trim().toInt() == 1
      val equipment = Equipment(name, price, type, hp, sp, str, vit, int, men, agi, tec, luk, mov,
          fir, wnd, ice, thd, inInventory)
      items[type.ordinal][name] = equipment
    }
  }

  fun sumNormalizedStats(equipment: Equipment): Double {
    return (0 until equipment.stats.size).sumByDouble {
      normalize(equipment.stats[it], maxArray[it], minArray[it])
    }
  }

  private fun normalize(x: Int, max: Int,
      min: Int) = if (min == max) 0.0 else (x.toDouble() - min) / (max - min)
}
package app

import genetikt.Individual
import genetikt.IndividualFactory
import genetikt.Population
import genetikt.chromosome.TypedChromosomeFactory
import java.util.*

/**
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @version 1.2
 * @since   1.0
 */

private val availableMoney = 50_000
private val availableItems = Items("EquipmentTest3.csv")

fun main(args: Array<String>) {

  val factory = IndividualFactory(
      TypedChromosomeFactory(size = 1, alphabet = availableItems.weapons),
      TypedChromosomeFactory(size = 1, alphabet = availableItems.armor),
      TypedChromosomeFactory(size = 1, alphabet = availableItems.ornament),
      TypedChromosomeFactory(size = 1, alphabet = availableItems.costume),
      TypedChromosomeFactory(size = 1, alphabet = availableItems.accessory),
      mutationRate = 0.4,
      fitnessFunction = ::fitness,
      filterFunction = ::filter
  )
  val population = Population(1000, factory)
  var fittest = population.getFittest()

  var stability = 0
  while (stability <= 100) {
    population.evolve()
    if (fittest == population.getFittest()) stability++
    else {
      stability = 0
      fittest = population.getFittest()
    }
  }
  println("The best equipment combination is: $fittest.")
  println("Money left: ${fittest.fitness[1]}")
  println("Fitness: ${fittest.fitness[0]}")
}

/**
 * Custom fitness.
 */
fun fitness(individual: Individual): DoubleArray {
  var stats = 0.0
  var remainingMoney = availableMoney.toDouble()
  var dna: Equipment

  for (chromosome in individual.genotype) {
    dna = chromosome.genes[0].dna as Equipment
    stats += availableItems.sumNormalizedStats(dna)
    remainingMoney -= dna.price
  }
  return doubleArrayOf(stats, remainingMoney)
}

/**
 * Custom filter.
 */
fun filter(individual: Individual) {
  val genotype = individual.genotype
  val size = individual.size
  val rand = Random()

  while (true) {
    val totalCost = genotype
        .map { it.genes[0].dna as Equipment }
        .filterNot { it.inInventory } // Only sum if item is not in inventory.
        .sumByDouble { it.price.toDouble() }
    if (totalCost <= availableMoney) break
    individual.replaceGeneAt(chromosomeIndex = rand.nextInt(size), geneIndex = 0,
        with = Equipment.EMPTY_SLOT.name)
  }
}
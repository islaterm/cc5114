package genetikt

/*import java.util.*

/**
 * Creates a population of individuals that can evolve according to a fitness function.
 *
 * @author  Ignacio Slater Muñoz.
 * @version 1.0
 * @since   1.0
 */
class Population {

  private val rand = Random()
  private lateinit var alphabet: String
  private lateinit var individuals: Array<Individual>

  var generation = 0
    private set
  var target: String = ""
  var size = 30

  fun build() {
    individuals = Array(size) { Individual(target.length) }
  }

  /**
   * Evolves the population to a next generation.
   */
  fun evolve() {
    val childrens = arrayListOf<Individual>()
    while (childrens.size < individuals.size) {
      val parent1 = tournamentSelection()
      val parent2 = tournamentSelection()
      val child = crossover(parent1, parent2)
      child.mutate()
      childrens.add(child)
    }
    individuals = childrens.toTypedArray()
    generation++
  }

  fun getFittest(): Individual? {
    var maxFit = Double.MIN_VALUE
    var fittest: Individual? = null
    for (individual in individuals) {
      val newFitness = individual.getFitness(target.toCharArray())
      if (maxFit < newFitness) {
        maxFit = newFitness
        fittest = individual
      }
    }
    return fittest
  }

  /**
   * Generates an offspring from 2 parents.
   */
  private fun crossover(parent1: Individual, parent2: Individual): Individual {
    val chromSize = parent1.chromosome.size
    val mixingPoint = rand.nextInt(chromSize)
    val offspringChrom = CharArray(chromSize)

    for (i in 0 until offspringChrom.size) {
      if (i < mixingPoint) offspringChrom[i] = parent1.getGene(i)
      else offspringChrom[i] = parent2.getGene(i)
    }
    return Individual(offspringChrom)
  }

  /**
   * Selects a random individual prioritizing the ones with greater fitness.
   */
  private fun tournamentSelection(): Individual {
    val ind1 = individuals[rand.nextInt(individuals.size)]
    val ind2 = individuals[rand.nextInt(individuals.size)]

    return if (ind1.getFitness(target.toCharArray()) > ind2.getFitness(target.toCharArray()))
      ind1
    else
      ind2
  }

  fun setAlphabet(alphabet: String) {
    this.alphabet = alphabet
  }
}

/**
 * @author  Ignacio Slater Muñoz.
 * @version 1.0
 * @since   1.0
 * @since   1.0
 */
class Individual {

  private val alphabet = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ.' "
  private val mutationRate = 0.03
  internal val chromosome: CharArray

  internal constructor(chromosomeSize: Int) {
    chromosome = CharArray(chromosomeSize) { getRandomChar() }
  }

  internal constructor(genes: CharArray) {
    this.chromosome = genes
  }

  override fun toString() = chromosome.joinToString("")

  internal fun getGene(index: Int): Char {
    return chromosome[index]
  }

  internal fun getFitness(target: CharArray): Double {
    assert(target.size == chromosome.size) {
      "Target must be equal in size to number of chromosome of the individual."
    }
    var score = 0.0
    (0 until target.size)
        .filter { target[it] == chromosome[it] }
        .forEach { score++ }
    return score / target.size
  }

  private fun getRandomChar() = alphabet[Random().nextInt(alphabet.length)]

  internal fun mutate() {
    for (i in 0 until chromosome.size) {
      val r = Random().nextDouble()
      if (r < mutationRate) chromosome[i] = getRandomChar()
    }
  }
}*/
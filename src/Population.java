

import java.util.ArrayList;
import java.util.Random;

/**
 * Holds the population for the genetic algorithm and allows it to evolve.
 */
public class Population {
  // number of population members (solutions)
  private static final int SIZE = 200;  
  private static final Random rand = new Random(System.currentTimeMillis());
  
  private final ArrayList<Solution> members = new ArrayList<>(SIZE);
  private final CityTable cities;
  private final double mutationRate;
  
  /**
   * Creates the initial random population.
   * @param cities
   * @param mutationRate 
   */
  public Population(CityTable cities, double mutationRate) {
    this.cities = cities;
    this.mutationRate = mutationRate;
    for (int i = 0; i < members.size(); i++) {
      Solution s = new Solution(cities);
      s.generateRandom();
      members.add(s);
    }
  }
  
  /**
   * Get the best solution in the population.
   * @return 
   */
  public Solution getBest() {
    // TODO: return the best solution in the population
    return null;
  }
  
  /**
   * Evolves the population for the number of generations (main genetic 
   * algorithm happens here).
   * @param generations 
   */
  public void evolve(int generations) {
    // TODO: implement the genetic algorithm here
  }
}

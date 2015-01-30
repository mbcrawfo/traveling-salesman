

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Holds the population for the genetic algorithm and allows it to evolve.
 */
public class Population {
  // number of population members (solutions)
  private static final int SIZE = 200;  
  private static final Random rand = new Random(System.currentTimeMillis());
  private static final SolutionComparator comparator = new SolutionComparator();
  
  private ArrayList<Solution> members = new ArrayList<>(SIZE);
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
    for (int i = 0; i < SIZE; i++) {
      Solution s = new Solution(cities);
      s.generateRandom();
      s.calculateFitness();
      members.add(s);
    }
    members.sort(comparator);
  }
  
  /**
   * Get the best solution in the population.
   * @return 
   */
  public Solution getBest() {
    return members.get(0);
  }
  
  /**
   * Evolves the population for the number of generations (main genetic 
   * algorithm happens here).
   * @param generations 
   */
  public void evolve(int generations) {
    for (int gen = 0; gen < generations; gen++) {
      ArrayList<Solution> newPop = new ArrayList<>(members.size());    
      for (int i = 0; i < members.size(); i++) {        
        // pick parents from the best 50% of the population
        Solution parentA = members.get(rand.nextInt(members.size() / 2));
        Solution parentB = parentA;
        while (parentA == parentB) {
          parentB = members.get(rand.nextInt(members.size() / 2));
        }
        
        Solution s = new Solution(cities);
        s.cross(parentA, parentB);
        if (rand.nextDouble() < mutationRate) {
          s.mutate();
        }
        s.calculateFitness();
        newPop.add(s);
      }
      newPop.sort(comparator);
      members = newPop;
    }
  }
  
  private static class SolutionComparator implements Comparator<Solution> {
    @Override
    public int compare(Solution o1, Solution o2) {
      if (o1.getFitness() < o2.getFitness()) {
        return -1;
      }
      else if (o1.getFitness() == o2.getFitness()) {
        return 0;
      }
      else {
        return 1;
      }
    }
  }
}

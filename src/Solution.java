

import java.util.Random;

/**
 * Represents one solution (aka population member) for the traveling 
 * salesman problem.
 */
public class Solution {
  private static final Random rand = new Random(System.currentTimeMillis());
  
  private final CityTable cities;
  // this is the path that the salesman follows.  it's always implied that he
  // goes back to the first city after the last city.
  private final int[] path;
  // the "score" of this solution
  private int fitness = -1;  
  
  /**
   * Creates the solution.
   * @param cities 
   */
  public Solution(CityTable cities) {
        
    if (cities == null) {
      throw new IllegalArgumentException("cities is null");
    }
    this.cities = cities;
    path = new int[cities.getNumCities()];
    for (int i = 0; i < path.length; i++) {
      path[i] = -1;
    }
  }
  
  /**
   * Get the fitness of the solution.
   * @return 
   */
  public int getFitness() {
    return fitness;
  }
  
  /**
   * Populate this solution with a new, randomly generated solution.
   */
  public void generateRandom() {
    // TODO: implement me
  }
  
  /**
   * Score the fitness of this solution.
   */
  public void calculateFitness() {
    // TODO: implement me.  you might need to add parameters to this method
  }
  
  /**
   * Replace this solution by crossing two parents to create a new solution.
   * @param parentA
   * @param parentB 
   */
  public void cross(Solution parentA, Solution parentB) {
    if (parentA == null || parentB == null) {
      throw new IllegalArgumentException("Parents cannot be null");
    }
    
    // TODO: implement me
  }
  
  /**
   * Mutate this solution (pick something random and change it).
   */
  public void mutate() {
    // TODO: implement me    
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i : path) {
      sb.append(i).append("->");
    }
    sb.append(path[0]);
    return sb.toString();
  }
}

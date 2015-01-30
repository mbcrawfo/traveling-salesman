import java.util.Arrays;
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
  private final Integer[] path;
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
    path = new Integer[cities.getNumCities()];
    for (int i = 0; i < path.length; i++) {
      path[i] = -1;
    }
  }
  
  /**
   * The distance the salesman travels in this solution.
   * @return 
   */
  public int getDistance() {
    int result = 0;
    int lastCity = path[0];
    for (int i = 1; i < path.length; i++) {
      result += cities.getDistance(lastCity, path[i]);
      lastCity = path[i];
    }
    result += cities.getDistance(lastCity, path[0]);
    return result;
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
    // fill with cities in order
    for (int i = 0; i < path.length; i++) {
      path[i] = i;
    }
    
    // now shuffle them
    for (int i = path.length - 1; i > 0; i--) {
      int idx = rand.nextInt(i + 1);
      int temp = path[idx];
      path[idx] = path[i];
      path[i] = temp;
    }
  }
  
  /**
   * Score the fitness of this solution.
   */
  public void calculateFitness() {
    fitness = getDistance();
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
    
    System.arraycopy(parentA.path, 0, path, 0, path.length);
    int startCross = rand.nextInt(path.length / 2);
    int endCross = startCross + rand.nextInt(path.length / 2);
    
    Arrays.sort(path, startCross, endCross, (Integer o1, Integer o2) -> {
      int idx1 = parentB.indexOfCity(o1);
      int idx2 = parentB.indexOfCity(o2);
      
      if (idx1 < idx2) {
        return -1;
      }
      else if (idx1 == idx2) {
        return 0;
      }
      else {
        return 1;
      }
    });
  }
  
  /**
   * Mutate this solution (pick something random and change it).
   */
  public void mutate() {
    // select two random cities and swap them
    int first = rand.nextInt(path.length);
    int second = first;
    while (first == second) {
      second = rand.nextInt(path.length);
    }
    
    int temp = path[first];
    path[first] = path[second];
    path[second] = temp;
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
  
  private int indexOfCity(int city) {
    if (city < 0 || city >= cities.getNumCities()) {
      throw new IllegalArgumentException("invalid city");
    }
    
    for (int i = 0; i < path.length; i++) {
      if (path[i] == city) {
        return i;
      }
    }
    
    return -1;
  }
}

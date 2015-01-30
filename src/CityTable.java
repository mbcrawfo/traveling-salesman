
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Holds a table of distances between cities.
 */
public class CityTable {
  private final int numCities;
  private final ArrayList<ArrayList<Integer>> cities;
  
  /**
   * Loads a table of cities from a file.  The file format is a single int 
   * on the first line defining the number of cities, followed by N lines, 
   * each with N ints separated by spaces defining the distances in the table.
   * @param path
   * @return The loaded table or null if there was an error.
   * @throws FileNotFoundException 
   */
  public static CityTable loadFromFile(String path) 
          throws FileNotFoundException {
    Scanner file = new Scanner(new File(path));
    
    int num = file.nextInt();
    if (num <= 0) {
      System.out.println("File format invalid");
      return null;
    }    
    CityTable result = new CityTable(num);
    
    for (int i = 0; i < num; i++) {
      for (int j = 0; j < num; j++) {
        // the distance from city i to city j
        int dist = file.nextInt();
        if (i == j && dist != 0) {
          System.out.println("Distance from city " + i + " to itself is not 0");
          return null;
        }
        if (dist < 0) {
          System.out.println("Invalid distance from " + i + " to " + j);
          return null;
        }
        result.cities.get(i).set(j, dist);
      }
    }
    
    return result;
  }
  
  /**
   * Generate a table with random distances between the cities.
   * @param numCities
   * @return 
   */
  public static CityTable generateRandom(int numCities) {
    if (numCities <= 0) {
      throw new IllegalArgumentException("numCities <= 0");
    }
    
    final int minDistance = 1;
    final int maxDistance = 10;
    final Random rand = new Random();
    CityTable result = new CityTable(numCities);
    
    for (int i = 0; i < numCities; i++) {
      for (int j = 0; j <= i; j++) {
        if (i == j) {
          result.cities.get(i).set(j, 0);
        }
        else {
          int distance = rand.nextInt(maxDistance - minDistance + 1) + minDistance;
          result.cities.get(i).set(j, distance);
          result.cities.get(j).set(i, distance);
        }
      }
    }
    
    return result;
  }
  
  /**
   * Creates the table.
   * @param numCities 
   */
  private CityTable(int numCities) {
    this.numCities = numCities;
    cities = new ArrayList<>(numCities);
    for (int i = 0; i < numCities; i++) {
      ArrayList<Integer> row = new ArrayList<>(numCities);
      for (int j = 0; j < numCities; j++) {
        row.add(-1);
      }      
      cities.add(row);
    }
  }
  
  /**
   * 
   * @return 
   */
  public int getNumCities() {
    return numCities;
  }
  
  /**
   * Gets the distance from city A to B.
   * @param cityA
   * @param cityB
   * @return 
   */
  public int getDistance(int cityA, int cityB) {
    if (cityA < 0 || cityA >= numCities) {
      throw new IllegalArgumentException("cityA out of range");
    }
    if (cityB < 0 || cityB >= numCities) {
      throw new IllegalArgumentException("cityB out of range");
    }
    
    return cities.get(cityA).get(cityB);
  }
  
  /**
   * Saves the city data to a file.
   * @param path
   * @throws FileNotFoundException 
   */
  public void save(String path) throws FileNotFoundException {    
    try (PrintWriter pw = new PrintWriter(path)) {
      pw.write(numCities + "\n");
      
      for (int i = 0; i < numCities; i++) {
        for (int j = 0; j < numCities; j++) {
          pw.write(cities.get(i).get(j) + " ");
        }
        pw.write("\n");
      }
      pw.write("\n");
    }
  }
}

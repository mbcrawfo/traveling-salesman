
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import util.menu.BaseMenuItem;
import util.menu.Menu;

/**
 * Traveling salesman program.
 * @author Michael Crawford
 */
public class TravelingSalesman {
  private Menu menu;
  private String loadedFile = "";
  CityTable cities;
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    new TravelingSalesman().run();
  }
  
  public TravelingSalesman() {
    menu = new Menu("Traveling Salesman") {
      @Override
      public void displayCallback() {
        if ("".equals(loadedFile)) {
          title = "Traveling Salesman\n<no file loaded>";
        }
        else {
          title = MessageFormat.format(
                  "Traveling Salesman\nFile: {0}\nCities: {1}", 
                  loadedFile, cities.getNumCities());
        }
      }
    };
    
    menu.addItem(new BaseMenuItem("1", "Load file", menu) {      
      @Override
      public boolean execute() {
        String path = getParent().readString("Enter file name: ");
        try {
          cities = CityTable.loadFromFile(path);
          if (cities != null) {
            loadedFile = path;
          }
        }
        catch (FileNotFoundException ex) {
          System.out.println("File not found");
        }
        return false;
      }
    });
    
    menu.addItem(new BaseMenuItem("2", "Run Genetic Algorithm", menu) {      
      @Override
      public boolean execute() {
        int generations = getParent().readInt(
                "How many generations? ", 1, Integer.MAX_VALUE);
        double mutationRate = getParent().readDouble(
                "Enter new rate: ", 0.0, 1.0);
        runAlgorithm(generations, mutationRate);
        return false;
      }
    });
    
    menu.addItem(new BaseMenuItem("3", "Quit", menu) {      
      @Override
      public boolean execute() {
        return true;
      }
    });
  }
  
  /**
   * Run the program.
   */
  public void run() {
    menu.execute();
  }
  
  /**
   * Execute the algorithm.
   * @param generations
   * @param mutationRate 
   */
  private void runAlgorithm(int generations, double mutationRate) {
    if (generations <= 0) {
      throw new IllegalArgumentException("generations must be positive");
    }
    if (mutationRate < 0.0 || mutationRate > 1.0) {
      throw new IllegalArgumentException(
              "mutationRate must be a percentage [0.0,1.0]");
    }
  }
}

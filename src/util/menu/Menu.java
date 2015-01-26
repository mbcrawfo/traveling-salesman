package util.menu;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Implements a menu which displays various options defined by {@link 
 * MenuItem} objects. Add the desired items to the menu, and call {@link 
 * Menu#execute() execute()} to run the menu. Note that a {@code Menu} is also a
 * {@code MenuItem}, so menus may be nested.
 */
public class Menu extends BaseMenuItem {
  private Scanner input = new Scanner(System.in);
  private TreeMap<String, MenuItem> items = new TreeMap<String, MenuItem>();
  protected String title;
  
  /**
   * Creates a new {@code Menu}, used for a standard menu.
   * @param title the title to be shown when the menu is displayed.
   * @pre title must not be null or empty.
   * @throws IllegalArgumentException Indicates that title is null or empty.
   */
  public Menu(String title) {
    this(title, null, null, null);
  }
  
  /**
   * Creates a new {@code Menu}, used for constructing a menu which will be
   * nested inside of another menu.
   * @param title the title to be shown when the menu is displayed.
   * @param ID the ID used when this menu is listed in its parent menu.
   * @param text the text displayed when this menu is listed in its parent menu.
   * @param parent the menu's parent menu.
   * @pre title must not be null or empty.
   * @throws IllegalArgumentException Indicates that title is null or empty.
   */
  public Menu(String title, String ID, String text, Menu parent) {
    super(ID, text, parent);
    
    // enforce precondition
    if (title == null || title.isEmpty()) {
      throw new IllegalArgumentException("title must not be null or empty");
    }
    
    this.title = title;
  }
  
  /**
   * Adds an item to the menu.
   * @param item the item to add.
   * @return true if the item was added successfully.
   * @pre item must not be null. item.getID() must not be null or empty.
   * item.getText() must not be null or empty.
   * @post The new item has been added to the menu.
   * @throws IllegalArgumentException Indicates that one of the preconditions
   * has been violated.
   */
  public boolean addItem(MenuItem item) {
    // enforce preconditions
    if (item == null) {
      throw new IllegalArgumentException("item must not be null");
    }
    if (item.getID() == null || item.getID().isEmpty()) {
      throw new IllegalArgumentException(
              "item.getID() must not be null or empty");
    }
    if (item.getText() == null || item.getText().isEmpty()) {
      throw new IllegalArgumentException(
              "item.getText() must not be null or empty");
    }
    
    if (items.containsKey(item.getID())) {
      return false;
    }
    
    items.put(item.getID(), item);
    return true;
  }
  
  /**
   * Adds multiple items to the menu.
   * @param itemArray an array of items to be added to the menu.
   * @return null if all items were successfully added.  If any items fail to 
   * be added, they are returned.
   * @pre itemArray must not be null. The preconditions of {@link 
   * Menu#addItem(util.menu.MenuItem) addItem} apply to every item passed to
   * this method.
   * @post All items have been added to the menu, except those items returned 
   * by this method.
   * @throws IllegalArgumentException Indicates that itemArray is null.
   * @see Menu#addItem(MenuItem)
   */
  public ArrayList<MenuItem> addItems(MenuItem[] itemArray) {
    // enforce precondition
    if (itemArray == null) {
      throw new IllegalArgumentException("itemArray must not be null");
    }
    
    ArrayList<MenuItem> failed = new ArrayList<MenuItem>(itemArray.length);
    
    for (MenuItem item : itemArray) {
      if (!addItem(item)) {
        failed.add(item);
      }
    }
    
    return failed.isEmpty() ? null : failed;
  }
  
  /**
   * Removes an item from the menu.
   * @param ID the ID of the item to be removed.
   * @return true if the item was removed.
   */
  public boolean removeItem(String ID) {
    if (!items.containsKey(ID)) {
      return false;
    }
    
    items.remove(ID);
    return true;
  }
  
  /**
   * Removes an item from the menu.
   * @param item the item to be removed.
   * @return true if the item was removed.
   */
  public boolean removeItem(MenuItem item) {
    return item == null ? false : removeItem(item.getID()); 
  }

  /**
   * Executes and displays the menu.
   * @return Always returns false. It is assumed that when there are nested
   * menus, the user would like to select an option from the parent menu after
   * leaving this menu.
   * @pre The menu must contain at least one item.
   */
  @Override
  public boolean execute() {
    // enforce precondition
    if (items.isEmpty()) {
      throw new IllegalStateException("cannot execute an empty menu");
    }
    
    boolean done = false;
    
    while (!done) {
      printMenu();
      String selection = readString("Enter selection:");
      
      MenuItem item = items.get(selection);
      if (item != null) {
        done = item.execute();
      }
      else {
        System.out.println("Invalid selection.");
      }
    }
    
    return false;
  }
  
  /**
   * Prompts the user, and reads their input.
   * @param prompt the prompt which is displayed to the user.
   * @return The user's input.
   */
  public final String readString(String prompt) {
    System.out.print(prompt + " ");
    return input.nextLine();
  }
  
  /**
   * Prompts the user, and reads their input as an integer. Input is validated
   * to be between {@code min} and {@code max}.
   * @param prompt the prompt which is displayed to the user.
   * @param min the smallest input value which will be accepted.
   * @param max the largest input value which will be accepted.
   * @return The user's input.
   */
  public final int readInt(String prompt, int min, int max) {
    int result = Integer.MIN_VALUE;
    boolean done = false;
    
    while (!done) {
      try {
        result = Integer.parseInt(readString(prompt));
        if (result >= min && result <= max) {
          done = true;
        }
      }
      catch (NumberFormatException e) {}
      
      if (!done) {
        System.out.println("Invalid input.");
      }
    }
    
    return result;
  }
  
  /**
   * Prompts the user, and reads their input as an integer. Any value between
   * {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE} is accepted.
   * @param prompt the prompt which is displayed to the user.
   * @return The user's input.
   */
  public final int readInt(String prompt) {
    return readInt(prompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }
  
  /**
   * Prompts the user and reads their input as a double. Input is validated to
   * be between {@code min} and {@code max}.
   * @param prompt the prompt which is displayed to the user.
   * @param min the smallest input value which will be accepted.
   * @param max the largest input value which will be accepted.
   * @return The user's input.
   */
  public final double readDouble(String prompt, double min, double max) {
    double result = Double.NaN;
    boolean done = false;
    
    while (!done) {
      try {
        result = Double.parseDouble(readString(prompt));
        if (result >= min && result <= max) {
          done = true;
        }
      }
      catch (NumberFormatException e) {}
      
      if (!done) {
        System.out.println("Invalid input.");
      }
    }
    
    return result;
  }
  
  /**
   * Prompts the user and reads their input as a double. Any value between
   * {@link Double#MIN_VALUE} and {@link Double#MAX_VALUE} is accepted.
   * @param prompt the prompt which is displayed to the user.
   * @return The user's input.
   */
  public final double readDouble(String prompt) {
    return readDouble(prompt, Double.MIN_VALUE, Double.MAX_VALUE);
  }
  
  /**
   * Prints the contents of the menu to the console.
   */
  public void printMenu() {
    displayCallback();
    System.out.println();
    System.out.println(title);
    
    for (String ID : items.keySet()) {
      MenuItem item = items.get(ID);
      item.displayCallback();
      System.out.println(ID + " " + item.getText());
    }
  }
}

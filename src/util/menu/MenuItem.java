package util.menu;

/**
 * Defines the interface used by items which are stored in a menu.
 * @author Michael Crawford
 */
public interface MenuItem {
  /**
   * Returns the identifier for this item. Items in the menu are sorted by this
   * ID, and it is the text that the user will enter to selection this item.
   * @return the identifier for this item.
   */
  public String getID();
  
  /**
   * Returns the text of this item. This is the text displayed when the menu
   * is displayed.
   * @return the text of this item.
   */
  public String getText();
  
  /**
   * Executes when the item is selected by the user.
   * @return true if the menu should exit following this function.  false
   * indicates that the menu should display again.
   */
  public boolean execute();
  
  /**
   * Returns the parent menu of this item.  Mainly used in the event that
   * additional input is desired during {@link MenuItem#execute() execute()}.
   * @return This item's parent menu
   */
  public Menu getParent();
  
  /**
   * Method is called before a menu is displayed, allowing it to dynamically 
   * modify its display text.
   */
  public void displayCallback();
}

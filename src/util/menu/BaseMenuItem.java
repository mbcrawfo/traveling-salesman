package util.menu;

/**
 * A basic implementation of {@link MenuItem}. Requires only that {@link 
 * MenuItem#execute() execute()} be implemented in the subclass.
 * @author Michael Crawford
 */
public abstract class BaseMenuItem implements MenuItem {
  private String ID;
  protected String text;
  private Menu parent;
  
  /**
   * Constructs a new {@code BaseMenuItem}.
   * @param ID the identifier for this item.
   * @param text the text displayed for this item.
   * @param parent the menu which this item is part of.
   */
  public BaseMenuItem (String ID, String text, Menu parent) {    
    this.ID = ID;
    this.text = text;
    this.parent = parent;
  }
  
  @Override
  public Menu getParent() {
    return parent;
  }
  
  @Override
  public String getID() {
    return ID;
  }
  
  @Override
  public String getText() {
    return text;
  }
  
  @Override
  public void displayCallback() {    
  }
}

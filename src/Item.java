/**
 * The <code>Item</code> is a container that hold all the data about
 * an item.
 * 
 * @author Jordan Anodjo
 */

public class Item {

    /**
     * Member variable -
     * The code/identification for an <code>Item</code>.
     */
    private Code code;

    /**
     * Member variable -
     * The name for an <code>Item</code>.
     */
    private String name;

    /**
     * Member variable -
     * The price for an <code>Item</code>.
     */
    private float price;

    /**
     * Member variable -
     * The amount of <code>Item</code> stored/ordered.
     */
    private int quantity;

    /**
     * Constructor -
     * Constructs an <code>Item</code> with all memeber variables filled.
     * 
     * @param code     - <code>Code</code> of the <code>Item</code>
     * @param name     - the name of the <code>Item</code>
     * @param price    - the cost of the <code>Item</code>
     * @param quantity - the amount of this <code>Item</code> saved
     */
    public Item(Code code, String name, float price, int quantity) {
        this.code = code;
        this.name = name;
        this.price = price;

        // Check if the amount is acceptable.
        setQuantity(quantity);
    }

    /**
     * Copy Constructor -
     * Constructs a copy of an <code>Item</code> from an <code>Item</code>.
     * 
     * @param item <code>Item</code> that will be copied
     */
    public Item(Item item) {
        this.code = new Code(item.getCode());
        this.name = item.getName();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
    }

    /**
     * Member Function -
     * Getter for the <code>Item</code> code.
     * 
     * @return The <code>Code</code> of the <code>Item</code>
     */
    public Code getCode() {
        return code;
    }

    /**
     * Member Function -
     * Getter for the <code>Item</code> name.
     * 
     * @return The the name of the <code>Item</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Member Function -
     * Getter for the <code>Item</code> price.
     * 
     * @return The price of the <code>Item</code>
     */
    public float getPrice() {
        return price;
    }

    /**
     * Member Function -
     * Getter for quantity of this <code>Item</code>
     * 
     * @return The quantity of this <code>Item</code> stored
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Member Function -
     * This function sets the quantity for this <code>Item</code>.
     * The quantity must be greater than or equal to 0.
     * Cannot logically have a negative <code>Item</code> if so the function will
     * throw an error.
     * 
     * @param quantity number to set quantity
     * @throws IllegalArgumentException A negative number was passed
     */
    public void setQuantity(int quantity) {
        if (quantity >= 0)
            this.quantity = quantity;
        else
            throw new IllegalArgumentException("Cannot have a negative amount of item.");

    }

    /**
     * Member Function -
     * This function increments the quantity for this <code>Item</code>.
     * 
     * @param amount - The amount of item to add
     * @throws IllegalArgumentException A number less than or equal to 0 was passed
     */
    public void addQuantity(int amount) {
        if (amount > 0)
            this.quantity += amount;
        else
            throw new IllegalArgumentException("Cannot add a non positive item.");

    }

    /**
     * Member Function -
     * Calculates the full cost of <code>Item</code>.
     * 
     * @return The cost of all the quantity mulitplied by price.
     */
    public float calculateAmountTotal() {
        return price * quantity;
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * Prints <code>Item</code> in the format of: amount, name, and total price.
     */
    @Override
    public String toString() {
        return String.format("%5d %-13s $ %6.2f", quantity, name, calculateAmountTotal());
    }

}

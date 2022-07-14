import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The <code>Receptacle</code> is a container that holds an
 * <code>ArrayList</code> of
 * <code>Item</code> and of <code>Code</code>.
 * 
 * @author Jordan Anodjo
 */
public class Receptacle {

    /**
     * Member variable -
     * <code>ArrayList</code> that contains <code>Item</code>.
     */
    protected ArrayList<Item> items;

    /**
     * Member variable -
     * An <code>ArrayList</code> that contains <code>Code</code>.
     */
    protected ArrayList<Code> codes;

    /**
     * Member variable -
     * <code>length</code> - the lenght of <code>Item[] items</code>.
     */
    protected int length;

    /**
     * Member variable -
     * <code>file</code> - The destination of where the Receptacle is saved.
     */
    protected File file;

    /**
     * Constructer -
     * Constructs an empty <code>Receptacle</code>.
     */
    public Receptacle() {
        this.length = 0;
        this.file = null;
        items = new ArrayList<Item>(length);
        codes = new ArrayList<Code>(length);
    }

    /**
     * Constructer -
     * Constructs a <code>Receptacle</code> from file.
     * 
     * @param path - Location of a file that holds <code>Item</code> data
     * @throws FileNotFoundException    File could not be found
     * @throws IllegalArgumentException File format is not valid
     */
    public Receptacle(String path) throws FileNotFoundException {
        this();

        // Open the file path
        File file = new File(path);
        Scanner input = new Scanner(file);

        // Go through each line in the file.
        // Create an Item from the data.
        // Then insert it into the Receptacle.
        int counter = 0;
        while (input.hasNext()) {
            // Split the data up
            String[] str = input.nextLine().split(", ");

            Code code;
            String name;
            Float price;

            try {
                // Create the parameters for the new Item.
                code = new Code(str[0]);
                name = str[1];
                price = Float.parseFloat(str[2]);
            } catch (Exception e) {
                input.close();
                throw new IllegalArgumentException("File format is not valid.");
            }

            // Create the Item.
            Item item = new Item(code, name, price, 0);

            // Add Item code into this Receptacle list.
            codes.add(code);

            // Add Item into this Receptacle.
            items.add(item);

            // Increment counter.
            counter++;
        }

        // Save the path.
        this.file = file;

        // Update length.
        length = counter;

        // Close the scanner.
        input.close();
    }

    /**
     * Member Function -
     * This function retrieves a reference to an <code>Item</code>.
     * 
     * @param code - The code of the <code>Item</code> being retieved
     * @return Reference to an <code>Item</code>
     * @throws IllegalArgumentException Item at code does not exist
     */
    public Item getItem(Code code) {
        // Check if the code given is in valid format.
        if (exist(code))
            return items.get(getIndex(code));
        throw new IllegalArgumentException("Item at code does not exist.");

    }

    /**
     * Member Function -
     * This function returns the length of the <code>Receptacle</code>.
     * 
     * @return The length of the <code>Receptacle</code>
     */
    public int getLength() {
        return length;
    }

    /**
     * Member Function -
     * This function sets the quantity for an <code>Item</code>.
     * 
     * @param code   - The code of the <code>Item</code> being set
     * @param amount - The value to set the amount to
     * @throws IllegalArgumentException Item does not exist
     */
    public void setItemAmount(Code code, int amount) {

        if (exist(code))
            items.get(getIndex(code)).setQuantity(amount);
        throw new IllegalArgumentException("Cannot set amount at code because item does not exist");
    }

    /**
     * Member function -
     * This function replaces the <code>Item</code> with the <code>Code</code>
     * passed.
     * 
     * @param item - <code>Item</code> that will be replacing the old item
     * @param code - <code>Code</code> of the item that will be replaced
     * @throws IllegalArgumentException Item does not exist
     */
    public void replaceItem(Item item, Code code) {
        int idx = getIndex(code);

        if (idx == -1)
            throw new IllegalArgumentException(String.format("Item with %s does not exist", code.getValue()));
        items.set(idx, item);
    }

    /**
     * Member Function -
     * This function takes an <code>Item</code> and adds it to the this
     * <code>Receptacle</code>.
     * 
     * @param item - <code>Item</code> that will be inserted
     */
    public void add(Item item) {
        // Update member variables.
        items.add(item);
        codes.add(item.getCode());
        length++;
    }

    /**
     * Member Function -
     * This function removes an <code>Item</code> with the same <code>Code</code>
     * passed.
     * 
     * @param code - <code>Code</code> of the <code>item</code> being removed
     * @throws IllegalArgumentException Item does not exist
     */
    public void remove(Code code) {

        // If there exist an Item with the same Code remove it.
        if (exist(code)) {

            // Store the index of the Item with the same Code.
            int idx = getIndex(code);

            // Update all the member variables.
            items.remove(idx);
            codes.remove(idx);
            length--;

        } else
            throw new IllegalArgumentException("Item at code does not exist.");
    }

    /**
     * Member Function -
     * This function creates a clone of an <code>Item</code> with the same
     * <code>Code</code> passed.
     * 
     * @param code The <code>Code</code> of the item to be copied
     * @return A copy of the <code>Item</code> at the specified index
     * @throws IllegalArgumentException Item does not exist
     */
    public Item copy(Code code) {
        try {
            return new Item(getItem(code));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cannot copy item at code, because item does not exist.");
        }
    }

    /**
     * Member Function -
     * This function checks if the <code>Item</code> given exists
     * 
     * @param item - <code>Item</code> that is being searched for
     * @return <code>True</code> if the item exist <code>False</code> otherwise
     */
    public boolean exist(Item item) {

        // Check if the code of the item is in the codes list
        for (Code code : codes)
            if (code.getValue().equals(item.getCode().getValue()))
                return true;

        return false;
    }

    /**
     * Member Function -
     * This function checks if the given <code>Code</code> has an <code>Item</code>
     * with the same code exists
     * inside the list.
     * 
     * @param code - <code>Code</code> that will be checked
     * @return Boolean value <code>true</code> if <code>Item</code> does exist
     *         <code>false</code> otherwise
     */
    public boolean exist(Code code) {

        // Check if the code given is in valid format
        for (Code _code : codes)
            if (_code.getValue().equals(code.getValue()))
                return true;
        return false;
    }

    /**
     * Member Function -
     * This function takes a <code>Code</code> and returns its index in the
     * <code>Receptacle</code>.
     * If the item does not exist will return -1.
     * 
     * @param code - <code>Code</code> of the <code>Item</code>
     * @return The index of the <code>item</code>
     */
    private int getIndex(Code code) {

        // Check each item in arrayList if it matches then return the index.
        if (exist(code))
            for (int i = 0; i < length; i++)
                if (items.get(i).getCode().getValue().equals(code.getValue()))
                    return i;

        return -1;
    }

    /**
     * Member Function -
     * Sorts the <code>Receptacle</code> by the length of each <code>Item</code>'s
     * name.
     */
    public void sort() {

        // For each Item compare it with the other Items and swap them if needed.
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (items.get(i).getName().length() < items.get(j).getName().length()) {
                    Item temp = items.get(i);
                    items.set(i, items.get(j));
                    items.set(j, temp);
                }
            }
        }
    }

    /**
     * Member Function -
     * Calcualates the cost of each <code>Item</code> in the
     * <code>Receptacle</code>.
     * 
     * @param tax_rate - The rate of tax
     * 
     * @return The total cost of the <code>Receptacle</code>
     */
    public double[] getTotalCost(double tax_rate) {
        float taxedTotal = 0;
        float nonTaxedTotal = 0;

        for (Item item : items)
            if (item.getCode().getLeadingChar() != 'B')
                taxedTotal += item.calculateAmountTotal();
            else
                nonTaxedTotal += item.calculateAmountTotal();

        return new double[] { taxedTotal + nonTaxedTotal, (taxedTotal * tax_rate) + taxedTotal + nonTaxedTotal };
    }

    /**
     * Member Function -
     * 
     * This function overwrites the file that was used to make the original
     * <code>Receptacle</code> with any new values given to it.
     */
    public void updateFile() {
        java.io.PrintWriter output;

        // Catches if the file being saved to exist.
        try {
            output = new java.io.PrintWriter(file);

        } catch (FileNotFoundException e) {
            throw new NullPointerException("Cannot update file since the source of the file is not declared");
        }

        // Writing File
        for (Item item : items) {
            output.printf("%s, %s, %.2f\n", item.getCode().getValue(), item.getName(), item.getPrice());
        }

        // Close the File
        output.close();
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * Returns a <code>String</code> containing each <code>Item</code> in the
     * <code>Receptacle</code>.
     * In the format of amount, name, total price.
     */
    @Override
    public String toString() {
        StringBuilder strbldr = new StringBuilder();
        for (int i = 0; i < length; i++) {
            strbldr.append(items.get(i).toString());

            // Dont print the last newline.
            if (!(i == length - 1))
                strbldr.append("\n");
        }

        return strbldr.toString();
    }
}

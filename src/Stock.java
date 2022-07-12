/**
 * "A store stock of items"
 * The <code>Stock</code> is a modified <code>Receptacle</code> that has
 * slightly different outputs.
 * 
 * @author Jordan Anodjo
 */
public class Stock extends Receptacle {

    public Stock(String path) throws java.io.FileNotFoundException {
        super(path);
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * Prints each <code>Item</code> in the <code>Stock</code>.
     * In the format of Code, Name, Price.
     */
    @Override
    public String toString() {

        StringBuilder strbldr = new StringBuilder();
        // String str = String.format("item code%-13sitem name%20sitem price%10s", " ",
        // " ", " ");
        String str = String.format("%-13s%-20s%-10s", "item code", "item name", "item price");
        strbldr.append(str);
        strbldr.append("\n");

        for (int i = 0; i < super.length; i++) {
            Item item = items.get(i);
            str = String.format("%-13s%-20s%-10.2f", item.getCode().getValue(), item.getName(), item.getPrice());
            strbldr.append(str);

            // Dont print the last newline.
            if (!(i == length - 1))
                strbldr.append("\n");
        }

        return strbldr.toString();
    }
}

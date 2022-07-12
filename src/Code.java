/**
 * The <code>Code</code> is a formated String that references a value
 * for an <code>Item</code>.
 * 
 * @author Jordan Anodjo
 */
public class Code {

    /**
     * Member variable -
     * <code>String format</code> is a regex.
     * used to set a specific format that all codes must follow.
     */
    private static String format = "^(?i)[A-Z].*";

    /**
     * Member variable -
     * <code>String code</code> is value that reference an <code>Item</code>.
     */
    private String code;

    /**
     * Copy Constructor -
     * Constructs a copy of an <code>Code</code> from a <code>Code</code>.
     * 
     * @param code
     */
    public Code(String code) {
        if (!code.matches(format))
            throw new IllegalArgumentException("Format is not valid");
        this.code = code;
    }

    public Code(Code code) {
        this.code = code.getValue();

    }

    public String getValue() {
        return code;
    }

    public char getLeadingChar() {
        return code.charAt(0);
    }

    @Override
    public String toString() {
        return code;
    }

}

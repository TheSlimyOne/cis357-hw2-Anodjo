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
     * Contructor -
     * Creates a <code>Code</code> with passed <code>String</code>.
     * 
     * @param code - <code>String</code> that will be used for a
     *             <code>Code</code>
     * @throws IllegalArgumentException <code>Code</code> format is not valid
     */
    public Code(String code) {
        if (!code.matches(format))
            throw new IllegalArgumentException("Code format is not valid");
        this.code = code;
    }

    /**
     * Copy Constructor -
     * Constructs a copy of a <code>Code</code> from a <code>Code</code>
     * 
     * @param code - <code>Code</code> that will be copied
     */
    public Code(Code code) {
        this.code = code.getValue();
    }

    /**
     * Member Function -
     * This function returns the String value of the code.
     * 
     * @return a value for this <code>Code</code>
     */
    public String getValue() {
        return code;
    }

    /**
     * Member function -
     * This function returns the <code>char</code> at the beginning of this
     * <code>Code</code>
     * 
     * @return <code>char</code> at the first index of the <code>Code</code>
     */
    public char getLeadingChar() {
        return code.charAt(0);
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * Creates a string version of <code>Code</code>.
     * 
     * @return The string version of <code>Code</code>
     */
    @Override
    public String toString() {
        return code;
    }

}

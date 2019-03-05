package biweekly.io.text;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class FoldingScheme {
    public static final FoldingScheme DEFAULT = new FoldingScheme(75, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
    private final String indent;
    private final int lineLength;

    public FoldingScheme(int lineLength, String indent) {
        if (lineLength <= 0) {
            throw new IllegalArgumentException("The line length must be greater than 0.");
        } else if (indent.length() > lineLength) {
            throw new IllegalArgumentException("The line length must be greater than the length of the indentation string.");
        } else {
            this.lineLength = lineLength;
            this.indent = indent;
        }
    }

    public int getLineLength() {
        return this.lineLength;
    }

    public String getIndent() {
        return this.indent;
    }
}

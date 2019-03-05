package biweekly.io.text;

import java.io.IOException;
import java.io.Writer;

public class FoldedLineWriter extends Writer {
    private int curLineLength = 0;
    private String indent;
    private int lineLength;
    private String newline;
    private final Writer writer;

    public FoldedLineWriter(Writer writer, int lineLength, String indent, String newline) {
        setLineLength(lineLength);
        setIndent(indent);
        this.writer = writer;
        this.newline = newline;
    }

    public void writeln(String str) throws IOException {
        write(str);
        write(this.newline);
    }

    public void write(char[] buf, int start, int end) throws IOException {
        write(buf, start, end, this.lineLength, this.indent);
    }

    public void write(char[] buf, int start, int end, int lineLength, String indent) throws IOException {
        int i = start;
        while (i < end) {
            char c = buf[i];
            if (c == '\n') {
                this.writer.write(buf, start, (i - start) + 1);
                this.curLineLength = 0;
                start = i + 1;
            } else if (c == '\r') {
                if (i == end - 1 || buf[i + 1] != '\n') {
                    this.writer.write(buf, start, (i - start) + 1);
                    this.curLineLength = 0;
                    start = i + 1;
                } else {
                    this.curLineLength++;
                }
            } else if (this.curLineLength >= lineLength) {
                if (Character.isWhitespace(c)) {
                    while (Character.isWhitespace(c) && i < end - 1) {
                        i++;
                        c = buf[i];
                    }
                    if (i == end - 1) {
                        break;
                    }
                }
                this.writer.write(buf, start, i - start);
                String s = this.newline + indent;
                this.writer.write(s.toCharArray(), 0, s.length());
                start = i;
                this.curLineLength = indent.length() + 1;
            } else {
                this.curLineLength++;
            }
            i++;
        }
        this.writer.write(buf, start, end - start);
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public int getLineLength() {
        return this.lineLength;
    }

    public void setLineLength(int lineLength) {
        if (lineLength <= 0) {
            throw new IllegalArgumentException("Line length must be greater than 0.");
        }
        this.lineLength = lineLength;
    }

    public String getIndent() {
        return this.indent;
    }

    public void setIndent(String indent) {
        if (indent.length() >= this.lineLength) {
            throw new IllegalArgumentException("The length of the indent string must be less than the max line length.");
        }
        this.indent = indent;
    }

    public String getNewline() {
        return this.newline;
    }

    public void setNewline(String newline) {
        this.newline = newline;
    }
}

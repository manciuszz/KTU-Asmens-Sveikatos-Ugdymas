package biweekly.io.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class FoldedLineReader extends BufferedReader {
    private String lastLine;
    private int lastLineNum;
    private int lineCount;
    private boolean singleSpaceFolding;

    public FoldedLineReader(Reader reader) {
        super(reader);
        this.singleSpaceFolding = true;
        this.lastLineNum = 0;
        this.lineCount = 0;
    }

    public FoldedLineReader(String text) {
        this(new StringReader(text));
    }

    public void setSingleSpaceFoldingEnabled(boolean enabled) {
        this.singleSpaceFolding = enabled;
    }

    public boolean isSingleSpaceFoldingEnabled() {
        return this.singleSpaceFolding;
    }

    public int getLineNum() {
        return this.lastLineNum;
    }

    private String readNonEmptyLine() throws IOException {
        String line;
        do {
            line = super.readLine();
            if (line != null) {
                this.lineCount++;
            }
            if (line == null) {
                break;
            }
        } while (line.length() == 0);
        return line;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String readLine() throws java.io.IOException {
        /*
        r6 = this;
        r4 = 0;
        r5 = r6.lastLine;
        if (r5 != 0) goto L_0x000e;
    L_0x0005:
        r2 = r6.readNonEmptyLine();
    L_0x0009:
        r6.lastLine = r4;
        if (r2 != 0) goto L_0x0011;
    L_0x000d:
        return r4;
    L_0x000e:
        r2 = r6.lastLine;
        goto L_0x0009;
    L_0x0011:
        r4 = r6.lineCount;
        r6.lastLineNum = r4;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
    L_0x001a:
        r1 = r6.readNonEmptyLine();
        if (r1 != 0) goto L_0x0025;
    L_0x0020:
        r4 = r3.toString();
        goto L_0x000d;
    L_0x0025:
        r4 = 0;
        r4 = r1.charAt(r4);
        r4 = java.lang.Character.isWhitespace(r4);
        if (r4 != 0) goto L_0x0033;
    L_0x0030:
        r6.lastLine = r1;
        goto L_0x0020;
    L_0x0033:
        r0 = 1;
        r4 = r6.singleSpaceFolding;
        if (r4 != 0) goto L_0x004b;
    L_0x0038:
        r4 = r1.length();
        if (r0 >= r4) goto L_0x004b;
    L_0x003e:
        r4 = r1.charAt(r0);
        r4 = java.lang.Character.isWhitespace(r4);
        if (r4 == 0) goto L_0x004b;
    L_0x0048:
        r0 = r0 + 1;
        goto L_0x0038;
    L_0x004b:
        r4 = r1.substring(r0);
        r3.append(r4);
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: biweekly.io.text.FoldedLineReader.readLine():java.lang.String");
    }
}

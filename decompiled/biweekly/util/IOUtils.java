package biweekly.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public class IOUtils {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static byte[] toByteArray(InputStream in) throws IOException {
        return toByteArray(in, false);
    }

    public static byte[] toByteArray(InputStream in, boolean close) throws IOException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            while (true) {
                int read = in.read(buffer);
                if (read == -1) {
                    break;
                }
                out.write(buffer, 0, read);
            }
            byte[] toByteArray = out.toByteArray();
            return toByteArray;
        } finally {
            if (close) {
                closeQuietly(in);
            }
        }
    }

    public static String getFileContents(File file) throws IOException {
        return getFileContents(file, Charset.defaultCharset().name());
    }

    public static String getFileContents(File file, String charset) throws IOException {
        return new String(toByteArray(new FileInputStream(file), true), charset);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static Writer utf8Writer(OutputStream out) {
        return new OutputStreamWriter(out, UTF8);
    }

    public static Writer utf8Writer(File file) throws FileNotFoundException {
        return utf8Writer(file, false);
    }

    public static Writer utf8Writer(File file, boolean append) throws FileNotFoundException {
        return utf8Writer(new FileOutputStream(file, append));
    }

    public static Reader utf8Reader(InputStream in) {
        return new InputStreamReader(in, UTF8);
    }

    public static Reader utf8Reader(File file) throws FileNotFoundException {
        return utf8Reader(new FileInputStream(file));
    }

    private IOUtils() {
    }
}

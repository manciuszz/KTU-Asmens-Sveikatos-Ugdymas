package biweekly.util;

public class Base64 {
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final int[] toInt = new int[128];

    static {
        for (int i = 0; i < ALPHABET.length; i++) {
            toInt[ALPHABET[i]] = i;
        }
    }

    public static String encode(byte[] buf) {
        int i;
        int size = buf.length;
        char[] ar = new char[(((size + 2) / 3) * 4)];
        int i2 = 0;
        int a = 0;
        while (i2 < size) {
            byte b1;
            byte b2;
            int i3 = i2 + 1;
            byte b0 = buf[i2];
            if (i3 < size) {
                i2 = i3 + 1;
                b1 = buf[i3];
            } else {
                b1 = (byte) 0;
                i2 = i3;
            }
            if (i2 < size) {
                i3 = i2 + 1;
                b2 = buf[i2];
            } else {
                b2 = (byte) 0;
                i3 = i2;
            }
            i = a + 1;
            ar[a] = ALPHABET[(b0 >> 2) & 63];
            a = i + 1;
            ar[i] = ALPHABET[((b0 << 4) | ((b1 & 255) >> 4)) & 63];
            i = a + 1;
            ar[a] = ALPHABET[((b1 << 2) | ((b2 & 255) >> 6)) & 63];
            a = i + 1;
            ar[i] = ALPHABET[b2 & 63];
            i2 = i3;
        }
        switch (size % 3) {
            case 1:
                i = a - 1;
                ar[i] = '=';
                break;
            case 2:
                i = a;
                break;
            default:
                i = a;
                break;
        }
        ar[i - 1] = '=';
        return new String(ar);
    }

    public static byte[] decode(String s) {
        int delta = s.endsWith("==") ? 2 : s.endsWith("=") ? 1 : 0;
        byte[] buffer = new byte[(((s.length() * 3) / 4) - delta)];
        int index = 0;
        int i = 0;
        while (i < s.length()) {
            int c0 = toInt[s.charAt(i)];
            int c1 = toInt[s.charAt(i + 1)];
            int i2 = index + 1;
            buffer[index] = (byte) (((c0 << 2) | (c1 >> 4)) & 255);
            if (i2 < buffer.length) {
                int c2 = toInt[s.charAt(i + 2)];
                index = i2 + 1;
                buffer[i2] = (byte) (((c1 << 4) | (c2 >> 2)) & 255);
                if (index >= buffer.length) {
                    break;
                }
                i2 = index + 1;
                buffer[index] = (byte) (((c2 << 6) | toInt[s.charAt(i + 3)]) & 255);
                i += 4;
                index = i2;
            } else {
                index = i2;
                break;
            }
        }
        return buffer;
    }

    private Base64() {
    }
}

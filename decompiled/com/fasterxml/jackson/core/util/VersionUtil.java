package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.regex.Pattern;

public class VersionUtil {
    private static final Pattern VERSION_SEPARATOR = Pattern.compile("[-_./;:]");
    private final Version _version;

    protected VersionUtil() {
        Version version = null;
        try {
            version = versionFor(getClass());
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load Version information from " + getClass());
        }
        if (version == null) {
            version = Version.unknownVersion();
        }
        this._version = version;
    }

    public Version version() {
        return this._version;
    }

    public static Version versionFor(Class<?> cls) {
        Version packageVersionFor = packageVersionFor(cls);
        if (packageVersionFor != null) {
            return packageVersionFor;
        }
        Closeable resourceAsStream = cls.getResourceAsStream("VERSION.txt");
        if (resourceAsStream == null) {
            return Version.unknownVersion();
        }
        try {
            packageVersionFor = doReadVersion(new InputStreamReader(resourceAsStream, "UTF-8"));
            return packageVersionFor;
        } catch (UnsupportedEncodingException e) {
            packageVersionFor = Version.unknownVersion();
            return packageVersionFor;
        } finally {
            _close(resourceAsStream);
        }
    }

    public static Version packageVersionFor(Class<?> cls) {
        Class cls2;
        try {
            cls2 = Class.forName(cls.getPackage().getName() + ".PackageVersion", true, cls.getClassLoader());
            return ((Versioned) cls2.newInstance()).version();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to get Versioned out of " + cls2);
        } catch (Exception e2) {
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.fasterxml.jackson.core.Version doReadVersion(java.io.Reader r5) {
        /*
        r0 = 0;
        r3 = new java.io.BufferedReader;
        r3.<init>(r5);
        r2 = r3.readLine();	 Catch:{ IOException -> 0x002a, all -> 0x0031 }
        if (r2 == 0) goto L_0x003b;
    L_0x000c:
        r1 = r3.readLine();	 Catch:{ IOException -> 0x0036, all -> 0x0031 }
        if (r1 == 0) goto L_0x0016;
    L_0x0012:
        r0 = r3.readLine();	 Catch:{ IOException -> 0x0039, all -> 0x0031 }
    L_0x0016:
        _close(r3);
    L_0x0019:
        if (r1 == 0) goto L_0x001f;
    L_0x001b:
        r1 = r1.trim();
    L_0x001f:
        if (r0 == 0) goto L_0x0025;
    L_0x0021:
        r0 = r0.trim();
    L_0x0025:
        r0 = parseVersion(r2, r1, r0);
        return r0;
    L_0x002a:
        r1 = move-exception;
        r1 = r0;
        r2 = r0;
    L_0x002d:
        _close(r3);
        goto L_0x0019;
    L_0x0031:
        r0 = move-exception;
        _close(r3);
        throw r0;
    L_0x0036:
        r1 = move-exception;
        r1 = r0;
        goto L_0x002d;
    L_0x0039:
        r4 = move-exception;
        goto L_0x002d;
    L_0x003b:
        r1 = r0;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.util.VersionUtil.doReadVersion(java.io.Reader):com.fasterxml.jackson.core.Version");
    }

    public static Version mavenVersionFor(ClassLoader classLoader, String str, String str2) {
        Closeable resourceAsStream = classLoader.getResourceAsStream("META-INF/maven/" + str.replaceAll("\\.", "/") + "/" + str2 + "/pom.properties");
        if (resourceAsStream != null) {
            Version parseVersion;
            try {
                Properties properties = new Properties();
                properties.load(resourceAsStream);
                parseVersion = parseVersion(properties.getProperty(ClientCookie.VERSION_ATTR), properties.getProperty("groupId"), properties.getProperty("artifactId"));
                return parseVersion;
            } catch (IOException e) {
                parseVersion = e;
            } finally {
                _close(resourceAsStream);
            }
        }
        return Version.unknownVersion();
    }

    public static Version parseVersion(String str, String str2, String str3) {
        String str4 = null;
        int i = 0;
        if (str != null) {
            CharSequence trim = str.trim();
            if (trim.length() > 0) {
                String[] split = VERSION_SEPARATOR.split(trim);
                int parseVersionPart = parseVersionPart(split[0]);
                int parseVersionPart2 = split.length > 1 ? parseVersionPart(split[1]) : 0;
                if (split.length > 2) {
                    i = parseVersionPart(split[2]);
                }
                if (split.length > 3) {
                    str4 = split[3];
                }
                return new Version(parseVersionPart, parseVersionPart2, i, str4, str2, str3);
            }
        }
        return null;
    }

    protected static int parseVersionPart(String str) {
        int i = 0;
        int length = str.length();
        int i2 = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt > '9' || charAt < '0') {
                break;
            }
            i2 = (i2 * 10) + (charAt - 48);
            i++;
        }
        return i2;
    }

    private static final void _close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
        }
    }

    public static final void throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }
}

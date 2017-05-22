/**
 * 
 */
package org.teste.memoria.command;

/**
 * @author marcio.ferreira
 * 
 */
public class OSUtils {
    public static String getOsName() {
        return System.getProperty("os.name", "unknown");
    }

    public static String platform() {
        final String osname = System.getProperty("os.name", "generic").toLowerCase();
        if (osname.startsWith("windows")) {
            return "win32";
        } else if (osname.startsWith("linux")) {
            return "linux";
        } else if (osname.startsWith("sunos")) {
            return "solaris";
        } else if (osname.startsWith("mac") || osname.startsWith("darwin")) {
            return "mac";
        } else {
            return "generic";
        }
    }

    public static boolean isWindows() {
        return OSUtils.getOsName().toLowerCase().indexOf("windows") >= 0;
    }

    public static boolean isLinux() {
        return OSUtils.getOsName().toLowerCase().indexOf("linux") >= 0;
    }

    public static boolean isUnix() {
        final String os = OSUtils.getOsName().toLowerCase();

        // XXX: this obviously needs some more work to be "true" in general (see bottom of file)
        if (os.indexOf("sunos") >= 0 || os.indexOf("linux") >= 0) {
            return true;
        }

        if (OSUtils.isMac() && System.getProperty("os.version", "").startsWith("10.")) {
            return true;
        }

        return false;
    }

    public static boolean isMac() {
        final String os = OSUtils.getOsName().toLowerCase();
        return os.startsWith("mac") || os.startsWith("darwin");
    }

    public static boolean isSolaris() {
        final String os = OSUtils.getOsName().toLowerCase();
        return os.indexOf("sunos") >= 0;
    }
}

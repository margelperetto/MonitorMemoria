package org.teste.memoria.command;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class IOUtils {

    private static String downloadAndRun(final String asset) throws IOException {
        final String vbsPath = IOUtils.loadAsset(asset);
        final String returned = IOUtils.runProcess("cscript.exe //NoLogo " + vbsPath, false, null);
        return returned;
    }

    public static String getSerialHD() throws IOException {
        String serialHD = "";
        try {
            serialHD = IOUtils.downloadAndRun("win32_diskdrive.vbs");
            serialHD = serialHD.substring(serialHD.indexOf("{'serialNumber':'") + 17, serialHD.indexOf("'}"));
        } catch (final Exception e) {
           e.printStackTrace();
        }
        return serialHD;
    }

    public static String runProcess(final String cmd, final boolean isLinux, final List<String> args) {
        Process process = null;
        String ret = "";
        try {
            process = IOUtils.executeProcess(cmd, args);
            ret = IOUtils.readLine(process);
            process.waitFor();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String readLine(final Process process) {
        final StringBuilder ret = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                ret.append(line);
                ret.append("\n");
            }

            if (ret == null || ret.equals("")) {
                br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                final StringBuilder s = new StringBuilder();
                s.append(br.readLine() != null ? br.readLine().toString() : "Invalid");
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return ret.toString();
    }

    public static Process executeProcess(String cmd, final List<String> args) {
        Process process = null;
        if (args != null && args.size() > 0) {
            for (final String arg : args) {
                cmd += " " + arg;
            }
        }
        try {
            process = Runtime.getRuntime().exec(CommandParser.parse(cmd));
        } catch (final IOException e) {
           e.printStackTrace();
        }

        return process;

    }

    public static String loadAsset(final String exefile) throws IOException {
        final File tempDir = new File(String.valueOf(System.getProperty("java.io.tmpdir")));
        final File dest = new File(tempDir + File.separator + exefile);

        if (dest.exists()) {
            return dest.getAbsolutePath();
        }

        InputStream stream = null;
        OutputStream out = null;
        FileOutputStream fileOutputStream = null;
        try {
            stream = IOUtils.class.getResourceAsStream("/" + exefile);
            if (stream == null) {
                throw new FileNotFoundException("File not found");
            }

            final File parent = dest.getParentFile();
            if (!parent.exists()) {
                if (!parent.mkdirs()) {
                    throw new IOException("Error on create dir: " + parent.getAbsolutePath());
                }
            }
            fileOutputStream = new FileOutputStream(dest);
            out = new BufferedOutputStream(fileOutputStream);
            int c;
            final byte[] buf = new byte[1024];
            while ((c = stream.read(buf)) != -1) {
                out.write(buf, 0, c);
            }
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (final Exception e) {
                	e.printStackTrace();
                }
            }

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (final Exception e) {
                	e.printStackTrace();
                }
            }
        }

        dest.deleteOnExit();
        return dest.getAbsolutePath();
    }

    public static String getHostName() {
        String hostName = "";
        if (OSUtils.isWindows()) {
            hostName = System.getenv("computername");
        } else {
            String nmHost = System.getenv("HOSTNAME");

            if (nmHost == null || nmHost.trim().length() <= 0) {
                final Runtime run = Runtime.getRuntime();
                Process proc;
                String hostNameAux = null;
                try {
                    proc = run.exec("hostname");
                    final BufferedInputStream in = new BufferedInputStream(proc.getInputStream());
                    final byte[] b = new byte[in.available()];
                    in.read(b, 0, in.available());
                    hostNameAux = new String(b);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                nmHost = hostNameAux;
            }
            hostName = nmHost;
        }
        return hostName;
    }

}

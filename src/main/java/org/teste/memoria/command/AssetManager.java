package org.teste.memoria.command;

import java.util.List;

public class AssetManager {

    public static ResponseVo runProcessVo(final String cmd, final boolean isLinux, final List<String> args) {
        final ResponseVo vbsVo = new ResponseVo();
        String ret = "";
        int result = -1;
        final Process process = IOUtils.executeProcess(cmd, args);
        ret = IOUtils.readLine(process);
        try {
            result = process.waitFor();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        vbsVo.setResponseCode(result);
        vbsVo.setResponseDescription(ret);

        return vbsVo;
    }

}

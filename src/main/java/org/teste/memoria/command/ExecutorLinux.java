package org.teste.memoria.command;

import java.util.List;
import org.teste.memoria.command.CmdResponse.CollectorError;

public abstract class ExecutorLinux {

    public static CmdResponse execCommand(final String cmd) throws Exception {
        return runCommand(cmd, null);
    }

    private  static CmdResponse runCommand(final String cmd, final List<String> args) throws Exception {
        CmdResponse response = new CmdResponse();

        response = runProcessVo(cmd, true, args);
        response.setErrorType(CollectorError.SUCCESS);
        return response;
    }

    private static CmdResponse runProcessVo(final String cmd, final boolean isLinux, final List<String> args) throws Exception {
        final CmdResponse vbsVo = new CmdResponse();
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

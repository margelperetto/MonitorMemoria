package org.teste.memoria.command;

import java.util.List;
import org.teste.memoria.command.ResponseVo.CollectorError;

public abstract class SEExecutorLinux {

    public static ResponseVo execCommand(final String cmd) throws Exception {
        return runCommand(cmd, null);
    }

    private  static ResponseVo runCommand(final String cmd, final List<String> args) throws Exception {
        ResponseVo response = new ResponseVo();

        response = AssetManager.runProcessVo(cmd, true, args);
        response.setErrorType(CollectorError.SUCCESS);
        return response;
    }

}

package org.teste.memoria.infos;

import org.teste.memoria.command.CmdResponse;
import org.teste.memoria.command.CmdResponse.CollectorError;
import org.teste.memoria.command.ExecutorLinux;

public class AvailableMemoryLinux extends MemoryLinux{

	public Integer loadInfo(Object... params) throws Exception {
		Integer availableMemory = 0;
		CmdResponse resp = null;
		resp = ExecutorLinux.execCommand("free -k -t | grep 'Total'");
		if (resp.getErrorType() == CollectorError.SUCCESS && !resp.getResponseDescription().equalsIgnoreCase("")) {
			resp = ExecutorLinux.execCommand("cat /proc/meminfo | grep 'MemFree:'");
			if (resp.getErrorType() == CollectorError.SUCCESS && !resp.getResponseDescription().equalsIgnoreCase("")) {
				availableMemory = parseMemory(resp.getResponseDescription(), "MemFree:", "buffers:");
			}
		}
		return availableMemory;
	}
	
}

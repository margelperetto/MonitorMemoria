package org.teste.memoria.infos;

import org.teste.memoria.command.CmdResponse;
import org.teste.memoria.command.CmdResponse.CollectorError;
import org.teste.memoria.command.ExecutorLinux;

public class TotalMemoryLinux extends MemoryLinux{

	public Integer loadInfo(Object... params) throws Exception {
		Integer qtTotalMemory = null;
		Integer totalMemory = 0;
		final String memoryType = "MemTotal:";
		CmdResponse response = null;
		try {
			response = ExecutorLinux.execCommand("cat /proc/meminfo | grep '" + memoryType + "'");
			if (response.getErrorType() == CollectorError.SUCCESS && !response.getResponseDescription().equalsIgnoreCase("")) {
				totalMemory = parseMemory(response.getResponseDescription(), memoryType, "memfree:");
			}
			qtTotalMemory = totalMemory;
		} catch (final Exception e) {
			throw new Exception("Error executor command: " + "cat /proc/meminfo | grep '" + memoryType + "'", e);
		}
		return qtTotalMemory;
	}

}

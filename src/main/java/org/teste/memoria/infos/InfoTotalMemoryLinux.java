package org.teste.memoria.infos;

import org.teste.memoria.command.ResponseVo;
import org.teste.memoria.command.ResponseVo.CollectorError;
import org.teste.memoria.command.SEExecutorLinux;

public class InfoTotalMemoryLinux extends InfoMemoryLinux{

	public Integer loadInfo(Object... params) throws Exception {
		Integer qtTotalMemory = null;
		Integer totalMemory = 0;
		final String memoryType = "MemTotal:";
		ResponseVo response = null;
		try {
			response = SEExecutorLinux.execCommand("cat /proc/meminfo | grep '" + memoryType + "'");
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

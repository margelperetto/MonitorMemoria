package org.teste.memoria.infos;

import org.teste.memoria.command.ResponseVo;
import org.teste.memoria.command.ResponseVo.CollectorError;
import org.teste.memoria.command.SEExecutorLinux;

public class InfoAvailableMemoryLinux extends InfoMemoryLinux{

	public Integer loadInfo(Object... params) throws Exception {
		Integer qtAvailableMemory = null;
		Integer availableMemory = 0;
		final String memoryType = "Total:";
		ResponseVo response = null;
		response = SEExecutorLinux.execCommand("free -k -t | grep 'Total'");
		if (response.getErrorType() == CollectorError.SUCCESS && !response.getResponseDescription().equalsIgnoreCase("")) {
//			availableMemory = parseArrayMemorySwap(response.getResponseDescription(), memoryType, "buffers:");
//			if (availableMemory == 0) {
				response = SEExecutorLinux.execCommand("cat /proc/meminfo | grep 'MemFree:'");
				if (response.getErrorType() == CollectorError.SUCCESS && !response.getResponseDescription().equalsIgnoreCase("")) {
					availableMemory = parseMemory(response.getResponseDescription(), "MemFree:", "buffers:");
				}
//			}
		}
		qtAvailableMemory = availableMemory;
		return qtAvailableMemory;
	}

}

package org.teste.memoria.infos;


public abstract class InfoMemoryLinux {

	protected Integer parseMemory(String memoryResponse, final String memType, final String stringFinal) {
		Integer availableMemory = 0;
		memoryResponse = memoryResponse.toLowerCase();
		final String[] verifyArray = memoryResponse.split("\n");
		if (verifyArray.length == 1) {
			final int memFreeindex = memoryResponse.toLowerCase().indexOf(memType.toLowerCase()) + memType.length();
			if (memFreeindex >= 8) {
				availableMemory = this.returnInteger(memoryResponse, memType, stringFinal, memFreeindex);
			}
		} else {
			availableMemory = this.parseResponse(memoryResponse, memType);
		}
		return availableMemory;
	}

	private Integer parseResponse(final String response, final String memType) {
		String newResponse = null;
		final String[] responseSplit = response.split("\n");
		for (final String responseArray : responseSplit) {
			if (responseArray.contains(memType.toLowerCase())) {
				newResponse = responseArray;
				break;
			}
		}
		return this.returnIntegerResponse(newResponse);
	}

	private Integer returnIntegerResponse(final String memoryResponse) {
		Integer availableMemory = 0;
		Integer dividir = 1;
		if (memoryResponse.indexOf("kb") > 0) {
			dividir = 1024;
		}
		final StringBuffer sb = new StringBuffer();
		final char[] caracteres = memoryResponse.toCharArray();
		for (final Character caracter : caracteres) {
			if (Character.isDigit(caracter)) {
				sb.append(caracter);
			}
		}
		availableMemory = Integer.parseInt(sb.toString());
		availableMemory = availableMemory / dividir;
		return availableMemory;
	}

	private Integer returnInteger(final String memoryResponse, final String memType, final String stringFinal, final int memFreeindex) {
		Integer availableMemory = 0;
		Integer dividir = 1;
		String version = null;
		version = memoryResponse.substring(memFreeindex, memoryResponse.indexOf(stringFinal));
		if (version.indexOf("kb") > 0) {
			dividir = 1024;
		}
		final StringBuffer sb = new StringBuffer();
		final char[] caracteres = version.toCharArray();
		for (final Character caracter : caracteres) {
			if (Character.isDigit(caracter)) {
				sb.append(caracter);
			}
		}
		availableMemory = Integer.parseInt(sb.toString());
		availableMemory = availableMemory / dividir;
		return availableMemory;
	}

	protected Integer parseArrayMemorySwap(final String memoryResponse, final String memType, final String stringFinal) throws Exception {
		final Integer availableMemory = 0;
		final String[] verifyArray = memoryResponse.split("\n");
		if (verifyArray.length > 1) {
			for (int i = 0; i < verifyArray.length; i++) {
				if (verifyArray[i].contains(memType)) {
					final String array = verifyArray[i];
					return parseValueMemorySwap(array);
				}
			}
		} else {
			return parseValueMemorySwap(memoryResponse);
		}
		return availableMemory;
	}

	private Integer parseValueMemorySwap(final String array) throws Exception {
		final String lastString = array.substring(array.lastIndexOf(" "), array.length()).trim();
		if (lastString != null && !lastString.equals("")) {
			final Integer value = Integer.parseInt(lastString);
			return value / 1024;
		}
		return 0;
	}
}

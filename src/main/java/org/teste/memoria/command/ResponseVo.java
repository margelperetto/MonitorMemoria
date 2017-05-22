package org.teste.memoria.command;

public class ResponseVo {
    private int status;
    private CollectorError errorType;
    private String responseDescription;
    private String errorDescription;
    private int responseCode;
    private String objectValue;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public CollectorError getErrorType() {
        return this.errorType;
    }

    public void setErrorType(final CollectorError errorType) {
        this.errorType = errorType;
    }

    public String getResponseDescription() {
        return this.responseDescription;
    }

    public void setResponseDescription(final String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(final int responseCode) {
        this.responseCode = responseCode;
    }

    public String getObjectValue() {
        return this.objectValue;
    }

    public void setObjectValue(final String objectValue) {
        this.objectValue = objectValue;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public void setErrorDescription(final String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ResponseVo [status=");
        builder.append(this.status);
        builder.append(", errorType=");
        builder.append(this.errorType);
        builder.append(", responseDescription=");
        builder.append(this.responseDescription);
        builder.append(", errorDescription=");
        builder.append(this.errorDescription);
        builder.append(", responseCode=");
        builder.append(this.responseCode);
        builder.append(", objectValue=");
        builder.append(this.objectValue);
        builder.append("]");
        return builder.toString();
    }
    
    public static enum CollectorError {
        SUCCESS, EXEC_PERMISSION, CMD_EXEC_ERROR, CMD_INTERRUPT_ERROR, COMMAND_NOT_FOUND, VBS_INSTALL_ERROR, VBS_EXEC_ERROR, JS_ERROR, JS_PERMISSION
    }
}

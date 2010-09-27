package com.espertech.esperio.socket.config;

public class SocketConfig {
    private int port;
    private DataType dataType;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}

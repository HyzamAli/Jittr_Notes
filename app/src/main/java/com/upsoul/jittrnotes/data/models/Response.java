package com.upsoul.jittrnotes.data.models;

public class Response<T> {
    private STATUS status;
    private T data;

    public Response(STATUS status, T data) {
        this.status = status;
        this.data = data;
    }

    public Response(STATUS status) {
        this.status = status;
        data = null;
    }

    public STATUS getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}

package me.chaoticva.heather;

public class Response<T> {
    public Response<T> data(T data) {
        this.data = data;
        return this;
    }

    public Response<T> statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public T getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    private T data;

    private int statusCode;
}

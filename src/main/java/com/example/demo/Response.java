package com.example.demo;

public class Response {
    private int value;

    public Response() {
    }

    public Response(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Response{" +
                "value=" + value +
                '}';
    }
}

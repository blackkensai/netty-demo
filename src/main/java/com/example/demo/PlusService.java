package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class PlusService {
    public Response execute(Request request) {
        return new Response(request.getA() + request.getB());
    }
}

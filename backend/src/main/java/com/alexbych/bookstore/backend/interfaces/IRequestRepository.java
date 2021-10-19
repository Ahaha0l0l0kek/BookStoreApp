package com.alexbych.bookstore.backend.interfaces;

import com.alexbych.bookstore.model.Request;

import java.util.List;

public interface IRequestRepository {
    List<Request> getAllRequests();

    Request getRequestById(long requestId);

    Request createRequest(Request request);

    Request updateRequest(Request request);

    void deleteRequest(long requestId);
}

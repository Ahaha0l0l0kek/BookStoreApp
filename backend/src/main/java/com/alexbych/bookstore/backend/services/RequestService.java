package com.alexbych.bookstore.backend.services;

import com.alexbych.bookstore.backend.converters.IRequestConverter;
import com.alexbych.bookstore.backend.dto.RequestDTO;
import com.alexbych.bookstore.backend.interfaces.IRequestRepository;
import com.alexbych.bookstore.model.Request;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestService {

    private final IRequestRepository requestRepository;
    private final IRequestConverter requestConverter;

    public List<RequestDTO> getAllRequests() {
        return requestRepository.getAllRequests().stream().map(requestConverter::fromRequestToRequestDto).collect(Collectors.toList());
    }

    public RequestDTO getRequestById(long requestId) {
        return requestConverter.fromRequestToRequestDto(requestRepository.getRequestById(requestId));
    }

    public RequestDTO createRequest(RequestDTO requestDTO) {
        requestRepository.createRequest(requestConverter.fromRequestDtoToRequest(requestDTO));
        return requestDTO;
    }

    public RequestDTO updateRequest(RequestDTO requestDTO) {
        requestRepository.updateRequest(requestConverter.fromRequestDtoToRequest(requestDTO));
        return requestDTO;
    }

    public void deleteRequest(long requestId) {
        requestRepository.deleteRequest(requestId);
    }

    public boolean changeStatusToCompleted(long id) {
        Request request = requestRepository.getRequestById(id);
        request.setCompleted(true);
        requestRepository.updateRequest(request);
        return requestRepository.getRequestById(id).isCompleted();
    }
}

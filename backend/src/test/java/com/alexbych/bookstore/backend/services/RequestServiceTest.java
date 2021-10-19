package com.alexbych.bookstore.backend.services;

import com.alexbych.bookstore.backend.converters.IRequestConverter;
import com.alexbych.bookstore.backend.dto.RequestDTO;
import com.alexbych.bookstore.backend.interfaces.IRequestRepository;
import com.alexbych.bookstore.backend.prototype.RequestPrototype;
import com.alexbych.bookstore.model.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RequestServiceTest {

    @MockBean
    private IRequestRepository requestRepository;

    @MockBean
    private IRequestConverter requestConverter;

    @Autowired
    private RequestService requestService;

    @Test
    public void getAllRequests(){
        Request aRequest = RequestPrototype.aRequest();
        List<RequestDTO> expected = new ArrayList<>();
        expected.add(RequestPrototype.aRequestDTO());
        when(requestRepository.getAllRequests()).thenReturn(new ArrayList<>(List.of(aRequest)));
        when(requestConverter.fromRequestToRequestDto(aRequest)).thenReturn(RequestPrototype.aRequestDTO());
        List<RequestDTO> allRequests = this.requestService.getAllRequests();
        assertThat(expected).isEqualTo(allRequests);
    }

    @Test
    public void getRequestById(){
        Request aRequest = RequestPrototype.aRequest();
        RequestDTO expected = RequestPrototype.aRequestDTO();
        when(requestRepository.getRequestById(anyLong())).thenReturn(aRequest);
        when(requestConverter.fromRequestToRequestDto(aRequest)).thenReturn(RequestPrototype.aRequestDTO());
        RequestDTO actual = this.requestService.getRequestById(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void createRequest(){
        Request aRequest = RequestPrototype.aRequest();
        RequestDTO expected = RequestPrototype.aRequestDTO();
        when(requestRepository.createRequest(any())).thenReturn(aRequest);
        when(requestConverter.fromRequestToRequestDto(aRequest)).thenReturn(RequestPrototype.aRequestDTO());
        RequestDTO actual = this.requestService.createRequest(RequestPrototype.aRequestDTO());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateRequest(){
        Request aRequest = RequestPrototype.aRequest();
        RequestDTO expected = RequestPrototype.aRequestDTO();
        when(requestRepository.createRequest(any())).thenReturn(aRequest);
        when(requestConverter.fromRequestToRequestDto(aRequest)).thenReturn(RequestPrototype.aRequestDTO());
        RequestDTO actual = this.requestService.updateRequest(RequestPrototype.aRequestDTO());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void deleteRequest(){
        requestService.deleteRequest(RequestPrototype.aRequest().getId());
        verify(requestRepository, times(1)).deleteRequest(RequestPrototype.aRequest().getId());
    }

    @Test
    public void changeStatusToCompleted(){
        Request notAvailableRequest = RequestPrototype.aRequest();
        notAvailableRequest.setCompleted(false);
        Request request = RequestPrototype.aRequest();
        when(requestRepository.getRequestById(anyLong())).thenReturn(notAvailableRequest);
        when((requestRepository.updateRequest(notAvailableRequest))).thenReturn(request);
        assertThat(requestService.changeStatusToCompleted(notAvailableRequest.getBookId())).isTrue();
    }
}

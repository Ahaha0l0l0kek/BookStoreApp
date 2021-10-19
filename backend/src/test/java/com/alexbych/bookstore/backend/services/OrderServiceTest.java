package com.alexbych.bookstore.backend.services;

import com.alexbych.bookstore.backend.converters.IOrderConverter;
import com.alexbych.bookstore.backend.dto.OrderDTO;
import com.alexbych.bookstore.backend.interfaces.IBookRepository;
import com.alexbych.bookstore.backend.interfaces.IClientRepository;
import com.alexbych.bookstore.backend.interfaces.IOrderRepository;
import com.alexbych.bookstore.backend.prototype.BookPrototype;
import com.alexbych.bookstore.backend.prototype.ClientPrototype;
import com.alexbych.bookstore.backend.prototype.OrderPrototype;
import com.alexbych.bookstore.model.Book;
import com.alexbych.bookstore.model.Client;
import com.alexbych.bookstore.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @MockBean
    private IOrderRepository orderRepository;

    @MockBean
    private IBookRepository bookRepository;

    @MockBean
    private IClientRepository clientRepository;

    @MockBean
    private IOrderConverter orderConverter;

    @Autowired
    private OrderService orderService;

    @Test
    public void getAllOrders(){
        Order aOrder = OrderPrototype.aOrder();
        List<OrderDTO> expected = new ArrayList<>();
        expected.add(OrderPrototype.aOrderDTO());
        when(orderRepository.getAllOrders()).thenReturn(new ArrayList<>(List.of(aOrder)));
        when(orderConverter.fromOrderToOrderDto(aOrder)).thenReturn(OrderPrototype.aOrderDTO());
        List<OrderDTO> allOrders = this.orderService.getAllOrders();
        assertThat(expected).isEqualTo(allOrders);
    }

    @Test
    public void getOrderById(){
        Order aOrder = OrderPrototype.aOrder();
        OrderDTO expected = OrderPrototype.aOrderDTO();
        when(orderRepository.getOrderById(anyLong())).thenReturn(aOrder);
        when(orderConverter.fromOrderToOrderDto(aOrder)).thenReturn(OrderPrototype.aOrderDTO());
        OrderDTO actual = this.orderService.getOrderById(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void createOrder(){
        Order aOrder = OrderPrototype.aOrder();
        OrderDTO expected = OrderPrototype.aOrderDTO();
        when(orderRepository.createOrder(any())).thenReturn(aOrder);
        when(orderConverter.fromOrderToOrderDto(aOrder)).thenReturn(OrderPrototype.aOrderDTO());
        OrderDTO actual = this.orderService.createOrder(OrderPrototype.aOrderDTO());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateOrder(){
        Order aOrder = OrderPrototype.aOrder();
        OrderDTO expected = OrderPrototype.aOrderDTO();
        when(orderRepository.updateOrder(any())).thenReturn(aOrder);
        when(orderConverter.fromOrderToOrderDto(aOrder)).thenReturn(OrderPrototype.aOrderDTO());
        OrderDTO actual = this.orderService.updateOrder(OrderPrototype.aOrderDTO());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void deleteOrder(){
        orderService.deleteOrder(OrderPrototype.aOrder().getId());
        verify(orderRepository, times(1)).deleteOrder(OrderPrototype.aOrder().getId());
    }

    @Test
    public void fulfilledOrders(){
        LocalDate firstDate = LocalDate.of(2020, 2, 12);
        LocalDate lastDate = LocalDate.of(2020, 4, 12);
        Order aOrder1 = OrderPrototype.aOrder();
        Order aOrder2 = OrderPrototype.aOrder();
        aOrder2.setDateOfOrder(aOrder2.getDateOfOrder().plusMonths(4));
        OrderDTO aOrderDTO1 = OrderPrototype.aOrderDTO();
        OrderDTO aOrderDTO2 = OrderPrototype.aOrderDTO();
        aOrderDTO2.setDateOfOrder(aOrderDTO2.getDateOfOrder().plusMonths(4));
        when(orderRepository.getAllOrders()).thenReturn(new ArrayList<>(List.of(aOrder1, aOrder2)));
        when(orderConverter.fromOrderToOrderDto(aOrder1)).thenReturn(aOrderDTO1);
        when(orderConverter.fromOrderToOrderDto(aOrder2)).thenReturn(aOrderDTO2);
        List<OrderDTO> actual = orderService.fulfilledOrders(firstDate, lastDate);
        assertThat(aOrderDTO1).isEqualTo(actual.get(0));
    }

    @Test
    public void countOfFulfilled(){
        LocalDate firstDate = LocalDate.of(2020, 2, 12);
        LocalDate lastDate = LocalDate.of(2020, 4, 12);
        Order aOrder1 = OrderPrototype.aOrder();
        Order aOrder2 = OrderPrototype.aOrder();
        aOrder2.setDateOfOrder(aOrder2.getDateOfOrder().plusMonths(4));
        OrderDTO aOrderDTO1 = OrderPrototype.aOrderDTO();
        OrderDTO aOrderDTO2 = OrderPrototype.aOrderDTO();
        aOrderDTO2.setDateOfOrder(aOrderDTO2.getDateOfOrder().plusMonths(4));
        when(orderRepository.getAllOrders()).thenReturn(new ArrayList<>(List.of(aOrder1, aOrder2)));
        when(orderConverter.fromOrderToOrderDto(aOrder1)).thenReturn(aOrderDTO1);
        when(orderConverter.fromOrderToOrderDto(aOrder2)).thenReturn(aOrderDTO2);
        List<OrderDTO> actual = orderService.fulfilledOrders(firstDate, lastDate);
        assertThat(1).isEqualTo(actual.size());
    }

    @Test
    public void totalPriceInTime(){
        LocalDate firstDate = LocalDate.of(2020, 2, 12);
        LocalDate lastDate = LocalDate.of(2020, 4, 12);
        when(orderRepository.getAllOrders()).thenReturn(new ArrayList<>(List.of(OrderPrototype.aOrder(), OrderPrototype.aOrder())));
        float actual = orderService.totalPriceInTime(firstDate, lastDate);
        assertThat(actual).isEqualTo(1800);
    }

    @Test
    public void changeOrderStatus(){
        Order newStatusOrder = OrderPrototype.aOrder();
        newStatusOrder.setOrderStatus("COMPLETED");
        when(orderRepository.getOrderById(anyLong())).thenReturn(OrderPrototype.aOrder());
        when(orderRepository.updateOrder(OrderPrototype.aOrder())).thenReturn(newStatusOrder);
        assertThat(orderService.changeOrderStatus(newStatusOrder.getId(), "COMPLETED")).isTrue();
    }

    @Test
    public void orderCompleted(){
        Order newStatusOrder = OrderPrototype.aOrder();
        newStatusOrder.setOrderStatus("COMPLETED");
        when(bookRepository.getAllBooks()).thenReturn(new ArrayList<>(List.of(BookPrototype.aBook())));
        when(orderRepository.getOrderById(BookPrototype.aBook().getId())).thenReturn(OrderPrototype.aOrder());
        when(orderRepository.updateOrder(OrderPrototype.aOrder())).thenReturn(newStatusOrder);
        assertThat(orderService.orderCompleted(BookPrototype.aBook().getId())).isTrue();
    }

    @Test
    public void detailsOfOrder(){
        Order aOrder = OrderPrototype.aOrder();
        Book aBook = BookPrototype.aBook();
        Client aClient = ClientPrototype.aClient();
        when(orderRepository.getOrderById(anyLong())).thenReturn(aOrder);
        when(bookRepository.getAllBooks()).thenReturn(new ArrayList<>(List.of(aBook)));
        when(clientRepository.getAllClients()).thenReturn(new ArrayList<>(List.of(aClient)));
        String actual = orderService.detailsOfOrder(1);
        assertThat(actual).isEqualTo(1 + " " + aClient.getName() + " " + aClient.getPhoneNumber() + " " +
                aBook.getName() + " " + aBook.getAuthor());
    }
}

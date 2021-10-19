package com.alexbych.bookstore.backend.converters;

import com.alexbych.bookstore.backend.dto.OrderDTO;
import com.alexbych.bookstore.model.Order;
import org.mapstruct.Mapper;

@Mapper
public interface IOrderConverter {
    Order fromOrderDtoToOrder(OrderDTO orderDTO);
    OrderDTO fromOrderToOrderDto(Order order);
}

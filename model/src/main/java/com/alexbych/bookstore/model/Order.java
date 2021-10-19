package com.alexbych.bookstore.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private long id;

    private float totalPrice;
    private long bookId;
    private long clientId;
    private LocalDate dateOfOrder;
    private OrderStatus orderStatus;
    private int quantity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "total_price")
    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "book_id")
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Column(name = "client_id")
    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    @Column(name = "date_of_order")
    public LocalDate getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(LocalDate dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    @Column(name = "order_status")
    public String getOrderStatus() {
        return switch (orderStatus) {
            case NEW -> "NEW";
            case COMPLETED -> "COMPLETED";
            case CANCELED -> "CANCELED";
        };
    }

    public void setOrderStatus(String orderStatus) {
        switch (orderStatus) {
            case "NEW" -> this.orderStatus = OrderStatus.NEW;
            case "COMPLETED" -> this.orderStatus = OrderStatus.COMPLETED;
            case "CANCELED" -> this.orderStatus = OrderStatus.CANCELED;
        }
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Order orderBook;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Order orderClient;

    @Override
    public String toString() {
        return "id=" + id +
                ", totalPrice=" + totalPrice +
                ", bookId=" + bookId +
                ", clientId=" + clientId +
                ", dateOfOrder=" + dateOfOrder +
                ", orderStatus=" + orderStatus +
                ", quantity=" + quantity;
    }
}

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
@Table(name = "requests")
public class Request implements Serializable {

    private long id;

    private long bookId;
    private int quantity;
    private LocalDate dateOfRequest;
    private boolean completed;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "book_id")
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "date_of_request")
    public LocalDate getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(LocalDate dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    @Column(name = "is_completed")
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Request request;

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ", dateOfRequest=" + dateOfRequest +
                ", isCompleted=" + completed +
                ", request=" + request +
                '}';
    }
}

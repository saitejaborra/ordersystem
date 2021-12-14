package com.techcrunchers.capstoneprojectdbs.beans;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.techcrunchers.capstoneprojectdbs.models.OrderDirection;
import com.techcrunchers.capstoneprojectdbs.models.OrderStatus;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderBook {
   
	@Id
    String orderId;
	
	@ManyToOne
    @NotNull
    @JoinColumn(name = "client_id")
    Client client;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "instrument_id")
    Instrument instrument;

//    @ManyToOne
//    @JoinColumn(name="custodian_id")
//    Custodian custodian;
//    
    @NotNull
    Double price;

    @NotNull
    Integer quantity;

    @NotNull
    Integer initialQuantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @NotNull   
    OrderDirection orderDirection;

    @NotNull
    Boolean limitOrder;

    @Temporal(TemporalType.TIMESTAMP)
    Date timeStamp;

    public OrderBook() {
		
		// TODO Auto-generated constructor stub
	}

	public OrderBook(String orderId, @NotNull Client client, @NotNull Instrument instrument, @NotNull Double price,
			@NotNull Integer quantity, @NotNull Integer initialQuantity, @NotNull OrderStatus orderStatus,
			@NotNull OrderDirection orderDirection, @NotNull Boolean limitOrder, Date timeStamp) {
		super();
		this.orderId = orderId;
		this.client = client;
		this.instrument = instrument;
		this.price = price;
		this.quantity = quantity;
		this.initialQuantity = initialQuantity;
		this.orderStatus = orderStatus;
		this.orderDirection = orderDirection;
		this.limitOrder = limitOrder;
		this.timeStamp = timeStamp;
		
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

//	public Custodian getCustodian() {
//		return custodian;
//	}
//
//	public void setCustodian(Custodian custodian) {
//		this.custodian = custodian;
//	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getInitialQuantity() {
		return initialQuantity;
	}

	public void setInitialQuantity(Integer initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderDirection getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(OrderDirection orderDirection) {
		this.orderDirection = orderDirection;
	}

	public Boolean getLimitOrder() {
		return limitOrder;
	}

	public void setLimitOrder(Boolean limitOrder) {
		this.limitOrder = limitOrder;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	


}

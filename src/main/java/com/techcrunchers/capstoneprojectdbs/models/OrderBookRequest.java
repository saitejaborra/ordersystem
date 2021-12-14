package com.techcrunchers.capstoneprojectdbs.models;

import com.techcrunchers.capstoneprojectdbs.beans.Client;
import com.techcrunchers.capstoneprojectdbs.beans.Instrument;

import lombok.Data;

@Data
public class OrderBookRequest {
    String clientId;
    public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public OrderBookRequest(String clientId, String instrumentId, Double price, Integer quantity,
			OrderDirection orderDirection, Boolean limitOrder) {
		super();
		this.clientId = clientId;
		this.instrumentId = instrumentId;
		this.price = price;
		this.quantity = quantity;
		this.orderDirection = orderDirection;
		this.limitOrder = limitOrder;
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
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	String instrumentId;
    Double price;
    Integer quantity;
    OrderDirection orderDirection;
    Boolean limitOrder;
}

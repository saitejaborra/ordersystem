package com.techcrunchers.capstoneprojectdbs.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Stocks {
    @Id
    @Column(name="stock_id")
    String stockId;

	@ManyToOne
    @NotNull
    @JoinColumn(name = "client_id")
    Client client;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "instrument_id")
    Instrument instrument;

    @NotNull
    @Column(name="quantity")
    Integer quantity;
    

    public Stocks() {
		
		// TODO Auto-generated constructor stub
	}

	public Stocks(String stockId, @NotNull Client client, @NotNull Instrument instrument, @NotNull Integer quantity) {
		super();
		this.stockId = stockId;
		this.client = client;
		this.instrument = instrument;
		this.quantity = quantity;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}

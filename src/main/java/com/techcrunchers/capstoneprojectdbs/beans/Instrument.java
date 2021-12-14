package com.techcrunchers.capstoneprojectdbs.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name="instruments")
public class Instrument {
    @Id
    @Column(name="instrument_id")
    String instrumentId;
    
    @NotNull
    @Column(name="instrument_name")
    String instrumentName;

    @Column(name="facevalue")
    Double faceValue;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name="expiry_date")
    Date expiryDate;

    public Instrument() {
    	
    }
    public Instrument(String instrumentId, @NotNull String instrumentName, Double faceValue, Date expiryDate) {
		super();
		this.instrumentId = instrumentId;
		this.instrumentName = instrumentName;
		this.faceValue = faceValue;
		this.expiryDate = expiryDate;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}

	public Double getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(Double faceValue) {
		this.faceValue = faceValue;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	
}

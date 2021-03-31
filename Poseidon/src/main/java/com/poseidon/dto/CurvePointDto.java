package com.poseidon.dto;

import java.sql.Timestamp;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.stereotype.Component;

@Component
public class CurvePointDto {

	private Integer curvePointId;
	
	@Positive(message="Must be a positive number")
	@NotNull(message="Must not be null")
	private Integer curveId;
	
	private Timestamp asOfDate;
	
	@Positive
	@DecimalMin(value="0.0", message = "This must be a positive decimal number")
	private Double term;
	
	@Positive
	@DecimalMin(value="0.0", message = "This must be a positive decimal number")
	private Double value;

	private Timestamp creationDate;
	
	public CurvePointDto() {
	}

	public Integer getCurvePointId() {
		return curvePointId;
	}

	public void setCurvePointId(Integer curvePointId) {
		this.curvePointId = curvePointId;
	}

	public Integer getCurveId() {
		return curveId;
	}
	
	public Timestamp getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	
}

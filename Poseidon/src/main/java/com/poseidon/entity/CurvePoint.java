package com.poseidon.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@Entity
@Table(name = "curve_point")
public class CurvePoint {
 	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="curve_point_id", length = 4)
	private Integer curvePointId;
	
	@Positive
	@NotBlank(message="Must not be null")
	@Column(name="curve_Id")
	private Integer curveId;
	
	@Column(name="as_of_date")
	private Timestamp asOfDate;
	
	@DecimalMin(value="0.0", message = "This must be a positive decimal number")
	@Column(name="term")
	private Double term;
	
	@DecimalMin(value="0.0", message = "This must be a positive decimal number")
	@Column(name="value")
	private Double value;
	
	@Column(name="creation_date")
	private Timestamp creationDate;

	public CurvePoint() {
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

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public Timestamp getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
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

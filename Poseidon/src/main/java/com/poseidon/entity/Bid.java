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

@Entity
@Table(name = "bid")
public class Bid {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="bid_id",length=4,nullable=false)
	private Integer bidId;
	
	@NotBlank(message = "Account is mandatory")
	@Column(name="account",length=30,nullable=false)
	private String account;
	
	@NotBlank(message="Type is mandatory")
	@Column(name="type",length=30,nullable=false)
	private String type;
	
	@DecimalMin(value="0.0", message = "This must be a positive decimal number")
	@Column(name="bid_quantity")
	private Double bidQuantity;
	
	@DecimalMin(value="0.0", message = "This must be a positive decimal number")
	@Column(name="ask_quantity")
	private Double askQuantity;
	
	@DecimalMin(value="0.0", message = "This must be a positive decimal number")
	@Column(name="bid")
	private Double bid;
	
	@DecimalMin(value="0.0", message = "This must be a positive decimal number")
	@Column(name="ask")
	private Double ask;
	
	@Column(name="benchmark",length=125)
	private String benchmark;
	
	@Column(name="bid_date")
	private Timestamp bidDate;
	
	@Column(name="commentary",length=125)
	private String commentary;
	
	@Column(name="security",length=125)
	private String security;
	
	@Column(name="status",length=10)
	private String status;
	
	@Column(name="trader",length=125)
	private String trader;
	
	@Column(name="book",length=125)
	private String book;
	
	@Column(name="creation_name",length=125)
	private String creationName;
	
	@Column(name="creation_date")
	private Timestamp creationDate;
	
	@Column(name="revision_name",length=125)
	private String revisionName;
	
	@Column(name="revision_date")
	private Timestamp revisionDate;
	
	@Column(name="deal_name",length=125)
	private String dealName;
	
	@Column(name="deal_type",length=125)
	private String dealType;
	
	@Column(name="source_list_id",length=125)
	private String sourceListId;
	
	@Column(name="side",length=125)
	private String side;

	public Bid() {
	}

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBidQuantity() {
		return bidQuantity;
	}

	public void setBidQuantity(Double bidQuantity) {
		this.bidQuantity = bidQuantity;
	}

	public Double getAskQuantity() {
		return askQuantity;
	}

	public void setAskQuantity(Double askQuantity) {
		this.askQuantity = askQuantity;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public Timestamp getBidDate() {
		return bidDate;
	}

	public void setBidDate(Timestamp bidDate) {
		this.bidDate = bidDate;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrader() {
		return trader;
	}

	public void setTrader(String trader) {
		this.trader = trader;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getRevisionName() {
		return revisionName;
	}

	public void setRevisionName(String revisionName) {
		this.revisionName = revisionName;
	}

	public Timestamp getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Timestamp revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getSourceListId() {
		return sourceListId;
	}

	public void setSourceListId(String sourceListId) {
		this.sourceListId = sourceListId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

}

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
	
	@Column(name="account",length=30,nullable=false)
	private String account;
	
	@Column(name="type",length=30,nullable=false)
	private String type;
	
	@Column(name="bid_quantity")
	private Double bidQuantity;
	
	@Column(name="ask_quantity")
	private Double askQuantity;
	
	@Column(name="bid")
	private Double bid;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((ask == null) ? 0 : ask.hashCode());
		result = prime * result + ((askQuantity == null) ? 0 : askQuantity.hashCode());
		result = prime * result + ((benchmark == null) ? 0 : benchmark.hashCode());
		result = prime * result + ((bid == null) ? 0 : bid.hashCode());
		result = prime * result + ((bidDate == null) ? 0 : bidDate.hashCode());
		result = prime * result + ((bidId == null) ? 0 : bidId.hashCode());
		result = prime * result + ((bidQuantity == null) ? 0 : bidQuantity.hashCode());
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((commentary == null) ? 0 : commentary.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((creationName == null) ? 0 : creationName.hashCode());
		result = prime * result + ((dealName == null) ? 0 : dealName.hashCode());
		result = prime * result + ((dealType == null) ? 0 : dealType.hashCode());
		result = prime * result + ((revisionDate == null) ? 0 : revisionDate.hashCode());
		result = prime * result + ((revisionName == null) ? 0 : revisionName.hashCode());
		result = prime * result + ((security == null) ? 0 : security.hashCode());
		result = prime * result + ((side == null) ? 0 : side.hashCode());
		result = prime * result + ((sourceListId == null) ? 0 : sourceListId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((trader == null) ? 0 : trader.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (ask == null) {
			if (other.ask != null)
				return false;
		} else if (!ask.equals(other.ask))
			return false;
		if (askQuantity == null) {
			if (other.askQuantity != null)
				return false;
		} else if (!askQuantity.equals(other.askQuantity))
			return false;
		if (benchmark == null) {
			if (other.benchmark != null)
				return false;
		} else if (!benchmark.equals(other.benchmark))
			return false;
		if (bid == null) {
			if (other.bid != null)
				return false;
		} else if (!bid.equals(other.bid))
			return false;
		if (bidDate == null) {
			if (other.bidDate != null)
				return false;
		} else if (!bidDate.equals(other.bidDate))
			return false;
		if (bidId == null) {
			if (other.bidId != null)
				return false;
		} else if (!bidId.equals(other.bidId))
			return false;
		if (bidQuantity == null) {
			if (other.bidQuantity != null)
				return false;
		} else if (!bidQuantity.equals(other.bidQuantity))
			return false;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (commentary == null) {
			if (other.commentary != null)
				return false;
		} else if (!commentary.equals(other.commentary))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (creationName == null) {
			if (other.creationName != null)
				return false;
		} else if (!creationName.equals(other.creationName))
			return false;
		if (dealName == null) {
			if (other.dealName != null)
				return false;
		} else if (!dealName.equals(other.dealName))
			return false;
		if (dealType == null) {
			if (other.dealType != null)
				return false;
		} else if (!dealType.equals(other.dealType))
			return false;
		if (revisionDate == null) {
			if (other.revisionDate != null)
				return false;
		} else if (!revisionDate.equals(other.revisionDate))
			return false;
		if (revisionName == null) {
			if (other.revisionName != null)
				return false;
		} else if (!revisionName.equals(other.revisionName))
			return false;
		if (security == null) {
			if (other.security != null)
				return false;
		} else if (!security.equals(other.security))
			return false;
		if (side == null) {
			if (other.side != null)
				return false;
		} else if (!side.equals(other.side))
			return false;
		if (sourceListId == null) {
			if (other.sourceListId != null)
				return false;
		} else if (!sourceListId.equals(other.sourceListId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (trader == null) {
			if (other.trader != null)
				return false;
		} else if (!trader.equals(other.trader))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
}

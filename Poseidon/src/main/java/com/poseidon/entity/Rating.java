package com.poseidon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "rating")
public class Rating {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rating_id",length = 4)
	private Integer ratingId;
	
	@Column(name="moodys_rating",length = 125)
	private String moodysRating;
	
	@Column(name="s_and_p_rating",length = 125)
	private String sandpRating;
	
	@Column(name="fitch_rating",length = 125)
	private String fitchRating;
	
	@Positive
	@Column(name="order_number")
	private Integer orderNumber;

	public Rating() {
	}

	public Integer getRatingId() {
		return ratingId;
	}

	public void setRatingId(Integer ratingId) {
		this.ratingId = ratingId;
	}

	public String getMoodysRating() {
		return moodysRating;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}

	public String getSandpRating() {
		return sandpRating;
	}

	public void setSandpRating(String sandpRating) {
		this.sandpRating = sandpRating;
	}

	public String getFitchRating() {
		return fitchRating;
	}

	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	
}

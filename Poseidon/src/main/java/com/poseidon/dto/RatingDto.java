package com.poseidon.dto;

import org.springframework.stereotype.Component;

@Component
public class RatingDto {

	private Integer ratingId;
	private String moodysRating;
	private String sandpRating;
	private String fitchRating;
	private Integer orderNumber;

	public RatingDto() {
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

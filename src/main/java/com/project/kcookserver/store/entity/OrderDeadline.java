package com.project.kcookserver.store.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class OrderDeadline {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderDeadlineId;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "storeId")
	private Store store;

	private int mondayDeadline;

	private int tuesdayDeadline;

	private int wednesdayDeadline;

	private int thursdayDeadline;

	private int fridayDeadline;

	private int saturdayDeadline;

	private int sundayDeadline;

	public OrderDeadline(int mondayDeadline, int tuesdayDeadline, int wednesdayDeadline, int thursdayDeadline, int fridayDeadline, int saturdayDeadline, int sundayDeadline) {
		this.mondayDeadline = mondayDeadline;
		this.tuesdayDeadline = tuesdayDeadline;
		this.wednesdayDeadline = wednesdayDeadline;
		this.thursdayDeadline = thursdayDeadline;
		this.fridayDeadline = fridayDeadline;
		this.saturdayDeadline = saturdayDeadline;
		this.sundayDeadline = sundayDeadline;
	}

	public OrderDeadline setStore(Store store) {
		this.store = store;
		return this;
	}

	public void updateDeadLine(int mondayDeadline, int tuesdayDeadline, int wednesdayDeadline, int thursdayDeadline, int fridayDeadline, int saturdayDeadline, int sundayDeadline) {
		this.mondayDeadline = mondayDeadline;
		this.tuesdayDeadline = tuesdayDeadline;
		this.wednesdayDeadline = wednesdayDeadline;
		this.thursdayDeadline = thursdayDeadline;
		this.fridayDeadline = fridayDeadline;
		this.saturdayDeadline = saturdayDeadline;
		this.sundayDeadline = sundayDeadline;
	}
}

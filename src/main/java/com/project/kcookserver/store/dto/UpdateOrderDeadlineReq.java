package com.project.kcookserver.store.dto;

import com.project.kcookserver.store.entity.OrderDeadline;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderDeadlineReq {

	private long storeId;

	private int mondayDeadline;

	private int tuesdayDeadline;

	private int wednesdayDeadline;

	private int thursdayDeadline;

	private int fridayDeadline;

	private int saturdayDeadline;

	private int sundayDeadline;

	public OrderDeadline toEntity() {
		return new OrderDeadline(
			mondayDeadline,
			tuesdayDeadline,
			wednesdayDeadline,
			thursdayDeadline,
			fridayDeadline,
			saturdayDeadline,
			sundayDeadline
		);
	}
}

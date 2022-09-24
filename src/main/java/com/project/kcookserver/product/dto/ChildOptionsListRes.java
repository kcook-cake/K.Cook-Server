package com.project.kcookserver.product.dto;

import com.project.kcookserver.product.entity.ChildOptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ChildOptionsListRes {

	private Long childOptionId;

	private String type;

	private String array;

	public ChildOptionsListRes(ChildOptions childOptions) {
		this.childOptionId = childOptions.getChildOptionsId();
		this.type = childOptions.getType();
		this.array = childOptions.getArray();
	}

}

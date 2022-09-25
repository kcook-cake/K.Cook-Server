package com.project.kcookserver.product.dto;

import java.util.ArrayList;
import java.util.List;

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

	private List<Integer> array = new ArrayList<>();

	public ChildOptionsListRes(ChildOptions childOptions) {
		this.childOptionId = childOptions.getChildOptionsId();
		this.type = childOptions.getType();
		String entityArray = childOptions.getArray();
		if(entityArray.length() >2 ) {
			String[] split = entityArray.substring(1,entityArray.length()-1).split(",");;
			for (String s : split) {
				array.add(Integer.parseInt(s.trim()));
			}
		}
	}

}

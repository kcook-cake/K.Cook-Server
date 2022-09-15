package com.project.kcookserver.store.dto;

import lombok.Data;

@Data
public class Coordinate {

	private double xCoordinate;
	private double yCoordinate;
	public Coordinate(String xCoordinate, String yCoordinate) {
		this.xCoordinate = Double.parseDouble(xCoordinate);
		this.yCoordinate = Double.parseDouble(yCoordinate);
	}

}

package com.project.kcookserver.product.entity;

import static com.project.kcookserver.configure.entity.Status.*;
import static javax.persistence.FetchType.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.dto.CreateChildOptionReq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChildOptions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long childOptionsId;

	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "optionsId")
	private Options options;

	private String type;

	private String array;

	public void setOptions(Options options) {
		this.options = options;
	}

	public ChildOptions(CreateChildOptionReq req) {
		this.status = VALID;
		this.type = req.getType();
		this.array = req.getArray().toString();
	}
}

package com.project.kcookserver.account.sms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {

	@Email
	@NotBlank
	private String email;
}

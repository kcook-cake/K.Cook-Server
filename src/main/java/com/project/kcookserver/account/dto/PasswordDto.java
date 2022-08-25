package com.project.kcookserver.account.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;

@Getter
public class PasswordDto {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Length(min=8, max= 50)
	private String password;

	@Getter
	public static class OnlyPasswordDto{
		@NotBlank
		@Length(min=8, max= 50)
		private String password;
	}
}

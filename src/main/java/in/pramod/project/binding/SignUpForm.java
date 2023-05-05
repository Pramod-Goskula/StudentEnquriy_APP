package in.pramod.project.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignUpForm {
	
	@NotEmpty(message = "* Name is mandatory")
	private String userName;
	@Email
	@NotEmpty(message = "* Email is mandatory")
	private String email;
	@NotNull(message = "PhoneNumber is required")
	private Long phoneNumber;
}

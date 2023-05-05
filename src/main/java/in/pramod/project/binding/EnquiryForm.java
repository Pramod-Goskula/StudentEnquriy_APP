package in.pramod.project.binding;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
public class EnquiryForm {
	@Id
	private Integer enquiryId;
	@NotEmpty(message = "Name is mandatory")
	private String studentName;
	@NotNull(message = "Phone Number is mandatory")
	private Long phoneNumber;
	@NotEmpty(message = "Classmode is mandatory ")
	private String classMode;
	@NotEmpty(message = "CourseName is mandatory")
	private String courseName;
	@NotEmpty(message = "EnquiryStatus is mandatory")
	private String enquiryStatus;
	
}

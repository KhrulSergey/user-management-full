package com.khsa.usermanagement.repository.validation;


import com.khsa.usermanagement.domain.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator {

	public boolean supports(Class aClass) {
		return aClass.equals(User.class);
	}

	 private Pattern pattern;
	 private Matcher matcher;

	 private static final
	 String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	 String STRING_PATTERN = "[a-zA-Z]+";
	 Integer MAX_AGE_VALUE = 120;

	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.invalid.user", "User Name Required");
		if (!(user.getName() != null && user.getName().isEmpty())) {
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(user.getName());
			if (!matcher.matches()) {
				errors.rejectValue("name", "name.containNonChar",
						"Enter a valid name");
			}
		}
		//TODO Check password Strength
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
		if (!(user.getPassword() != null && user.getPassword().isEmpty())) {
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(user.getPassword());
			if (!matcher.matches()) {
				errors.rejectValue("password", "password.containNonChar",
						"Enter a valid password of strings");
			}
		}
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.user", "First Name Required");
//		 if (!(user.getFirstName() != null && user.getFirstName().isEmpty())) {
//			   pattern = Pattern.compile(STRING_PATTERN);
//			   matcher = pattern.matcher(user.getFirstName());
//			   if (!matcher.matches()) {
//			    errors.rejectValue("firstName", "firstName.containNonChar",
//			      "Enter a valid first name");
//			   }
//			  }
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.user", "Last Name Required");
//		if (!(user.getLastName() != null && user.getLastName().isEmpty())) {
//			   pattern = Pattern.compile(STRING_PATTERN);
//			   matcher = pattern.matcher(user.getLastName());
//			   if (!matcher.matches()) {
//			    errors.rejectValue("lastName", "lastName.containNonChar",
//			      "Enter a valid last name");
//			   }
//			  }
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "error.invalid.user", "Birth Date Required");
//		if(user.getBirthDate().getYear() > MAX_AGE_VALUE ) {
//			errors.rejectValue("birthday", "old.birthday");
//		}
//		if(user.getBirthDate().isAfter(LocalDate.now())) {
//			errors.rejectValue("birthday", "underage.birthday");
//		}
	}
		//TODO check gender, about
		// check if user exists
}


package com.bridgelabz.usermanagement.customquery;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class ContactNumberValidator implements
ConstraintValidator<ContactNumberConstraint, String>{

	@Override
	public boolean isValid(String contactField,
		      ConstraintValidatorContext cxt) {
		System.out.println(1);
		        return contactField != null && contactField.matches("[0-9]+")
		          && (contactField.length() > 8) && (contactField.length() < 14);
		    }

}

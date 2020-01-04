package com.bridgelabz.usermanagement.customquery;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Constraint(validatedBy = ContactNumberValidator.class)
@Target( { ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactNumberConstraint {
	 String message() default "Invalid phone number";
	    Class<?>[] groups() default {};				//boilerplate code to conforms to the Spring standards.
	    Class<? extends Payload>[] payload() default {};
}

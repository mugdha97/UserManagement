package com.bridgelabz.usermanagement.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomException {

	// public Forgotpasswordexception(String message)
//	{
//		super(message);
//	}
	static Logger logger = LoggerFactory.getLogger(CustomException.class);

	public static class UserNotFound extends RuntimeException {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		public UserNotFound(String msg) {
			super(msg);
			logger.warn(msg);

		}
	}

	public static class UserEmailAlreadyExist extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UserEmailAlreadyExist(String msg) {
			super(msg);
			// logger.warn(msg);
		}
	}

	public static class UserNotVerified extends RuntimeException {
		public UserNotVerified(String msg) {
			super(msg);
		}

	}
	public static class Password extends RuntimeException {
		public Password(String msg) {
			super(msg);
		}

	}

}

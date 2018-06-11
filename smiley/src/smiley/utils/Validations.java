package smiley.utils;

import java.util.regex.Pattern;

public class Validations {
	
	private static final Pattern LOCAL_MOBILE_NUMBER_PATTERN = Pattern
			.compile("((\\+258)|(258))?8(2|4|6|7)\\d{7}");
	
	private static final Pattern ALL_EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	public static Pattern getLocalMobileNumberPattern() {
		return LOCAL_MOBILE_NUMBER_PATTERN;
	}

	public static boolean isValidForSMSNotification(String phone) {
		String phoneNumber = prepareNumberForValidation(phone);
		return LOCAL_MOBILE_NUMBER_PATTERN.matcher(phoneNumber).matches();
	}

	private static String prepareNumberForValidation(String phone) {
		return phone.replace(" ", "");
	}
	
	public static boolean isValidForEmailNotification(String email) {
		String emailValidated = prepareNumberForValidation(email);
		return ALL_EMAIL_PATTERN.matcher(emailValidated).matches();
	}
}

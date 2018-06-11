package smiley.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class FormatUtils {
	
	public static LocalDate formatDateToBox(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		Instant instant = calendar.toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		LocalDate date = zdt.toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date.toString(), formatter);
		return localDate;
		
	}

}

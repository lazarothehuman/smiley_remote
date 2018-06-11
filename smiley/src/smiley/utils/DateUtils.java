package smiley.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date getRightDate(LocalDate localDate, Date date) {
		if (date != null) {
			// converter para calendar, fazer os sets depois meter no calendar e enviar uma
			// instancia de tempo
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			date = calendar.getTime();
			return date;
		}

		if (localDate != null) {
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			date = Date.from(instant);
			return date;
		}
		
		return null;
	}

}

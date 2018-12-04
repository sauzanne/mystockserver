package fr.mystocks.mystockserver.technic.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.GregorianCalendar;

public final class DateTools {

	private DateTools() {
		super();
	}

	public static LocalDate getPreviousOpenDate(LocalDate date) {
		/*
		 * si la date tombe un dimanche on enlève 2 jour pour récupérer le premier
		 * vendredi qui précède
		 */
		if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return date.minusDays(2);
		} /*
			 * si la date tombe un samedi on enlève 1 jour pour récupérer le premier
			 * vendredi qui précède
			 */
		else if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
			return date.minusDays(1);
		} else if (date.getDayOfWeek() == DayOfWeek.MONDAY) {/*
																 * si la date tombe un lundi on enlève 3 jour pour
																 * récupérer le premier vendredi qui précède
																 */
			return date.minusDays(3);

		}
		/* dans tous les autres cas on enlève un jour */
		return date.minusDays(1);
	}

	/**
	 * Permet d'obtenir le premier jour ouvré qui précède
	 * 
	 * @param date
	 *            la date à partir duquel on recherche
	 * @return le jour ouvré le plus proche
	 */
	public static LocalDate getLastOpenDate(LocalDate date) {
		/*
		 * si la date tombe un dimanche on enlève 2 jour pour récupérer le premier
		 * vendredi qui précède
		 */
		if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return date.minusDays(2);
		} /*
			 * si la date tombe un samedi on enlève 1 jour pour récupérer le premier
			 * vendredi qui précède
			 */
		else if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
			return date.minusDays(1);
		}
		return date;
	}

	/**
	 * Permet d'obtenir le premier jour ouvré qui suit
	 * 
	 * @param date
	 *            la date à partir duquel on recherche
	 * @return le jour ouvré le plus proche
	 */
	public static LocalDate getNextOpenDate(LocalDate date) {
		/*
		 * si la date tombe un dimanche on ajoute 1 jour pour récupérer le premier lundi
		 * qui suit
		 */
		if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return date.plusDays(1);
		} /*
			 * si la date tombe un samedi on ajoute 2 jour pour récupérer le premier lundi
			 * qui suit
			 */
		else if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
			return date.plusDays(2);
		}
		return date;
	}

	/**
	 * Convert a String in ISO Local Date in a LocalDate
	 * 
	 * @param isoLocalDate
	 *            a String with ISO Local Date format
	 * @return LocalDate or null if conversion fails
	 */
	public static LocalDate convertIsoLocalDate(String isoLocalDate) {
		try {
			return LocalDate.parse(isoLocalDate, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (DateTimeParseException e) {
			return null;
		}
	}
	
	/**
	 * Convertir une gregorianCalendar en localDate
	 * 
	 * @param dateToConvert
	 *            la date dans l'ancien format
	 * @return la date dans le nouveau format
	 */
	public static LocalDate convertGregorianCalendarToLocalDate(GregorianCalendar dateToConvert) {
		if (dateToConvert != null) {
			return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return null;
	}


}

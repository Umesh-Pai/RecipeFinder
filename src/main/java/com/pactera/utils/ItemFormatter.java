package com.pactera.utils;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.pactera.domain.FridgeItem;

public final class ItemFormatter {

	/**
     * This method takes a string array, creates a fridge item and returns it
     *
     * @param  columns  string array containing the list of fridge items to be parsed
     * @return   newly created fridge item
     */
	public static FridgeItem parseFridgeItem(String[] columns) {

		if (columns.length != 4)
			throw new IllegalArgumentException(
					" Expected 4 fields for item, instead of: "
							+ columns.length);

		return new FridgeItem(getItem(columns[0]), getAmount((columns[1])),
				getUnit((columns[2])), getDate((columns[3])));
	}

	/**
     * This method takes an item name, validates it, trims it and returns
     *
     * @param  item  the name of the item to be validated
     * @return   item after validation
     */
	private static String getItem(String item) {
		if (item == null || item.trim().length() == 0)
			throw new IllegalArgumentException(" Expected item name ");
		return item.trim();
	}

	/**
     * This method takes an amount, validates it, parsers it and returns
     *
     * @param  amount  the amount of the item to be validated
     * @return   amount after validation
     */
	private static int getAmount(String amount) {

		int amt;
		if (amount == null || amount.trim().length() == 0)
			throw new IllegalArgumentException(" Expected amount for item ");

		try {
			amt = Integer.parseInt(amount.trim());
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
		return amt;
	}

	/**
     * This method takes a string, validates it, trims it and returns
     *
     * @param  unit  the unit of the item to be validated
     * @return   unit after validation
     */
	private static UnitEnum getUnit(String unit) {

		if (unit == null || unit.trim().length() == 0)
			throw new IllegalArgumentException(" Expected no of units for item ");
		
		return UnitEnum.valueOf(unit.trim().toUpperCase());
	}

	/**
     * This method takes a string, validates it, formats it and returns a date object
     *
     * @param  date  the date of the item to be validated and formatted
     * @return   date after formatting
     */
	private static LocalDate getDate(String date) {

		if (date == null || date.trim().length() == 0)
			throw new IllegalArgumentException(" Expected date for item ");

		DateTimeFormatter formatter = DateTimeFormat.forPattern("d/M/yyyy");
		
		return formatter.parseLocalDate(date.trim());
	}

}

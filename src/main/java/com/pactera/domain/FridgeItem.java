package com.pactera.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pactera.utils.UnitEnum;
import org.joda.time.LocalDate;
import java.util.Date;

public class FridgeItem extends Item {

	private Date useBy;

	public FridgeItem(String item, int amount, UnitEnum unit, LocalDate useBy) {
		setItem(item);
		setAmount(amount);
		setUnit(unit);
		setUseBy(useBy.toDate());
	}
	
	/**
	 * getter method for UseBy date
	 *
	 * @param  
	 * @return  useBy  the UseBy date
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy", locale = "en_AU", timezone = "Australia/Sydney")
	public Date getUseBy() {
		return useBy;
	}

	/**
	 * setter method for UseBy date
	 *
	 * @param  useBy  the UseBy date
	 * @return 
	 */
	public void setUseBy(Date useBy) {
		this.useBy = useBy;
	}

	/**
	 * toString method
	 *
	 */
	@Override
	public String toString() {
		return "FridgeItem {" + super.toString() + ", useBy=" + useBy + "}";
	}
}


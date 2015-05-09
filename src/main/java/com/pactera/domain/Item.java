package com.pactera.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.pactera.utils.UnitEnum;

@XmlRootElement
public class Item {

	private String item;
	private int amount;
	private UnitEnum unit;

	public Item() {
	}

	public Item(String item, int amount, UnitEnum unit) {
		setItem(item);
		setAmount(amount);
		setUnit(unit);
	}

	/**
	 * getter method for item
	 *
	 * @param  
	 * @return  item  the fridge item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * setter method for item
	 *
	 * @param  item  the fridge item
	 * @return 
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * getter method for Amount
	 *
	 * @param  
	 * @return  amount  the item amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * setter method for amount
	 *
	 * @param  amount  the item amount
	 * @return 
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * getter method for Unit
	 *
	 * @param  
	 * @return   unit   the unit of the item
	 */
	public UnitEnum getUnit() {
		return unit;
	}

	/**
	 * setter method for unit
	 *
	 * @param  unit  the unit of the item
	 * @return 
	 */
	public void setUnit(UnitEnum unit) {
		this.unit = unit;
	}

	/**
	 * toString method
	 *
	 */
	@Override
	public String toString() {
		return "Item{" + "item='" + item + '\'' + ", amount=" + amount
				+ ", unit=" + unit + "}";
	}
}

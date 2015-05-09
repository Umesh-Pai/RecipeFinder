package com.pactera.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UnitEnum {
	OF, GRAMS, ML, SLICES;

	@JsonValue
	public String toJson() {
		return name().toLowerCase();
	}

	@JsonCreator
	public static UnitEnum fromJson(String text) {
		return valueOf(text.toUpperCase());
	}
}
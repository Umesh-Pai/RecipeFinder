package com.pactera.utils;

import org.junit.Test;

public class ParseItemWithNullArgumentTest {

	@Test(expected = NullPointerException.class)
    public void parseFridgeItem_ShouldThrowException_WhenItemArgumentIsNull() {
		ItemFormatter.parseFridgeItem(null);
    }   
}

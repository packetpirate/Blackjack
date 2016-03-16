package com.blackjack.model;

public enum CardValue {
	ACE(0, 11), 
	TWO(1, 2), 
	THREE(2, 3), 
	FOUR(3, 4), 
	FIVE(4, 5), 
	SIX(5, 6), 
	SEVEN(6, 7), 
	EIGHT(7, 8), 
	NINE(8, 9), 
	TEN(9, 10), 
	JACK(10, 10), 
	QUEEN(11, 10), 
	KING(12, 10);
	
	private int index;
	public int getIndex() { return this.index; }
	private int value;
	public int getCardValue() { return this.value; }
	
	private CardValue(int i, int v) {
		this.index = i;
		this.value = v;
	}
}

package com.blackjack.model;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class Card {
	public static BufferedImage CARD_SHEET;
	public static BufferedImage BACK_IMAGE;
	public static final int CARD_WIDTH = 79;
	public static final int CARD_HEIGHT = 123;
	
	private CardSuit suit;
	public CardSuit getSuit() { return this.suit; }
	private CardValue value;
	public CardValue getValue() { return this.value; }
	
	private BufferedImage image;
	public BufferedImage getImage() { return this.image; }
	
	public Card(CardSuit _suit_, CardValue _value_) {
		this.suit = _suit_;
		this.value = _value_;
		
		// Determine the image to be used for this card based on the suit and value.
		if(Card.CARD_SHEET != null) {
			try {
				int x = value.getIndex() * Card.CARD_WIDTH;
				int y = suit.getIndex() * Card.CARD_HEIGHT;
				image = Card.CARD_SHEET.getSubimage(x, y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
			} catch(RasterFormatException rfe) {
				System.err.println("ERROR: Problem loading subimage of card sprite sheet.");
				System.exit(1);
			}
		} else {
			System.err.println("ERROR: Card sprite sheet null. Could not create subimage.");
			System.exit(1);
		}
	}
}

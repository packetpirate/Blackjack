package com.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> cards;
	public ArrayList<Card> getCards() { return this.cards; }
	
	public Deck() {
		// Initialize a deck of 52 cards.
		cards = new ArrayList<>();
		shuffle();
	}
	
	private void populateDeck() {
		cards.clear();
		for(CardSuit s : CardSuit.values()) {
			for(CardValue v : CardValue.values()) {
				cards.add(new Card(s, v));
			}
		}
	}
	
	public void shuffle() {
		// Simple enough. No need to get fancy.
		populateDeck();
		Collections.shuffle(cards);
	}
	
	public Card drawCard() {
		// Simply remove the first element in the cards list and return it.
		// This means the deck must be repopulated when shuffled.
		return cards.remove(0);
	}
}

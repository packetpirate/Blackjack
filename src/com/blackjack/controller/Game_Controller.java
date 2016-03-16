/*****************************************************************
 * This class acts as the Controller in my MVC design. It exhibits
 * two-way communication with the View and Model, acting like a
 * "messenger boy" between the two.
 *****************************************************************/
package com.blackjack.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import com.blackjack.model.GameState;
import com.blackjack.model.Game_Framework;
import com.blackjack.view.Game_Canvas;

public class Game_Controller {
	private Game_Canvas canvas;
	private Game_Framework framework;
	
	public Game_Controller(Game_Canvas _canvas_, Game_Framework _framework_) {
		this.canvas = _canvas_;
		this.canvas.setFocusable(true);
		this.framework = _framework_;
		
		this.canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				Point2D mousePos = new Point2D.Double(m.getX(), m.getY());
				if(canvas.getHitButton().inBounds(mousePos) && canvas.getHitButton().isActive()) {
					framework.hit(framework.getPlayerHand());
					update();
				} else if(canvas.getStayButton().inBounds(mousePos) && canvas.getStayButton().isActive()) {
					framework.stay();
					update();
				} else if(canvas.getNextButton().inBounds(mousePos) && canvas.getNextButton().isActive()) {
					framework.nextGame();
					canvas.getHitButton().setActive(true);
					canvas.getStayButton().setActive(true);
					canvas.getNextButton().setActive(false);
					update();
				}
			}
		});
	}
	
	public void update() {
		if((framework.getState() == GameState.DEALER_TURN) || 
		   (framework.getState() == GameState.PLAYER_WIN) || 
		   (framework.getState() == GameState.DEALER_WIN) || 
		   (framework.getState() == GameState.PUSH)) {
			canvas.getHitButton().setActive(false);
			canvas.getStayButton().setActive(false);
			canvas.getNextButton().setActive(true);
		}
		
		canvas.update(framework.getState(), framework.getPlayerHand(), framework.getDealerHand());
	}
}

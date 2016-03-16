package com.blackjack.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Button {
	private boolean active;
	public boolean isActive() { return this.active; }
	public void setActive(boolean _active_) {
		this.active = _active_;
	}
	
	private String text;
	public String getText() { return this.text; }
	
	private Rectangle2D bounds;
	public boolean inBounds(Point2D p) { return bounds.contains(p); }
	
	public Button(String _text_, int x, int y, int w, int h, boolean _active_) {
		this.text = _text_;
		this.active = _active_;
		this.bounds = new Rectangle2D.Double(x, y, w, h);
	}
	
	public void draw(Graphics2D g2d) {
		if(this.active) g2d.setColor(Color.RED);
		else g2d.setColor(Color.GRAY);
		g2d.fill(this.bounds);
		
		g2d.setColor(Color.BLACK);
		g2d.draw(this.bounds);
		
		Font font = new Font("Courier New", Font.BOLD, 16);
		FontMetrics metrics = g2d.getFontMetrics(font);
		int x = ((int)bounds.getX() + (int)(bounds.getWidth() / 2)) - (metrics.stringWidth(this.text) / 2);
		int y = (int)bounds.getY() + (int)(bounds.getHeight() / 2) + (metrics.getHeight() / 2) - 1;
		g2d.setColor(Color.WHITE);
		g2d.setFont(font);
		g2d.drawString(this.text, x, y);
	}
}

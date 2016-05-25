package com.gravisim3d.ui;

import com.gravisim3d.event.IClickListener;
import com.gravisim3d.event.IHoverListener;

/**
 * Menu item interface
 * 
 * @author Ethem Kurt
 *
 */
public interface IMenuItem {

	/**
	 * Get title
	 * 
	 * @return Title
	 */
	public String getTitle();

	/**
	 * Get description
	 * 
	 * @return description
	 */
	public String getDescription();

	/**
	 * Set click listener
	 * 
	 * @param click_listener
	 *            Click listener
	 */
	public void setClickListener(IClickListener click_listener);

	/**
	 * Get click listener
	 * 
	 * @return CLick listener
	 */
	public IClickListener getClickListener();

	/**
	 * Set hover listener
	 * 
	 * @param hover_listener
	 *            Hover listener
	 */
	public void setHoverListener(IHoverListener hover_listener);

	/**
	 * Get hover listener
	 * 
	 * @return Hover listener
	 */
	public IHoverListener getHoverListener();
}

package com.gravisim3d.event;

/**
 * Hover listener interface
 * 
 * @author Ethem Kurt
 *
 */
public interface IHoverListener extends IListener {

	/**
	 * Hover event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void onHover(HoverEventArgs args);

	/**
	 * Enter event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void onEnter(HoverEventArgs args);

	/**
	 * Leave event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void onLeave(HoverEventArgs args);
}
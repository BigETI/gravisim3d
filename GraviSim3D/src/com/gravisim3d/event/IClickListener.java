package com.gravisim3d.event;

/**
 * Click listener interface
 * 
 * @author Ethem Kurt
 *
 */
public interface IClickListener extends IListener {

	/**
	 * Click event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void onClick(ClickEventArgs args);

	/**
	 * Press Event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void onPress(ClickEventArgs args);

	/**
	 * Release event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void onRelease(ClickEventArgs args);
}
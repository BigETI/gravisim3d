package com.gravisim3d.event;

/**
 * Click notifier class
 * 
 * @author Ethem Kurt
 *
 */
public class ClickNotifier extends Notifier<IClickListener> {

	/**
	 * Notify "onClick" event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void setOnClick(ClickEventArgs args) {
		for (IClickListener i : getListeners())
			i.onClick(args);
	}

	/**
	 * Notify "onPress" event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void setOnPress(ClickEventArgs args) {
		for (IClickListener i : getListeners())
			i.onPress(args);
	}

	/**
	 * Notify "onRelease" event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void setOnRelease(ClickEventArgs args) {
		for (IClickListener i : getListeners())
			i.onRelease(args);
	}
}
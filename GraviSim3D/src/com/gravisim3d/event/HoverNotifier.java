package com.gravisim3d.event;

/**
 * Hover notifier class
 * 
 * @author Ethem Kurt
 *
 */
public class HoverNotifier extends Notifier<IHoverListener> {

	/**
	 * Notify "onHover" event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void setOnHover(HoverEventArgs args) {
		for (IHoverListener i : getListeners())
			i.onHover(args);
	}

	/**
	 * Notify "onEnter" event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void setOnEnter(HoverEventArgs args) {
		for (IHoverListener i : getListeners())
			i.onEnter(args);
	}

	/**
	 * Notify "onLeave" event
	 * 
	 * @param args
	 *            Arguments
	 */
	public void setOnLeave(HoverEventArgs args) {
		for (IHoverListener i : getListeners())
			i.onLeave(args);
	}
}
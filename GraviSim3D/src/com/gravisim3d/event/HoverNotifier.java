package com.gravisim3d.event;

public class HoverNotifier extends Notifier<IHoverListener> {

	public void setOnHover(HoverEventArgs args) {
		for (IHoverListener i : getListeners())
			i.onHover(args);
	}

	public void setOnEnter(HoverEventArgs args) {
		for (IHoverListener i : getListeners())
			i.onEnter(args);
	}

	public void setOnLeave(HoverEventArgs args) {
		for (IHoverListener i : getListeners())
			i.onLeave(args);
	}
}
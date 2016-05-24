package com.gravisim3d.event;

public class ClickNotifier extends Notifier<IClickListener> {

	public void setOnClick(ClickEventArgs args) {
		for (IClickListener i : getListeners())
			i.onClick(args);
	}

	public void setOnPress(ClickEventArgs args) {
		for (IClickListener i : getListeners())
			i.onPress(args);
	}

	public void setOnRelease(ClickEventArgs args) {
		for (IClickListener i : getListeners())
			i.onRelease(args);
	}
}
package com.gravisim3d.event;

public interface IHoverListener extends IListener {
	public void onHover(HoverEventArgs args);

	public void onEnter(HoverEventArgs args);

	public void onLeave(HoverEventArgs args);
}
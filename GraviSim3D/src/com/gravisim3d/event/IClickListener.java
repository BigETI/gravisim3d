package com.gravisim3d.event;

public interface IClickListener extends IListener {
	public void onClick(ClickEventArgs args);

	public void onPress(ClickEventArgs args);

	public void onRelease(ClickEventArgs args);
}
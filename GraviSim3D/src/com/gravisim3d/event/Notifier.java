package com.gravisim3d.event;

import java.util.ArrayList;
import java.util.List;

public abstract class Notifier<T extends IListener> {
	private ArrayList<T> listeners = new ArrayList<T>();

	public void addListener(T listener) {
		listeners.add(listener);
	}

	public void removeAllListeners() {
		listeners.clear();
	}

	protected List<T> getListeners() {
		return listeners;
	}
}
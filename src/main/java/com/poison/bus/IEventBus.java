package com.poison.bus;

public interface IEventBus{
	void registerListener(IEventListener<?> listener);
	void publishEvent(IEvent event);
	void dispatchEvents();
}

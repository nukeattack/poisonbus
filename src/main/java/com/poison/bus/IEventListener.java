package com.poison.bus;

/**
 *
 */
public interface IEventListener<T extends IEvent> {
    void handleEvent(T event);
    String getEventType();
}

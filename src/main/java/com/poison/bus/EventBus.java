package com.poison.bus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class EventBus implements IEventBus{
	
	private Map<String, IEventListener[]> listenersMap = new HashMap<String, IEventListener[]>();
    private Map<String, IEvent[]> eventsMap = new HashMap<String, IEvent[]>();
    private int initialListenersSize;
    private int initialEventArraySize;

    public EventBus(int initialListenersSize, int initialEventArraySize){
    	this.initialEventArraySize = initialEventArraySize;
    	this.initialListenersSize = initialListenersSize;
    }

	public void registerListener(IEventListener<?> listener) {
		String key = listener.getEventType();
		if(!listenersMap.containsKey(key)){
			listenersMap.put(key, new IEventListener[initialListenersSize]);
		}
		IEventListener[] listeners = listenersMap.get(key);
		for(int i = 0; i < listeners.length; i++){
			if(listeners[i] == null){
				listeners[i] = listener;
				return;
			}
		}
		listenersMap.put(key, Arrays.copyOf(listeners, listeners.length * 2));
		registerListener(listener);
	}

	public void publishEvent(IEvent event) {
		String key = event.getEventTypeName();
		if(!eventsMap.containsKey(key)){
			eventsMap.put(key, new IEvent[initialEventArraySize]);
		}
		IEvent[] events = eventsMap.get(key);
		for(int i = 0; i < events.length; i++){
			if(events[i] == null){
				events[i] = event;
				return;
			}
		}
		eventsMap.put(key, Arrays.copyOf(events, events.length * 2));
		publishEvent(event);
	}

	public void dispatchEvents() {
		Iterator<String> eventTypeIterator = eventsMap.keySet().iterator();
		while(eventTypeIterator.hasNext()){
			String eventType = eventTypeIterator.next();
			IEvent[] events = eventsMap.get(eventType);
			IEventListener [] listeners = listenersMap.get(eventType);
			if(listeners != null){
				for(int i = 0; i < events.length; i++){
					if(events[i] != null){
						for(int j = 0; j < listeners.length; j++){
							if(listeners[j] != null){
								listeners[j].handleEvent(events[i]);
							}
						}
					}
				}
			}
		}
		
	}
}

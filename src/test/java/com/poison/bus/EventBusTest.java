package com.poison.bus;

import junit.framework.TestCase;
/**
 *
 */
public class EventBusTest extends TestCase {
    class MyEvent implements IEvent{
    	public static final String NAME = "MyEvent";
		public String getEventTypeName() {
			return NAME;
		}
    }    
    class OtherEvent implements IEvent{
    	public static final String NAME = "OtherEvent";
		public String getEventTypeName() {
			return "OtherEvent";
		}
    }
    
    class OtherEventHandler implements IEventListener<OtherEvent>{
    	int handledEvents = 0;
		public void handleEvent(OtherEvent event) {
			handledEvents++;
		}

		public String getEventType() {
			return OtherEvent.NAME;
		}
    }
    
    class MyEventHandler implements IEventListener<MyEvent>{
    	int handledEvents = 0;
		public void handleEvent(MyEvent event) {
			handledEvents++;
		}

		public String getEventType() {
			return MyEvent.NAME;
		}
    }    

    
    public void testRegisterListener(){
    	EventBus bus = new EventBus(10, 10);
    	MyEventHandler myEventHandle = new MyEventHandler();
    	OtherEventHandler otherEventHandler = new OtherEventHandler();
    	
    	bus.registerListener(myEventHandle);
    	bus.registerListener(otherEventHandler);
    	bus.publishEvent(new MyEvent());
    	bus.publishEvent(new OtherEvent());
    	for(int i = 0 ; i < 1000000; i++){
    		bus.publishEvent(new OtherEvent());
    	}
    	assertTrue(bus.listenersMap.containsKey(MyEvent.NAME));
    	assertTrue(bus.listenersMap.containsKey(OtherEvent.NAME));
    	int myEventListenersCount = 0;
    	for(int i = 0; i < bus.listenersMap.get(MyEvent.NAME).length; i++){
    		if(bus.listenersMap.get(MyEvent.NAME)[i] != null){
        		myEventListenersCount ++;
    		}
    	}
    	assertEquals(myEventListenersCount, 1);
    	int otherEventListenersCount = 0;
    	for(int i = 0; i < bus.listenersMap.get(OtherEvent.NAME).length; i++){
    		if(bus.listenersMap.get(OtherEvent.NAME)[i] != null){
        		otherEventListenersCount ++;
    		}
    	}
    	
    	assertEquals(otherEventListenersCount, 1);
    	bus.dispatchEvents();
    	assertEquals(1001, otherEventHandler.handledEvents);
    	assertEquals(1, myEventHandle.handledEvents);
    	

//    	assertNotNull(listener.getHandledEvents().get(0));
    }
}

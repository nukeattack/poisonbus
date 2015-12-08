package com.poison.bus;

import junit.framework.TestCase;
/**
 *
 */
public class EventBusTest extends TestCase {
    class MyEvent implements IEvent{

		public String getEventTypeName() {
			// TODO Auto-generated method stub
			return "MyEvent";
		}
    	
    }
    
    class OtherEvent implements IEvent{

		public String getEventTypeName() {
			// TODO Auto-generated method stub
			return "OtherEvent";
		}
    	
    }
    public void testRegisterListener(){
    	EventBus bus = new EventBus(10, 10);
    	bus.registerListener(new IEventListener<MyEvent>() {
    		public String getEventType() {
    			return "MyEvent";
    		}

			public void handleEvent(MyEvent event) {
				System.out.println("handled my event");
			}
		});
    	
    	bus.registerListener(new IEventListener<OtherEvent>() {

			public void handleEvent(OtherEvent event) {
				System.out.println("handled other event");
				
			}
			public String getEventType() {
				return "OtherEvent";
			}
    		
		});
    	bus.publishEvent(new MyEvent());
    	bus.publishEvent(new OtherEvent());
    	for(int i = 0 ; i < 1000; i++){
    		bus.publishEvent(new OtherEvent());
    	}
    	bus.dispatchEvents();
    	

//    	assertEquals(listener.getHandledEvents().size(), 1);
//    	assertNotNull(listener.getHandledEvents().get(0));
    }
}

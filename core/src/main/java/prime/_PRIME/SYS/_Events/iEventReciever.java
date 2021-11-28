package prime._PRIME.SYS._Events;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import com.badlogic.gdx.InputProcessor;

import prime._PRIME.SYS._Events.Prototype.Listener;
import prime._PRIME.SYS._Events.Prototype.Signal;




public interface iEventReciever extends  EventTarget, EventListener, Listener<Signal<Event>>{

	
	@Override
	 public default void handleEvent(Event evt) {
		
	}
	
	public default boolean handle(Signal<Event> event) {
		Log("$&: " + event.toString());
		return false;
	}
	
	

	public default boolean handle(Event evnt)
	{
		if(evnt instanceof aEvent)
		{
			return this.handle((aEvent)evnt);
		}
		
		if(evnt instanceof Signal)
		{
			return this.handle((Signal)evnt);
		}
		
		return false;
	}

	
	public default boolean handle(aEvent event) {
		
		

		return false;
	}

	

	
}

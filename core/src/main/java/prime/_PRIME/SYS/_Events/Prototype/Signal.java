package prime._PRIME.SYS._Events.Prototype;

import org.w3c.dom.events.EventTarget;

import com.badlogic.gdx.utils.SnapshotArray;


import prime._PRIME.SYS._Events.iEvent;
import prime._PRIME.SYS._Events.iEventReciever;


/**
 * A Signal is a basic event class that can dispatch an event to multiple listeners. It uses generics to allow any type of object
 * to be passed around on dispatch.
 * @author Stefan Bachmann
 */
public class Signal<T> implements iEvent{
	protected SnapshotArray<Listener<T>> listeners;

	public Signal () {
		listeners = new SnapshotArray<Listener<T>>();
	}

	/**
	 * Add a Listener to this Signal
	 * @param listener The Listener to be added
	 */
	public void add (Listener<T> listener) {
		listeners.add(listener);
	}

	/**
	 * Remove a listener from this Signal
	 * @param listener The Listener to remove
	 */
	public void remove (Listener<T> listener) {
		listeners.removeValue(listener, true);
	}

	/** Removes all listeners attached to this {@link Signal}. */
	public void removeAllListeners () {
		listeners.clear();
	}

	/**
	 * Dispatches an event to all Listeners registered to this Signal
	 * @param object The object to send off
	 */
	public void dispatch (T object) {
		final Object[] items = listeners.begin();
		for (int i = 0, n = listeners.size; i < n; i++) {
			Listener<T> listener = (Listener<T>)items[i];
			listener.receive(this, object);
		}
		listeners.end();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventTarget getCurrentTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getEventPhase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getCancelable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getTimeStamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stopPropagation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preventDefault() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public iEventReciever getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTarget(Object target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSender() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSender(Object sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getBubbles() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBubbles(boolean bubbles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStopped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCapture(boolean capture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCapture() {
		// TODO Auto-generated method stub
		return false;
	}

//////////////////////

}
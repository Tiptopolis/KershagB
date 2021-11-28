package prime._PRIME.SYS._Events;

import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;

import prime._PRIME.RAUM.iSpace;
import prime._PRIME.SYS._Events.Prototype.Signal;





public abstract class aEvent extends Signal<Event> implements iEvent{

	public iSpace stage;
	public Object target;
	public Object source;
	private boolean capture; // true means event occurred during the capture phase
	private boolean bubbles = true; // true means propagate to target's parents
	private boolean handled; // true means the event was handled (the stage will eat the input)
	private boolean stopped; // true means event propagation was stopped
	private boolean cancelled; // true means propagation was stopped and any action that this event would cause should not happen
	public Object content;


	
	/** Marks this event as handled. This does not affect event propagation inside scene2d, but causes the {@link Stage}
	 * {@link InputProcessor} methods to return true, which will eat the event so it is not passed on to the application under the
	 * stage. */
	public void handle () {
		handled = true;
	}

	/** Marks this event cancelled. This {@link #handle() handles} the event and {@link #stop() stops} the event propagation. It
	 * also cancels any default action that would have been taken by the code that fired the event. Eg, if the event is for a
	 * checkbox being checked, cancelling the event could uncheck the checkbox. */
	public void cancel () {
		cancelled = true;
		stopped = true;
		handled = true;
	}

	/** Marks this event has being stopped. This halts event propagation. Any other listeners on the {@link #getListenerActor()
	 * listener actor} are notified, but after that no other listeners are notified. */
	public void stop () {
		stopped = true;
	}

	public void reset () {
		stage = null;
		target = null;
		source = null;
		capture = false;
		bubbles = true;
		handled = false;
		stopped = false;
		cancelled = false;
	}

	/** Returns the actor that the event originated from. */
	@Override
	public iEventReciever getTarget () {
		return (iEventReciever) target;
	}

	@Override
	public void setTarget (Object targetActor) {
		this.target = targetActor;
	}

	/** Returns the actor that this listener is attached to. */
	@Override
	public Object getSender() {
		// TODO Auto-generated method stub
		return this.source;
	}

	@Override
	public void setSender(Object sender) {
		this.source = sender;
		
	}

	public boolean getBubbles () {
		return bubbles;
	}

	/** If true, after the event is fired on the target actor, it will also be fired on each of the parent actors, all the way to
	 * the root. */
	public void setBubbles (boolean bubbles) {
		this.bubbles = bubbles;
	}

	/** {@link #handle()} */
	public boolean isHandled () {
		return handled;
	}

	/** @see #stop() */
	public boolean isStopped () {
		return stopped;
	}

	/** @see #cancel() */
	public boolean isCancelled () {
		return cancelled;
	}

	public void setCapture (boolean capture) {
		this.capture = capture;
	}

	/** If true, the event was fired during the capture phase.
	 * @see Actor#fire(Event) */
	public boolean isCapture () {
		return capture;
	}

	public void setStage (iSpace stage) {
		this.stage = stage;
	}

	/** The stage for the actor the event was fired on. */
	public iSpace getStage () {
		return stage;
	}


	public void setContent(Object o)
	{
		this.content = o;
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName();
	}

	@Override
	public EventTarget getCurrentTarget() {
		
		return this.getTarget();
	}

	@Override
	public short getEventPhase() {
		
		return 0;
	}

	@Override
	public boolean getCancelable() {
		
		return true;
	}

	@Override
	public long getTimeStamp() {
		
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

}



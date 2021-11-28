package prime._PRIME.SYS._Events;

import org.w3c.dom.events.Event;

import com.badlogic.gdx.utils.Pool.Poolable;

public interface iEvent extends Poolable, Event{

	public void handle();

	public void cancel();

	public void stop();

	public void reset();

	public iEventReciever getTarget();

	public void setTarget(Object target);
	
	public Object getSender();
	
	public void setSender(Object sender);

	public boolean getBubbles();

	public void setBubbles(boolean bubbles);

	public boolean isHandled();

	public boolean isStopped();

	public void setCapture(boolean capture);

	public boolean isCapture();

}

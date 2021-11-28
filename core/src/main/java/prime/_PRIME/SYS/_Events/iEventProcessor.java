package prime._PRIME.SYS._Events;

import com.badlogic.gdx.InputProcessor;

import prime._PRIME.SYS._Events.Prototype.Message;

public interface iEventProcessor extends iEventListener, InputProcessor{

	public boolean handle(Message m);
	
}

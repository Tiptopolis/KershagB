package prime._PRIME.SYS._Events.Prototype;

import org.w3c.dom.events.EventListener;



//import com.badlogic.gdx.scenes.scene2d.EventListener;

//import java.util.EventListener;

/**
 * A simple Listener interface used to listen to a {@link Signal}.
 * 
 * @author Stefan Bachmann
 */
public interface Listener<T extends Object> {
	/**
	 * @param signal The Signal that triggered event
	 * @param object The object passed on dispatch
	 */
	
	public default void recieve(T object)
	{
		
	}
	
	public default void receive(Signal<T> signal, T object) {

	}

	public default void receive(Signal<T> signal, String message, T object) {

	}

	public default void recieve(Signal<T> signal, T... objects) {

	}

	public default void recieve(Signal<T> signal, String message, T... objects) {

	}
}
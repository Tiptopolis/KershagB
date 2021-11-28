package prime._PRIME.SYS;

import com.badlogic.gdx.Files;

import prime._PRIME.N_M.iStatique;
import prime._PRIME.SYS._Events.iEventProcessor;



public interface iSystem extends iStatique, iEventProcessor {
	//Stores mapped references to VirtualFiles, VirtualTypes, and ObjectiveValues (JSON_Value)
	// public static Node UNDEFINED = new Node(new Lookup(), "[?]", Object.class);


	
	public void update(float deltaTime);

	public void update(int deltaTime);

	public void terminate();
	
}

package prime._PRIME.SYS;

import com.badlogic.gdx.ApplicationListener;

import prime._PRIME.UI.aViewContext;


public interface iApplet extends ApplicationListener, iSystem{

	public boolean running();
	public aViewContext domain();
	
	
	
}

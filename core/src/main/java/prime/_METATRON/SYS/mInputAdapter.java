package prime._METATRON.SYS;

import com.badlogic.gdx.InputProcessor;

import prime._PRIME.SYS._Events.Prototype.Listener;
import prime._PRIME.SYS._Events.Prototype.Signal;



public class mInputAdapter extends Signal<Signal> implements InputProcessor{

	
	@Override
	public boolean keyDown(int keycode) {
		for(Listener L : this.listeners)
		{
			L.receive(this,"keyDown", keycode);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		for(Listener L : this.listeners)
		{
			L.receive(this,"keyUp", keycode);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		for(Listener L : this.listeners)
		{
			L.receive(this,"keyTyped", character);
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for(Listener L : this.listeners)
		{
			L.receive(this,"touchDown", new int[] {screenX,screenY,pointer,button});
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for(Listener L : this.listeners)
		{
			L.receive(this,"touchUp", new int[] {screenX,screenY,pointer,button});
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for(Listener L : this.listeners)
		{
			L.receive(this,"touchDragged", new int[] {screenX,screenY,pointer});
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		for(Listener L : this.listeners)
		{
			L.receive(this,"mouseMoved", new int[] {screenX,screenY});
		}
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		for(Listener L : this.listeners)
		{
			L.receive(this,"scrolled", new float[] {amountX,amountY});
		}
		return false;
	}

}

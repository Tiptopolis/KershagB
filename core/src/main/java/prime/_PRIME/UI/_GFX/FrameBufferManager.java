package prime._PRIME.UI._GFX;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;

public class FrameBufferManager {

	// Major Component of View-Handling
	// One to Many relation

	protected static FrameBufferManager instance = new FrameBufferManager(); // need to de-static this

	public ArrayList<iFrameBuffer> registeredBuffers; // live buffers

	public FrameBufferManager() {

	}

	public void registerBuffer(iFrameBuffer buffer) {
		if (this.registeredBuffers == null)
			this.registeredBuffers = new ArrayList<iFrameBuffer>();

		this.registeredBuffers.add(buffer);

	}

	public void update(float deltaTime) {
		if (this.registeredBuffers != null)
			for (iFrameBuffer f : this.registeredBuffers) {
				f.buffer(); // this updates buffer's info in memory, not to screen
			}
	}

	public void dispose() {
		for (iFrameBuffer f : this.registeredBuffers) {
			f.dispose();
		}
	}
}

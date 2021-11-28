package prime._METATRON.SYS;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.badlogic.gdx.InputProcessor;


public class ConsoleInputAdapter {

	

	protected BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	protected BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

	protected InputProcessor shell;

	public ConsoleInputAdapter(InputProcessor shell) {
		this.shell = shell;

	}

	public int read() throws IOException {

		return reader.read();
	}

	public String readLine() throws IOException {

		return reader.readLine();
		
	}

	public void write(String str) throws IOException {
		this.writer.write(str);
	}

	public void writeLine(String str) throws IOException {

		this.write(str);
	}

	
}

package prime._PRIME.SYS._Events.Prototype;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;

public class Message extends Signal<Event>{

	
	public String content = "";
	
	public Message(String content)
	{
		this.content = content;
	}
	
	@Override
	public String toString()
	{
		return this.content;
	}

	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Message)
		{
			return this.toString().equals(other.toString());
		}
		if(other instanceof CharSequence)
		{
			return this.toString().equals(other.toString());
		}
		
		return false;
	}
	
}

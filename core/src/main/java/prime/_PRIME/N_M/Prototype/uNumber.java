package prime._PRIME.N_M.Prototype;

import com.badlogic.gdx.utils.Array;

import prime._PRIME.N_M.iCypher;
import prime._PRIME.N_M.UTIL.N_Resolver;


public class uNumber extends aNumber {

	//static manipulation & creation
	
	public uNumber()
	{
		super();
	}
	
	public uNumber(Number n)
	{
		super(n);
	}
	
	public static String[] purge(String s)
	{
		return purge(iCypher.toStream(s));
	}
	
	public static String[] purge(String[]s)
	{
		Array<String> S = new Array<String>(true,0,String.class);
		for(int i = 0; i < s.length; i++)
		{		
			if(N_Resolver.stringInt(s[i])!=null);
			S.add(s[i]);
		}
		return S.toArray();
	}
	
	public static Number[] toStream(String[] s)
	{
		String[] S = purge(s);
		Number[] res = new Number[S.length];
		for(int i = 0; i < S.length; i++)
		{		
			res[i] = N_Resolver.stringInt(S[i]);
		}
		return res;
	}
	
	public static Number[] toStream(String s)
	{
		return toStream(iCypher.toStream(s));
	}
	
	public static Number fromStream(Number[] ns)
	{
		String s ="";
		for(Number n : ns)
		{
			s+=""+n.intValue();
		}
		return N_Resolver.stringInt(s);
	}
	
}

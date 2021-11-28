package prime._PRIME.N_M;

import prime._PRIME.N_M._N;

public interface iStatique {

	//no args register-er, can be used to cast a signal
	public default void init()
	{
		this.reg(this, "");
	}
	
	default void reg(iStatique self)
	{
		this.init();
	}
	
	default void reg(iStatique self, Object...objects) {
		
	}
	
	default void reg(_N self) {
	}
	
	default void reg(_N self, Object...objects) {
	}
	
	default iStatique a()
	{
		return null;
	}
	
	public default String TypeName()
	{
		return this.getClass().getSimpleName();
	}


	
}

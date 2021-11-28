package prime._PRIME.N_M.UTIL;

public interface betaOperator {

	//Operator takes T, returns T
	
	public <I extends Object, O extends Object> O add(I a, I b);

	public <I extends Object, O extends Object> O sub(I a, I b);

	public <I extends Object, O extends Object> O mul(I a, I b);

	public <I extends Object, O extends Object> O div(I a, I b);

	public <I extends Object, O extends Object> O pow(I a, I b);

	public <I extends Object, O extends Object> O root(I a, I b);

	public <I extends Object, O extends Object> O mod(I a, I b);

	public <I extends Object, O extends Object> O rem(I a, I b);

	public <I extends Object, O extends Object> O min(I a, I b);

	public <I extends Object, O extends Object> O max(I a, I b);

	public <I extends Object, O extends Object> O abs(I a);
	public <I extends Object, O extends Object> O neg(I a);
	public <I extends Object, O extends Object> O inv(I a);
	
	public <I extends Object, O extends Object> O sum(I... a);
	public <I extends Object, O extends Object> O avg(I... a); //mean
	public <I extends Object, O extends Object> O med(I... a); //median
	public <I extends Object, O extends Object> O mde(I... a); //mode
	
}

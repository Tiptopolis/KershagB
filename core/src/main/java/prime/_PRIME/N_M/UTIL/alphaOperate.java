package prime._PRIME.N_M.UTIL;

public interface alphaOperate {

	public <I extends Object, O extends Object> O add(I b);

	public <I extends Object, O extends Object> O sub(I b);

	public <I extends Object, O extends Object> O mul(I b);

	public <I extends Object, O extends Object> O div(I b);

	public <I extends Object, O extends Object> O pow(I b);

	public <I extends Object, O extends Object> O root(I b);

	public <I extends Object, O extends Object> O mod(I b);

	public <I extends Object, O extends Object> O rem(I b);

	public <I extends Object, O extends Object> O min(I b);

	public <I extends Object, O extends Object> O max(I b);

	public <I extends Object, O extends Object> O abs();

	public <I extends Object, O extends Object> O neg();

	public <I extends Object, O extends Object> O inv();

	public <I extends Object, O extends Object> O sum();

	public <I extends Object, O extends Object> O avg(); // mean

	public <I extends Object, O extends Object> O med(); // median

	public <I extends Object, O extends Object> O mde(); // mode
	//////////////////////////////////////////////////////////

	public default <I extends Object, O extends alphaOperate> alphaOperate add(I... b) {
		for (I i : b) {
			this.add(b);
		}
		return this;
	}

	public default <I extends Object, O extends alphaOperate> alphaOperate sub(I... b) {
		for (I i : b) {
			this.sub(b);
		}
		return this;
	}
	
	public default <I extends Object, O extends alphaOperate> alphaOperate mul(I... b) {
		for (I i : b) {
			this.mul(b);
		}
		return this;
	}
	
	public default <I extends Object, O extends alphaOperate> alphaOperate div(I... b) {
		for (I i : b) {
			this.div(b);
		}
		return this;
	}
	
	public default <I extends Object, O extends alphaOperate> alphaOperate min(I... b) {
		for (I i : b) {
			this.min(b);
		}
		return this;
	}
	
	public default <I extends Object, O extends alphaOperate> alphaOperate max(I... b) {
		for (I i : b) {
			this.max(b);
		}
		return this;
	}

}

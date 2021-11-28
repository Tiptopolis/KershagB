package prime._PRIME.N_M;

import static prime._PRIME.N_M.UTIL.N_Operator.*;
import static prime._PRIME.N_M.UTIL.N_Resolver.*;

import prime._PRIME.N_M._N.n_Number;
import prime._PRIME.N_M.UTIL.N_Operator;
import prime._PRIME.N_M.UTIL.N_Resolver;



public abstract class N_ extends n_Number implements iStatique {

	protected static N_Operator Operator;
	protected static N_Resolver Resolver;
	public Node<Number> Node;
	
	public N_() {
		super();
		this.Node = new Node("Number",this);
	}

	public N_(Number n) {
		super(n);
		this.Node = new Node("Number",this);
	}
	


	public Number add(Number b) {
		return Operator.add(this.Value, b);
	}

	public Number sub(Number b) {
		return Operator.sub(this.Value, b);
	}

	public Number mul(Number b) {
		return Operator.mul(this.Value, b);
	}

	public Number div(Number b) {
		return Operator.div(this.Value, b);

	}

	public Number pow(Number b) {
		return Operator.pow(this.Value, b);

	}

	public Number root(Number b) {
		return Operator.root(this.Value, b);
	}

	public Number mod(Number b) {
		return Operator.mod(this.Value, b);
	}

	public Number rem(Number b) {
		return Operator.rem(this.Value, b);
	}

	public Number min(Number b) {
		return Operator.min(this.Value, b);
	}

	public Number max(Number b) {
		return Operator.max(this.Value, b);
	}

	public Number abs() {
		return Operator.abs(this.Value);
	}

	////////////////////////
	// NOTE N_.a(1f/9) returns the proper calculated value
	public static N_ a(Number n) {

		return new N_(n) {
			

			@Override
			public Object Class() {
				// TODO Auto-generated method stub
				return null;
			}

		};
	}

	
	public static N_ a(String n) {
		return new N_(N_Resolver.resolve(n)) {

			@Override
			public Object Class() {
				// TODO Auto-generated method stub
				return null;
			}
			

		};
	}

}

package prime._PRIME.N_M.UTIL;

import java.util.ArrayList;
import java.util.HashMap;

import prime._PRIME.N_M._N.Name;
import prime._PRIME.N_M._N.n_Number;
import prime._PRIME.N_M.iStatique;

public interface iOperator extends iStatique {

	public void inline(boolean is);

	public boolean inline();
	
	public default Boolean E(Object...objects)
	{
		return false;
	}
	


	public static class Operator extends n_Number implements iOperator {

		public static ArrayList<Operator> All;

		public static Type _Operator = new Type();

		protected Operator() {
			this.init();
		}

		@Override
		public void init() {
			// create an empty Object[] of every enum Map to initialize them
		}
		
		

		@Override
		public <O> O get() {

			// return final value

			return null;
		}

		@Override
		public void reg(iStatique self, Object... objects) {
			// TODO Auto-generated method stub

		}

		@Override
		public void inline(boolean is) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean inline() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object Class() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static enum ArithmeticOperator implements iOperator {
		PLUS("+", "ADD"),
		MINUS("-", "SUB"),
		MULTIPLY("*","MUL"),
		DIVIDE("/","DIV");
		;

		public static HashMap<Name, ArithmeticOperator> ArithmeticOperators;

		private ArithmeticOperator(String... statique) {

		}

		@Override
		public void reg(iStatique self, Object... objects) {
			// TODO Auto-generated method stub

		}

		@Override
		public void inline(boolean is) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean inline() {
			// TODO Auto-generated method stub
			return false;
		}

	}

	public static enum BinaryOperator implements iOperator {
		AND("&", "AND"),
		AND2("&&", "AND"),
		OR("|","OR"),
		OR2("||","OR")
		;

		private BinaryOperator(String... statique) {

		}

		@Override
		public void reg(iStatique self, Object... objects) {
			// TODO Auto-generated method stub

		}

		@Override
		public void inline(boolean is) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean inline() {
			// TODO Auto-generated method stub
			return false;
		}

	}

	public static enum ContainerOperator implements iOperator {
		OBJECT("[","]","OBJECT","SQUARE")
		;

		private ContainerOperator(String... statique) {

		}

		@Override
		public void reg(iStatique self, Object... objects) {
			// TODO Auto-generated method stub

		}

		@Override
		public void inline(boolean is) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean inline() {
			// TODO Auto-generated method stub
			return false;
		}

	}

}

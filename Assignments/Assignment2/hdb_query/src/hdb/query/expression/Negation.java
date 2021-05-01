package hdb.query.expression;

import java.io.PrintStream;

/**
 * A {@code Negation} represents a negation operation.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Negation extends Node {

	/**
	 * The child of this {@code Negation}.
	 */
	protected Node child;

	/**
	 * Constructs a {@code Negation}.
	 * 
	 * @param child
	 *            the child of the {@code Negation}
	 */
	public Negation(Node child) {
		this.child = child;
	}

	/**
	 * Returns the result type of the expression represented by this {@code Negation} and its descendants.
	 * 
	 * @return the result type of the expression represented by this {@code Negation} and its descendants
	 * @throws UnboundVariableException
	 *             if the expression represented by this {@code Negation} and its descendants has a variable whose type
	 *             is not set
	 */
	@Override
	public Class<?> resultType() throws UnboundVariableException {
		return child.resultType();
	}

	/**
	 * Evaluates the expression represented by this {@code Negation} and its descendants.
	 * 
	 * @return the result of evaluating the expression represented by this {@code Negation} and its descendants
	 * @throws UnboundVariableException
	 *             if the expression represented by this {@code Negation} and its descendants has a variable whose value
	 *             is not set
	 */
	@Override
	public Object evaluate() throws UnboundVariableException {
		Object c = object2num(child.evaluate());
		if (c instanceof Integer)
			return -1 * ((Integer) c).intValue();
		else if (c instanceof Double)
			return -1 * ((Double) c).doubleValue();
		else
			throw new UnsupportedOperationException();
	}

	/**
	 * Prints the sub-tree rooted at this {@code Negation}.
	 * 
	 * @param out
	 *            a {@code PrintStream}
	 * @param indentation
	 *            the indentation
	 */
	@Override
	protected void print(PrintStream out, int indentation) {
		super.print(out, indentation);
		child.print(out, indentation + 1);
	}

}

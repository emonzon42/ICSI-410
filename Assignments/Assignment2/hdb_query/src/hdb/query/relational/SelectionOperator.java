package hdb.query.relational;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.Tuple;
import hdb.query.expression.LogicalExpression;
import hdb.query.expression.ParsingException;
import hdb.query.expression.UnboundVariableException;

/**
 * A {@code SelectionOperator} outputs, among the input {@code Tuple}s, those that satisfy a specified predicate.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class SelectionOperator extends UnaryOperator {

	/**
	 * The {@code ExpressionEvaluator} for this {@code SelectionOperator}.
	 */
	protected ExpressionEvaluator evaluator;

	/**
	 * The predicate for this {@code SelectionOperator}.
	 */
	protected String predicate;

	/**
	 * Constructs a {@code SelectionOperator}.
	 * 
	 * @param input
	 *            the input {@code Operator} for the {@code SelectionOperator}
	 * @param predicate
	 *            the predicate for the {@code SelectionOperator}
	 * @throws ParsingException
	 *             if an error occurs while parsing the expressions
	 * @throws UnboundVariableException
	 *             if a variable in the predicate does not correspond to any attribute in the input schema of the
	 *             {@code SelectionOperator}
	 */
	public SelectionOperator(Operator input, String predicate) throws ParsingException, UnboundVariableException {
		super(input);
		this.predicate = predicate;
		evaluator = new ExpressionEvaluator(new LogicalExpression(predicate), input.outputSchema());
		// TODO complete this method (10 points)
	}

	/**
	 * Returns the predicate of this {@code SelectionOperator}.
	 * 
	 * @return the predicate of this {@code SelectionOperator}
	 */
	public String predicate() {
		return predicate;
	}

	/**
	 * Returns the output schema of this {@code SelectionOperator}.
	 * 
	 * @return the output schema of this {@code SelectionOperator}
	 */
	@Override
	public RelationSchema outputSchema() {
		// TODO complete this method (10 points)
		return null;
	}

	/**
	 * Determines whether or not this {@code SelectionOperator} has the next output {@code Tuple}.
	 * 
	 * @return {@code true} if this {@code SelectionOperator} has the next output {@code Tuple}; {@code false} otherwise
	 */
	@Override
	public boolean hasNext() {
		// TODO complete this method (10 points)
		return false;
	}

	/**
	 * Returns the next output {@code Tuple} from this {@code SelectionOperator}.
	 * 
	 * @return the next output {@code Tuple} from this {@code SelectionOperator}
	 */
	@Override
	public Tuple next() {
		// TODO complete this method (10 points)
		return null;
	}

	/**
	 * Rewind this {@code SelectionOperator}.
	 */
	@Override
	public void rewind() {
		// TODO complete this method (10 points)
	}

}

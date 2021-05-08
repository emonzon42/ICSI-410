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
	 * The current index of this {@code SelectionOperator}.
	 */
	protected int index;

	/**
	 * The index indicating where the last {@code Tuple} satisfying the predicate lies, in this {@code SelectionOperator}.
	 */
	protected int lastIndex;

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
		evaluator = new ExpressionEvaluator(new LogicalExpression(predicate), inputSchema());
		
		// goes through and finds last index that satisfies predicate
		while (input.hasNext()) {
			Tuple t = input.next();
			index++;
			if (evaluator.evaluate(t) == Boolean.TRUE){
				lastIndex = index;
			}
		}
		rewind();
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
		return inputSchema();
	}

	/**
	 * Determines whether or not this {@code SelectionOperator} has the next output {@code Tuple}.
	 * 
	 * @return {@code true} if this {@code SelectionOperator} has the next output {@code Tuple}; {@code false} otherwise
	 */
	@Override
	public boolean hasNext() {
		return index < lastIndex ? true : false;
	}

	/**
	 * Returns the next output {@code Tuple} from this {@code SelectionOperator}.
	 * 
	 * @return the next output {@code Tuple} from this {@code SelectionOperator}
	 */
	@Override
	public Tuple next() {
		while (hasNext()) {
			Tuple t = input.next();
			index++;
			try {
				if (evaluator.evaluate(t) == Boolean.TRUE){
					return t;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Rewind this {@code SelectionOperator}.
	 */
	@Override
	public void rewind() {
		index = 0;
		input.rewind();
	}

}

package hdb.query.relational;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.Tuple;

/**
 * A {@code TupleArrayReaderOperator} reads {@code Tuple}s from an array.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class TupleArrayReader extends Operator {

	/**
	 * The input array for this {@code TupleArrayReaderOperator}.
	 */
	protected Tuple[] input;

	/**
	 * The {@code RelationSchema} of this {@code TupleArrayReaderOperator}.
	 */
	protected RelationSchema schema;

	/**
	 * The current index of this {@code TupleArrayReaderOperator} (used for iteration).
	 */
	protected int currentIndex = 0;

	/**
	 * Constructs a {@code UnaryOperator}.
	 * 
	 * @param input
	 *            the input array for this {@code TupleArrayReaderOperator}
	 * @param schema
	 *            the {@code RelationSchema} for the {@code TupleArrayReaderOperator}
	 */
	public TupleArrayReader(Tuple[] input, RelationSchema schema) {
		this.input = input;
		this.schema = schema;
	}

	@Override
	public RelationSchema outputSchema() { // returns the output schema of the operator.
		return schema;
	}

	@Override
	public boolean hasNext() { //determines whether or not the operator has the next outputTuple.
		if (currentIndex < input.length)
			return true;
		return false;
	}

	@Override
	public Tuple next() { //returns the next outputTuple.
		Tuple next = input[currentIndex];
		currentIndex++;
		return next;
	}

	@Override
	public void rewind() { //rewinds the operator in order to retrieve all of the outputTuples again.
		currentIndex = 0;
	}

}

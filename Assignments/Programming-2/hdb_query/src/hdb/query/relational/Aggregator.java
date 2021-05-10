package hdb.query.relational;

import java.util.Iterator;

import hdb.data.relational.RelationSchema;
import hdb.data.relational.Tuple;

/**
 * An {@code Aggregator} groups all {@code Tuple}s from an input {@code Operator} and obtains, for each group of
 * {@code Tuple}s, a {@code Tuple} that represents/summarizes that group of {@code Tuple}s.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Aggregator {


	private Operator input;
	private RelationSchema schema;
	private String[] groupingAttributeNames;
	private Class<?>[] aggregateFunctionTypes;
	private String[] aggregationAttributeNames;
	
	/**
	 * Constructs an {@code Aggregator}.
	 * 
	 * @param input
	 *            the input {@code Operator} for the {@code Aggregator}
	 * @param outputSchema
	 *            the output schema for the {@code Aggregator}
	 * @param groupingAttributeNames
	 *            the names of the grouping attributes
	 * @param aggregateFunctionTypes
	 *            the types of the {@code AggregateFunction}s used by the {@code Aggregator}
	 * @param aggregationAttributeNames
	 *            the names of attributes used by the {@code AggregateFunction}s
	 */
	public Aggregator(Operator input, RelationSchema outputSchema, String[] groupingAttributeNames,
			Class<?>[] aggregateFunctionTypes, String[] aggregationAttributeNames) {
		// TODO complete this method (5 points)
		this.input = input;
		this.schema = outputSchema;
		this.groupingAttributeNames = groupingAttributeNames;
		this.aggregateFunctionTypes = aggregateFunctionTypes;
		this.aggregationAttributeNames = aggregationAttributeNames;

		
	}

	/**
	 * Returns an iterator over the output {@code Tuple}s of this {@code Aggregator}.
	 * 
	 * @return an iterator over the output {@code Tuple}s of this {@code Aggregator}
	 */
	public Iterator<Tuple> iterator() {
		// TODO complete this method (5 points)
		return null;
	}

}

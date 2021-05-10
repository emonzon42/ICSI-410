package hdb.data.nonrelational;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * A {@code CollectionSchema} represents the schema of a non-relational collection.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class CollectionSchema implements java.io.Serializable {

	/**
	 * Automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -100208280521684870L;

	/**
	 * An {@code InvalidAttributeIndexException} is thrown if an invalid attribute index is given to a
	 * {@code CollectionSchema}.
	 * 
	 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
	 */
	public static class InvalidAttributeIndexException extends Exception {

		/**
		 * Automatically generated serial version UID.
		 */
		private static final long serialVersionUID = -7371027889948222798L;

	}

	/**
	 * A {@code HashMap} that associates each attribute name with an attribute index.
	 */
	HashMap<String, Integer> name2index = new HashMap<String, Integer>();

	/**
	 * A {@code HashMap} that associates each attribute index with an attribute name.
	 */
	HashMap<Integer, String> index2name = new HashMap<Integer, String>();

	/**
	 * A {@code HashMap} that associates attribute indices with {@code CollectionSchema}s.
	 */
	HashMap<Integer, CollectionSchema> index2schema = new HashMap<Integer, CollectionSchema>();

	/**
	 * Constructs a {@code CollectionSchema}.
	 */
	public CollectionSchema() {
	}

	/**
	 * Returns a string representation of this {@code CollectionSchema}.
	 */
	@Override
	public String toString() {
		String s = "{";
		for (Entry<Integer, String> e : index2name.entrySet()) {
			s += (s.length() == 1 ? "" : ", ") + e.getKey() + "=" + e.getValue();
			CollectionSchema c = index2schema.get(e.getKey());
			if (c != null)
				s += c.toString();
		}
		return s + "}";
	}

	/**
	 * Returns the name of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the name of the specified attribute
	 */
	public String attributeName(int attributeIndex) {
		return index2name.get(attributeIndex);
	}

	/**
	 * Returns the subschema associated with the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the subschema associated with the specified attribute
	 */
	public CollectionSchema subschema(int attributeIndex) {
		return index2schema.get(attributeIndex);
	}

	/**
	 * Returns the index of the specified attribute in this {@code CollectionSchema} (registers that attribute
	 * if no such attribute has been registered in this {@code CollectionSchema}).
	 * 
	 * @param attributeName
	 *            the name of an attribute
	 * @return the index of the specified attribute in this {@code CollectionSchema}
	 */
	public int[] attributeIndex(String attributeName) {
			
		if (attributeName.contains(".") && name2index.get(attributeName.split("\\.")[0]) == null) {//creates attribute and subattributes
			name2index.put(attributeName.split("\\.")[0], name2index.size());
			index2name.put(name2index.get(attributeName.split("\\.")[0]), attributeName.split("\\.")[0]);
			
			CollectionSchema sub = new CollectionSchema();
			sub.attributeIndex(attributeName.split("\\.")[1]);
			index2schema.put(name2index.get(attributeName.split("\\.")[0]),sub);

			return new int[] {name2index.get(attributeName.split("\\.")[0]), sub.name2index.get(attributeName.split("\\.")[1])};
		} else if (attributeName.contains(".")){ //returns subattributes / creates new subattribute
			System.out.println(name2index.get(attributeName.split("\\.")[0]));

			//creates subattribute (and schema) if needed
			if (index2schema.get(name2index.get(attributeName.split("\\.")[0])) != null){
				subschema(name2index.get(attributeName.split("\\.")[0])).attributeIndex(attributeName.split("\\.")[1]);
			} else{
				CollectionSchema sub = new CollectionSchema();
				sub.attributeIndex(attributeName.split("\\.")[1]);
				index2schema.put(name2index.get(attributeName.split("\\.")[0]),sub);
			}

			return new int[] {name2index.get(attributeName.split("\\.")[0]), 
							subschema(name2index.get(attributeName.split("\\.")[0])).name2index.get(attributeName.split("\\.")[1])};
		}else if (name2index.get(attributeName) == null){ //creates attribute
			name2index.put(attributeName, name2index.size());
			index2name.put(name2index.get(attributeName), attributeName);
			return new int [] {name2index.get(attributeName)};
		}else{ // returns attribute
			return new int [] {name2index.get(attributeName)};
		}
		
	}

	/**
	 * Returns the name of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the name of the specified attribute
	 * @throws InvalidAttributeIndexException
	 *             if the specified attribute index is invalid
	 */
	public String attributeName(int[] attributeIndex) throws InvalidAttributeIndexException {
		if (index2name.get(attributeIndex[0]) == null){
			throw new InvalidAttributeIndexException();
		}	

		if (attributeIndex.length > 1){
			// recursively concatonates all sub attributes
			String name = index2name.get(attributeIndex[0])
			.concat(".")
			.concat(index2schema.get(attributeIndex[0]).attributeName(Arrays.copyOfRange(attributeIndex, 1, attributeIndex.length)));
			
			return name;
			
		}else{
			return index2name.get(attributeIndex[0]);
		}
	}

}

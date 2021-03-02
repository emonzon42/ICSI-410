package hdb.data.relational;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * A {@code Tuple} represents a record in a relation containing a number of attributes.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class Tuple implements java.io.Serializable {

	/**
	 * A {@code TypeException} is thrown when an object is not an instance of an appropriate type.
	 * 
	 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
	 */
	public static class TypeException extends Exception {

		/**
		 * Automatically generated serial version UID.
		 */
		private static final long serialVersionUID = 2260118532930630008L;

	}

	/**
	 * Automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -2038398067728844490L;

	/**
	 * The {@code RelationSchema} for this {@code Tuple}.
	 */
	RelationSchema schema;

	/**
	 * The attribute values of this {@code Tuple}.
	 */
	Object[] attributeValues;

	/**
	 * Constructs a {@code Tuple}.
	 * 
	 * @param schema
	 *            a {@code RelationSchema}
	 * @param attributeValues
	 *            the attribute values of the {@code Tuple}
	 * @throws TypeException
	 *             if a specified attribute value does not match the type of the corresponding attribute
	 */
	public Tuple(RelationSchema schema, Object... attributeValues) throws TypeException {
		this.schema = schema;
		this.attributeValues = new Object[schema.attributeTypes.length];
		for (int i = 0; i < schema.attributeTypes.length; i++)
			setAttribute(i, attributeValues[i]);
	}

	/**
	 * Constructs a {@code Tuple} from the specified {@code ObjectInputStream}.
	 * 
	 * @param schema
	 *            a {@code RelationSchema}
	 * @param in
	 *            an {@code ObjectInputStream}
	 * @throws TypeException
	 *             if an attribute value does not match the type of the corresponding attribute
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ClassNotFoundException
	 *             if the class of a serialized object cannot be found
	 */
	public Tuple(RelationSchema schema, ObjectInputStream in)
			throws ClassNotFoundException, TypeException, IOException {
		this.schema = schema;
		attributeValues = new Object[schema.attributeTypes.length];
		for (int i = 0; i < schema.attributeTypes.length; i++)
			setAttribute(i, read(schema.attributeType(i), in));
	}

	/**
	 * Returns the value of the specified attribute.
	 * 
	 * @param i
	 *            the index of an attribute
	 * @return the value of the specified attribute
	 */
	public Object attributeValue(int i) {
		return attributeValues[i];
	}

	/**
	 * Returns a string representation of this {@code Tuple}.
	 */
	@Override
	public String toString() {
		return Arrays.toString(attributeValues);
	}

	/**
	 * Sets the value of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @param o
	 *            the value of the attribute
	 * @throws TypeException
	 *             if the specified object is not an instance of the type of the specified attribute
	 */
	public void setAttribute(int attributeIndex, Object o) throws TypeException {
		//System.err.println(o.getClass() + " | " + schema.attributeType(attributeIndex));
		
		if(o.getClass().equals(schema.attributeType(attributeIndex))) //if o's class is the same class as the class that should be in that index
			attributeValues[attributeIndex] = o;
		else
			throw new TypeException();
	}

	/**
	 * Writes the attributes of this {@code Tuple} to the specified {@code ObjectOutputStream}.
	 * 
	 * @param out
	 *            an {@code ObjectOutputStream}.
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void writeAttributes(ObjectOutputStream out) throws IOException {
		for (int i = 0; i < attributeValues.length; i++) {
			write(attributeValues[i], out);
		}
	}

	/**
	 * Writes the specified object to the specified {@code ObjectOutputStream}.
	 * 
	 * @param o
	 *            an object
	 * @param out
	 *            an {@code ObjectOutputStream}
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void write(Object o, ObjectOutputStream out) throws IOException {
		if(o.getClass().equals(Integer.class))
			out.writeInt((Integer) o);
		else if(o.getClass().equals(Double.class))
			out.writeDouble((Double) o);
		else
			out.writeObject(o);
	}

	/**
	 * Reads an object of the specified type from the specified {@code ObjectInputStream}.
	 * 
	 * @param type
	 *            a type
	 * @param in
	 *            an {@code ObjectInputStream}
	 * @return the object read
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ClassNotFoundException
	 *             if the class of a serialized object cannot be found
	 */
	protected Object read(Class<?> type, ObjectInputStream in) throws IOException, ClassNotFoundException {
		if(type.equals(Integer.class))
			return in.readInt();
		else if(type.equals(Double.class))
			return in.readDouble();
		else
			return in.readObject();
	}

}

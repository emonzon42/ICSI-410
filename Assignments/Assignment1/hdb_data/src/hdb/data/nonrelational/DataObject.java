package hdb.data.nonrelational;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import hdb.data.nonrelational.CollectionSchema.InvalidAttributeIndexException;

/**
 * A {@code DataObject} represents an entity using a number of attributes organized possibly in a hierarchical fashion.
 * {@code DataObject}s in a non-relational collection can have different sets of attributes.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class DataObject implements java.io.Serializable {

	/**
	 * Automatically generated serial version UID.
	 */
	private static final long serialVersionUID = -5147919692261347227L;

	/**
	 * The {@code CollectionSchema} for this {@code DataObject}.
	 */
	CollectionSchema schema;

	/**
	 * A {@code HashMap} that associates each attribute index with a value.
	 */
	HashMap<Integer, Object> index2value = new HashMap<Integer, Object>();

	/**
	 * Constructs a {@code DataObject}.
	 * 
	 * @param schema
	 *            a {@code CollectionSchema}
	 */
	public DataObject(CollectionSchema schema) {
		this.schema = schema;
	}

	/**
	 * Returns the {@code CollectionSchema} associated this {@code DataObject}.
	 * 
	 * @return the {@code CollectionSchema} associated this {@code DataObject}
	 */
	public CollectionSchema schema() {
		return schema;
	}

	/**
	 * Returns a string representation of this {@code DataObject}.
	 */
	@Override
	public String toString() {
		String s = "{";
		for (Entry<Integer, Object> e : index2value.entrySet()) {
			s += (s.length() == 1 ? "" : ", ");
			s += (e.getKey() + ":" + schema.attributeName(e.getKey()) + "=" + e.getValue());
		}
		return s + "}";
	}

	/**
	 * Sets the value of the specified attribute.
	 * 
	 * @param attributeName
	 *            the name of an attribute
	 * @param o
	 *            the value of the attribute
	 */
	public void setAttribute(String attributeName, Object o) {
		index2value.put(schema.attributeIndex(attributeName)[0], o);
	}

	/**
	 * Returns the value of the specified attribute.
	 * 
	 * @param attributeIndex
	 *            the index of an attribute
	 * @return the value of the specified attribute
	 */
	public Object attributeValue(int[] attributeIndex) {
		return index2value.get(attributeIndex[attributeIndex.length-1]);
	}

	/**
	 * Writes the attributes of this {@code DataObject} to the specified {@code ObjectOutputStream}.
	 * 
	 * @param out
	 *            an {@code ObjectOutputStream}.
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void writeAttributes(ObjectOutputStream out) throws IOException {
		for (int i = 0; i < index2value.size(); i++) {
			out.writeObject(index2value.get(i));
		}
	}

	/**
	 * Constructs a {@code DataObject} from the specified {@code ObjectInputStream}.
	 * 
	 * @param schema
	 *            a {@code CollectionSchema}
	 * @param in
	 *            an {@code ObjectInputStream}
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ClassNotFoundException
	 *             if the class of a serialized object cannot be found
	 * @throws InvalidAttributeIndexException
	 *             if an attribute not registered in the {@code CollectionSchema} is read
	 */
	public DataObject(CollectionSchema schema, ObjectInputStream in)
			throws IOException, ClassNotFoundException, InvalidAttributeIndexException {
		this(schema);

		for (int i = 0; i < schema.index2name.size(); i++){
			setAttribute(schema.attributeName(i), in.readObject());
		}
			
	}

}

package sep.util.io.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;
import java.util.Collection;

public final class SerializationUtil {
	public static Serializable deserialization(final InputStream inputStream) throws ClassNotFoundException, IOException {
		final Serializable object;
		try (final ObjectInputStream input = new ObjectInputStream(inputStream)) {
			object = (Serializable) input.readObject();
		}
		return object;
	}
	
	public static Serializable[] deserializations(final InputStream inputStream) throws IOException, ClassNotFoundException {
		return (Serializable[]) deserialization(inputStream);
	}
	
	public static <T extends Serializable> void serialization(final OutputStream outputStream, final T object) throws IOException {
		try (ObjectOutputStream output = new ObjectOutputStream(outputStream)) {
			output.writeObject(object);
		}
	}

	public static void serialization(final OutputStream outputStream, final Collection<Serializable> objects) throws IOException {
		serialization(outputStream, objects.toArray(new Serializable[objects.size()]));
	}
	
	public static void serialization(final OutputStream outputStream, final Serializable... objects) throws IOException {
		serialization(outputStream, (Serializable) objects);
	}

	public static SignedObject genSignedObject(final Serializable object, final KeyPairGenerator generator) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, IOException {
		return new SignedObject(object, generator.generateKeyPair().getPrivate(), Signature.getInstance(generator.getAlgorithm()));
	}
	
	private SerializationUtil() {
	}
}
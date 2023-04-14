package io.github.iwyfewwnt.uwgson;

import com.google.gson.*;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import io.github.iwyfewwnt.uwgson.deserializers.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A type adapter factory.
 *
 * <p><hr>
 * <pre>{@code
 *     new GsonBuilder()
 *             .registerTypeAdapterFactory(new UwGsonTypeAdapterFactory())
 *             .create();
 * }</pre>
 * <hr>
 */
@SuppressWarnings("unused")
public final class UwGsonTypeAdapterFactory implements TypeAdapterFactory {

	/**
	 * Initialize a {@link UwGsonTypeAdapterFactory} instance.
	 */
	public UwGsonTypeAdapterFactory() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Object typeAdapter = getTypeAdapter(type.getRawType());

		if (typeAdapter == null) {
			return null;
		}

		JsonSerializer<T> serializer = null;
		if (typeAdapter instanceof JsonSerializer) {
			//noinspection unchecked
			serializer = (JsonSerializer<T>) typeAdapter;
		}

		JsonDeserializer<T> deserializer = null;
		if (typeAdapter instanceof JsonDeserializer) {
			//noinspection unchecked
			deserializer = (JsonDeserializer<T>) typeAdapter;
		}

		// #nullSafe argument is added since google/gson#2.10
		return new TreeTypeAdapter<>(serializer, deserializer, gson, type, null, false);
	}

	/**
	 * Get a type adapter for the provided class.
	 *
	 * @param clazz		class type of the type adapter
	 * @return			type adapter or {@code null}
	 */
	private static Object getTypeAdapter(Class<?> clazz) {
		if (clazz == String.class) {
			return new StringJsonDeserializer();
		}

		if (clazz == Boolean.class) {
			return new BooleanJsonDeserializer();
		}

		if (clazz == Byte.class) {
			return new ByteJsonDeserializer();
		}

		if (clazz == Short.class) {
			return new ShortJsonDeserializer();
		}

		if (clazz == Integer.class) {
			return new IntegerJsonDeserializer();
		}

		if (clazz == Long.class) {
			return new LongJsonDeserializer();
		}

		if (clazz == Float.class) {
			return new FloatJsonDeserializer();
		}

		if (clazz == Double.class) {
			return new DoubleJsonDeserializer();
		}

		if (clazz == List.class) {
			return new UnmodifiableListDeserializer();
		}

		if (clazz == Map.class) {
			return new UnmodifiableMapDeserializer();
		}

		if (clazz == Set.class) {
			return new UnmodifiableSetDeserializer();
		}

		return null;
	}
}

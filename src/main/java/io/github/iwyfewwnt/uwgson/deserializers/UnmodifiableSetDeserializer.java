package io.github.iwyfewwnt.uwgson.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An unmodifiable {@link Set} JSON deserializer.
 */
@SuppressWarnings("unused")
public final class UnmodifiableSetDeserializer implements JsonDeserializer<Set<?>> {

	/**
	 * Initialize an {@link UnmodifiableSetDeserializer} instance.
	 */
	public UnmodifiableSetDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
		if (json == null || type == null
				|| context == null || json.isJsonNull()) {
			return null;
		}

		try {
			JsonArray jsonArray = json.getAsJsonArray();
			if (jsonArray.isEmpty()) {
				return null;
			}

			Type elementType = ((ParameterizedType) type)
					.getActualTypeArguments()[0];

			List<JsonElement> jsonList = jsonArray.asList();

			Set<?> set = new HashSet<>(jsonList.size());
			for (JsonElement jsonElement : jsonList) {
				set.add(context.deserialize(jsonElement, elementType));
			}

			return Collections.unmodifiableSet(set);
		} catch (ClassCastException
				| NullPointerException
				| IndexOutOfBoundsException
				| TypeNotPresentException
				| MalformedParameterizedTypeException
				| UnsupportedOperationException
				| IllegalStateException
				| AssertionError e) {
			e.printStackTrace();
		}

		return null;
	}
}

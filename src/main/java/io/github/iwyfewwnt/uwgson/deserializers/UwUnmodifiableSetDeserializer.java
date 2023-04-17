package io.github.iwyfewwnt.uwgson.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

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
public final class UwUnmodifiableSetDeserializer implements JsonDeserializer<Set<?>> {

	/**
	 * Initialize an {@link UwUnmodifiableSetDeserializer} instance.
	 */
	public UwUnmodifiableSetDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
		if (json == null || json.isJsonNull()) {
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
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	}
}

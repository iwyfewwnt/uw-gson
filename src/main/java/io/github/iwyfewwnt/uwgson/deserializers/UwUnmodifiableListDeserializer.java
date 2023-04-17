package io.github.iwyfewwnt.uwgson.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An unmodifiable {@link List} JSON deserializer.
 */
@SuppressWarnings("unused")
public final class UwUnmodifiableListDeserializer implements JsonDeserializer<List<?>> {

	/**
	 * Initialize an {@link UwUnmodifiableListDeserializer} instance.
	 */
	public UwUnmodifiableListDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
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

			List<?> list = new ArrayList<>(jsonList.size());
			for (JsonElement jsonElement : jsonList) {
				list.add(context.deserialize(jsonElement, elementType));
			}

			return Collections.unmodifiableList(list);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	}
}

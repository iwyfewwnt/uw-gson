/*
 * Copyright 2023 iwyfewwnt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

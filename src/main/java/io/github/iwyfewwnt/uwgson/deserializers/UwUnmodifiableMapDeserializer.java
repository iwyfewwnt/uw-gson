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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * An unmodifiable {@link Map} JSON deserializer.
 */
@SuppressWarnings("unused")
public final class UwUnmodifiableMapDeserializer implements JsonDeserializer<Map<?, ?>> {

	/**
	 * Initialize an {@link UwUnmodifiableMapDeserializer} instance.
	 */
	public UwUnmodifiableMapDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<?, ?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
		if (json == null || json.isJsonNull()) {
			return null;
		}

		try {
			Set<Map.Entry<String, JsonElement>> jsonEntrySet = json.getAsJsonObject()
					.entrySet();

			if (jsonEntrySet.isEmpty()) {
				return null;
			}

			Type[] genericTypes = ((ParameterizedType) type)
					.getActualTypeArguments();

			Type keyType = genericTypes[0];
			Type valueType = genericTypes[1];

			Map<Object, Object> map = new HashMap<>(jsonEntrySet.size());
			for (Map.Entry<String, JsonElement> entry : jsonEntrySet) {
				Object key = context.deserialize(new JsonPrimitive(entry.getKey()), keyType);
				Object val = context.deserialize(entry.getValue(), valueType);

				map.put(key, val);
			}

			return Collections.unmodifiableMap(map);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	}
}

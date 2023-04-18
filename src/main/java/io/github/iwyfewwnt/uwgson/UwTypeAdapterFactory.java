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
 *             .registerTypeAdapterFactory(new UwTypeAdapterFactory())
 *             .create();
 * }</pre>
 * <hr>
 */
@SuppressWarnings({"unused", "unchecked"})
public final class UwTypeAdapterFactory implements TypeAdapterFactory {

	/**
	 * Initialize a {@link UwTypeAdapterFactory} instance.
	 */
	public UwTypeAdapterFactory() {
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

		if (typeAdapter instanceof TypeAdapter) {
			return (TypeAdapter<T>) typeAdapter;
		}

		JsonSerializer<T> serializer = null;
		if (typeAdapter instanceof JsonSerializer) {
			serializer = (JsonSerializer<T>) typeAdapter;
		}

		JsonDeserializer<T> deserializer = null;
		if (typeAdapter instanceof JsonDeserializer) {
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
			return new UwStringJsonDeserializer();
		}

		if (clazz == Boolean.class) {
			return new UwBooleanJsonDeserializer();
		}

		if (clazz == Byte.class) {
			return new UwByteJsonDeserializer();
		}

		if (clazz == Short.class) {
			return new UwShortJsonDeserializer();
		}

		if (clazz == Integer.class) {
			return new UwIntegerJsonDeserializer();
		}

		if (clazz == Long.class) {
			return new UwLongJsonDeserializer();
		}

		if (clazz == Float.class) {
			return new UwFloatJsonDeserializer();
		}

		if (clazz == Double.class) {
			return new UwDoubleJsonDeserializer();
		}

		if (clazz == List.class) {
			return new UwUnmodifiableListDeserializer();
		}

		if (clazz == Map.class) {
			return new UwUnmodifiableMapDeserializer();
		}

		if (clazz == Set.class) {
			return new UwUnmodifiableSetDeserializer();
		}

		return null;
	}
}

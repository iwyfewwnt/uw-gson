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

import java.lang.reflect.Type;

/**
 * A {@link Character} JSON deserializer.
 */
@SuppressWarnings("unused")
public final class UwCharacterJsonDeserializer implements JsonDeserializer<Character> {

	/**
	 * Initialize a {@link UwCharacterJsonDeserializer} instance.
	 */
	public UwCharacterJsonDeserializer() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
		if (json == null || json.isJsonNull()) {
			return null;
		}

		try {
			String str = json.getAsString();

			if (str.equals("null")) {
				return null;
			}

			if (str.length() == 1) {
				return str.charAt(0);
			}

			throw new UnsupportedOperationException(
					"Unable to deserialize the <"
							+ type.getTypeName() + "> type"
			);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	}
}

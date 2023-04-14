
package io.github.iwyfewwnt.uwgson;

import com.google.gson.JsonElement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link JsonElement} deserialization utility.
 */
@SuppressWarnings("unused")
public final class UwJson {

	/**
	 * A default "isAllowNullString" option boolean value.
	 */
	private static final boolean DEFAULT_IS_ALLOW_NULL_STRING = true;

	/**
	 * A default "isAllowEmptyString" option boolean value.
	 */
	private static final boolean DEFAULT_IS_ALLOW_EMPTY_STRING = true;

	/**
	 * A default "isAllowIntAsBool" option boolean value.
	 */
	private static final boolean DEFAULT_IS_ALLOW_INT_AS_BOOL = false;

	/**
	 * An "isAllowNullString" option map that based on threads.
	 */
	private static final Map<Thread, Boolean> IS_ALLOW_NULL_STRING_OPTION_MAP = new ConcurrentHashMap<>();

	/**
	 * An "isAllowEmptyString" option map that based on threads.
	 */
	private static final Map<Thread, Boolean> IS_ALLOW_EMPTY_STRING_OPTION_MAP = new ConcurrentHashMap<>();

	/**
	 * An "isAllowIntAsBool" option map that based on threads.
	 */
	private static final Map<Thread, Boolean> IS_ALLOW_INT_AS_BOOL_OPTION_MAP = new ConcurrentHashMap<>();

	/**
	 * Deserialize {@link JsonElement} to a primitive object.
	 *
	 * <p>Supported deserialize options:
	 * <ul>
	 *     <li>Boolean :: "isAllowNullString" :: default # {@code true}.
	 *     <li>Boolean :: "isAllowEmptyString" :: default # {@code true}.
	 *     <li>Boolean :: "isAllowIntAsBool" :: default # {@code false}.
	 * </ul>
	 *
	 * <p>Supported primitive types:
	 * <ul>
	 *     <li>{@link String}.
	 *     <li>{@link Boolean}.
	 *     <li>{@link Byte}.
	 *     <li>{@link Short}.
	 *     <li>{@link Integer}.
	 *     <li>{@link Long}.
	 *     <li>{@link Float}.
	 *     <li>{@link Double}.
	 *     <li>{@link Character}.
	 * </ul>
	 *
	 * @param json		json element to deserialize
	 * @param clazz		class of the primitive object
	 * @param <T>		class type
	 * @return			deserialized primitive object or {@code null}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(JsonElement json, Class<T> clazz) {
		try {
			if (json == null || clazz == null
					|| json.isJsonNull()) {
				return null;
			}

			String str = json.getAsString();

			boolean isAllowNullString = getIsAllowNullStringOption();
			if (!isAllowNullString && str.equals("null")) {
				return null;
			}

			boolean isAllowEmptyString = getIsAllowEmptyStringOption();
			if (!isAllowEmptyString && str.isEmpty()) {
				return null;
			}

			if (clazz == String.class) {
				return (T) str;
			}

			String exceptionMessage = "Unable to deserialize the <"
					+ clazz.getSimpleName() + "> class";

			if (clazz == Boolean.class) {
				if (str.equals("true")) {
					return (T) Boolean.TRUE;
				}

				if (str.equals("false")) {
					return (T) Boolean.FALSE;
				}

				boolean isAllowIntAsBool = getIsAllowIntAsBoolOption();
				if (isAllowIntAsBool) {
					try {
						int intVal = Integer.parseInt(str);

						if (intVal == 1) {
							return (T) Boolean.TRUE;
						}

						if (intVal == 0) {
							return (T) Boolean.FALSE;
						}
					} catch (NumberFormatException ignored) {
					}
				}

				throw new UnsupportedOperationException(exceptionMessage);
			}

			if (clazz == Byte.class) {
				try {
					return (T) (Byte) Byte.parseByte(str);
				} catch (NumberFormatException ignored) {
				}

				throw new UnsupportedOperationException(exceptionMessage);
			}

			if (clazz == Short.class) {
				try {
					return (T) (Short) Short.parseShort(str);
				} catch (NumberFormatException ignored) {
				}

				throw new UnsupportedOperationException(exceptionMessage);
			}

			if (clazz == Integer.class) {
				try {
					return (T) (Integer) Integer.parseInt(str);
				} catch (NumberFormatException ignored) {
				}

				throw new UnsupportedOperationException(exceptionMessage);
			}

			if (clazz == Long.class) {
				try {
					return (T) (Long) Long.parseLong(str);
				} catch (NumberFormatException ignored) {
				}

				throw new UnsupportedOperationException(exceptionMessage);
			}

			if (clazz == Float.class) {
				try {
					return (T) (Float) Float.parseFloat(str);
				} catch (NumberFormatException ignored) {
				}

				throw new UnsupportedOperationException(exceptionMessage);
			}

			if (clazz == Double.class) {
				try {
					return (T) (Double) Double.parseDouble(str);
				} catch (NumberFormatException ignored) {
				}

				throw new UnsupportedOperationException(exceptionMessage);
			}

			if (clazz == Character.class) {
				if (str.length() == 1) {
					return (T) (Character) str.charAt(0);
				}

				throw new UnsupportedOperationException(exceptionMessage);
			}

			throw new UnsupportedOperationException(exceptionMessage);
		} catch (UnsupportedOperationException | IllegalStateException| AssertionError e) {
			e.printStackTrace();
		} finally {
			setIsAllowNullStringOption(DEFAULT_IS_ALLOW_NULL_STRING);
			setIsAllowEmptyStringOption(DEFAULT_IS_ALLOW_EMPTY_STRING);
			setIsAllowIntAsBoolOption(DEFAULT_IS_ALLOW_INT_AS_BOOL);
		}

		return null;
	}

	/**
	 * Get an "isAllowNullString" option.
	 *
	 * @return	boolean value of the option
	 */
	public static boolean getIsAllowNullStringOption() {
		return getOption(IS_ALLOW_NULL_STRING_OPTION_MAP, DEFAULT_IS_ALLOW_INT_AS_BOOL);
	}

	/**
	 * Get an "isAllowEmptyString" option.
	 *
	 * @return	boolean value of the option
	 */
	public static boolean getIsAllowEmptyStringOption() {
		return getOption(IS_ALLOW_EMPTY_STRING_OPTION_MAP, DEFAULT_IS_ALLOW_EMPTY_STRING);
	}

	/**
	 * Get an "isAllowIntAsBool" option.
	 *
	 * @return	boolean value of the option
	 */
	public static boolean getIsAllowIntAsBoolOption() {
		return getOption(IS_ALLOW_INT_AS_BOOL_OPTION_MAP, DEFAULT_IS_ALLOW_INT_AS_BOOL);
	}

	/**
	 * Set an "isAllowNullString" option.
	 *
	 * @param isAllowNullString		boolean value to set the option to
	 */
	public static void setIsAllowNullStringOption(Boolean isAllowNullString) {
		setOption(IS_ALLOW_NULL_STRING_OPTION_MAP, isAllowNullString, DEFAULT_IS_ALLOW_NULL_STRING);
	}

	/**
	 * Set an "isAllowEmptyString" option.
	 *
	 * @param isAllowEmptyString	boolean value to set the option to
	 */
	public static void setIsAllowEmptyStringOption(Boolean isAllowEmptyString) {
		setOption(IS_ALLOW_EMPTY_STRING_OPTION_MAP, isAllowEmptyString, DEFAULT_IS_ALLOW_EMPTY_STRING);
	}

	/**
	 * Set an "isAllowIntAsBool" option.
	 *
	 * @param isAllowIntAsBool	boolean value to set the option to
	 */
	public static void setIsAllowIntAsBoolOption(Boolean isAllowIntAsBool) {
		setOption(IS_ALLOW_INT_AS_BOOL_OPTION_MAP, isAllowIntAsBool, DEFAULT_IS_ALLOW_INT_AS_BOOL);
	}

	/**
	 * Get an option value by the current thread.
	 *
	 * @param optionMap		option map that based on threads
	 * @param defaultValue	default value of the option
	 * @param <T>			option type
	 * @return				option value or the default one
	 */
	private static <T> T getOption(Map<Thread, T> optionMap, T defaultValue) {
		if (optionMap == null) {
			return defaultValue;
		}

		Thread thread = Thread.currentThread();

		return optionMap.getOrDefault(thread, defaultValue);
	}

	/**
	 * Set an option value by the current thread.
	 *
	 * @param optionMap		option map that based on threads.
	 * @param value			value to set the option to
	 * @param defaultValue	default value of the option
	 * @param <T>			option type
	 */
	private static <T> void setOption(Map<Thread, T> optionMap, T value, T defaultValue) {
		if (optionMap == null) {
			return;
		}

		if (value == null) {
			value = defaultValue;
		}

		Thread thread = Thread.currentThread();

		optionMap.put(thread, value);
	}

	private UwJson() {
		throw new UnsupportedOperationException();
	}
}

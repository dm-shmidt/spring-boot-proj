package com.test.quotation.util;

import com.test.quotation.exception.UpdateFailedException;
import com.test.quotation.model.dto.BaseDto;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PropsMapper<T> {
    /**
     * Updates target object with appropriate values ignoring null-values.
     * Supposing that keys in map have snake_case format
     */
    public void updateValues(Map<String, Object> map, T target) {

        if (target == null) {
            throw new UpdateFailedException("Could not update. The object is null.");
        }

        Class<?> targetClass = target.getClass();

        Map<String, String> fields = Arrays.stream(targetClass.getDeclaredFields())
                .filter(field -> !field.getName().equalsIgnoreCase("id")
                && field.getType().getSuperclass() != BaseDto.class)
                .collect(Collectors.toMap(Field::getName, field -> field.getType().getName()));

        map.forEach((key, value) -> {
            String fieldName = toCamel(key);
            if (fields.containsKey(fieldName)) {
                String setterMethodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                try {
                    Class<?> setterArgumentType = Class.forName(fields.get(fieldName));

                    if (setterArgumentType != LinkedHashMap.class) {
                        Object valueToSet = setValue(setterArgumentType, value);
                        targetClass.getDeclaredMethod(setterMethodName, setterArgumentType)
                                .invoke(target, valueToSet);
                    } else {
                        throw new UpdateFailedException("Could not update sub-object of class " + setterArgumentType.getName());
                    }

                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    throw new UpdateFailedException("Failed to invoke method " + setterMethodName + "(" + value.getClass().getName() + ") on " + targetClass.getName());
                } catch (ClassNotFoundException | ParseException e) {
                    throw new UpdateFailedException("Failed to get value of " + value.toString());
                }
            }
        });
    }

    private String toCamel(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = Character.toLowerCase(s.charAt(i));
            if (c == '_') {
                continue;
            }
            if (i > 0 && s.charAt(i - 1) == '_') {
                c = Character.toUpperCase(c);
            }
            builder.append(c);
        }
        return builder.toString();
    }

    private Object setValue(Class<?> type, Object value) throws ParseException {
        if (type == LocalDate.class) {
            return LocalDate.parse(value.toString());
        }
        return value;
    }
}

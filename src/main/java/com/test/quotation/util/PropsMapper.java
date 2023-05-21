package com.test.quotation.util;

import com.test.quotation.exception.UpdateFailedException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        List<String> fields = Arrays.stream(targetClass.getDeclaredFields())
                .map(Field::getName).filter(f -> !f.equalsIgnoreCase("id")).toList();

        map.forEach((key, value) -> {
            String fieldName = toCamel(key);
            if (fields.contains(fieldName)) {
                String setterMethodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                try {
                    targetClass.getDeclaredMethod(setterMethodName, value.getClass()).invoke(target, value);
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    throw new UpdateFailedException("Failed to invoke method " + setterMethodName + "(" + value.getClass() + ") on " + targetClass.getName());
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
}

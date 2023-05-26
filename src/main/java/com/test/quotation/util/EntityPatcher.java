package com.test.quotation.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.quotation.model.dto.BaseRecord;

import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class EntityPatcher<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public EntityPatcher() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public T patch(BaseRecord updatesEntity, T entity) {
        TypeReference<Map<String, Object>> mapType = new TypeReference<>() {};
        Map<String, Object> entityMap = objectMapper.convertValue(entity, mapType);

        Map<String, Object> updatesMap = objectMapper.convertValue(updatesEntity, mapType);

        patchMaps(updatesMap, entityMap);

        return objectMapper.convertValue(entityMap, (Class<T>) entity.getClass());
    }

    private void patchMaps(Map<String, Object> updatesMap, Map<String, Object> entityMap) {
        // Convert keys of the input map to the camelCase format
        Map<String, Object> inputMap = updatesMap.entrySet()
                .stream()
                .filter(item -> item.getValue() != null)
                .collect(Collectors.toMap(item -> toCamel(item.getKey()), Map.Entry::getValue));

        for (Map.Entry<String, Object> inputEntry: inputMap.entrySet()) {
            String key = inputEntry.getKey();
            if (entityMap.containsKey(key) && !key.equalsIgnoreCase("id")) {
                Object patchVal = inputEntry.getValue();
                Object entityVal = entityMap.get(key);
                if (entityVal instanceof Map<?, ?> && patchVal instanceof Map<?, ?>
                        && !((Map<?, ?>) patchVal).isEmpty()) {
                    patchMaps((Map<String, Object>) patchVal, (Map<String, Object>) entityVal);
                } else {
                    if (!(patchVal instanceof Map<?, ?>) && patchVal != null && !(patchVal.equals(entityVal)) ) {
                        entityMap.put(key, patchVal);
                    }
                }
            }
        }
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

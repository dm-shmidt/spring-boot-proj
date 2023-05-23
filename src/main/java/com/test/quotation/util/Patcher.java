package com.test.quotation.util;

import com.test.quotation.model.dto.BaseDto;
import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.dto.SubscriptionDto;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class Patcher {
    private final Map<Class<? extends BaseDto>, PropsMapper> patchersMap =
            Map.of(
                    CustomerDto.class, new PropsMapper<CustomerDto>(),
                    QuotationDto.class, new PropsMapper<QuotationDto>(),
                    SubscriptionDto.class, new PropsMapper<SubscriptionDto>()
            );

    @SuppressWarnings("unchecked")
    public BaseDto patch(Map<String, Object> map, BaseDto target) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> targetClass = target.getClass();
        patchersMap.get(targetClass).updateValues(map, target);

        // Find and Patch child-dto
        List<Field> fields = Arrays.stream(target.getClass().getDeclaredFields())
                .filter(field -> field.getType().getSuperclass() == BaseDto.class)
                .toList();

        for (Field field : fields) {
            if (patchersMap.containsKey(field.getType())) {
                String getterName = "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                BaseDto childDto = (BaseDto) target.getClass().getDeclaredMethod(getterName).invoke(target);
                Class<?> childDtoClass = childDto.getClass();
                String childDtoPureClassName = childDtoClass.getName()
                        .substring(childDtoClass.getName().lastIndexOf(".") + 1)
                        .toLowerCase();
                String keyInUpdateMap = childDtoPureClassName
                        .substring(0, childDtoPureClassName.lastIndexOf("dto"));
                Map<String, Object> childUpdateMap = (Map<String, Object>) map.get(keyInUpdateMap);
                if (childUpdateMap != null && !childUpdateMap.isEmpty()) {
                    patch(childUpdateMap, childDto);
                }
            }
        }
        return target;
    }
}

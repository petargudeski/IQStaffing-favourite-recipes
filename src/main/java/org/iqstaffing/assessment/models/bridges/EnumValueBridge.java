package org.iqstaffing.assessment.models.bridges;

import org.hibernate.search.mapper.pojo.bridge.ValueBridge;
import org.hibernate.search.mapper.pojo.bridge.runtime.ValueBridgeToIndexedValueContext;
import org.iqstaffing.assessment.models.enums.Category;

public class EnumValueBridge implements ValueBridge<Category, String> {

    @Override
    public String toIndexedValue(Category text, ValueBridgeToIndexedValueContext valueBridgeToIndexedValueContext) {
        return text.getName();
    }
}
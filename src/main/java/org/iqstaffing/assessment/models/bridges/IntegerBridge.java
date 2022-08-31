package org.iqstaffing.assessment.models.bridges;

import org.hibernate.search.mapper.pojo.bridge.ValueBridge;
import org.hibernate.search.mapper.pojo.bridge.runtime.ValueBridgeToIndexedValueContext;

public class IntegerBridge implements ValueBridge<Integer, String> {

    @Override
    public String toIndexedValue(Integer value, ValueBridgeToIndexedValueContext valueBridgeToIndexedValueContext) {
        return value.toString();
    }
}
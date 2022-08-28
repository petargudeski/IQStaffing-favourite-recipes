package org.iqstaffing.assessment.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConverterUtil {

    /**
     *
     * @param c name of the Enum
     * @param string value you want to convert to Enum
     * @param <T>
     * @return Enum type
     */
    public <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if( c != null && string != null ) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch(IllegalArgumentException ex) {
                log.error("Error during converting String to Enum: {}", ex.getMessage());
            }
        }
        return null;
    }
}

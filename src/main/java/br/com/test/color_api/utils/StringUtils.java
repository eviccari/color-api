package br.com.test.color_api.utils;

public class StringUtils {

    private StringUtils(){}

    /**
     * Verify if string value is empty, in order:
     *    - null
     *    - blank
     *    - empty
     * @param value
     * @return
     */
    public static final boolean isEmpty(String value){
        return value == null
                || value.isBlank()
                || value.isEmpty();
    }
}

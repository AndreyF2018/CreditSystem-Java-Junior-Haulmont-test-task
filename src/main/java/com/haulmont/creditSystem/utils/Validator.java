package com.haulmont.creditSystem.utils;

import com.vaadin.ui.TextField;

public class Validator {

    public static boolean isLetterType(String value) {
        for (char ch : value.toCharArray()) {
            if (!Character.isLetter(ch)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDigitType(String value) {
        for (char ch : value.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNullFields(TextField... fields){
        for (TextField item : fields) {
            if (item.getValue().equals("")){
                return false;
            }
        }
        return true;
    }
}

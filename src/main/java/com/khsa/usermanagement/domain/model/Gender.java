package com.khsa.usermanagement.domain.model;

public enum Gender {
    MALE('M'),
    FEMALE('F');

    private final char code;

    private Gender(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    /**
     *
     * @param code
     * @return
     */
    public static Gender fromCode(char code) {
        if (code == 'M' || code == 'm') {
            return MALE;
        }
        if (code == 'F' || code == 'f') {
            return FEMALE;
        }
        return null;
    }
}



package com.khsa.usermanagement.domain.model;

public enum Gender {
    MALE('M', "Male"),
    FEMALE('F', "Female");

    private final char code;
    private final String name;

    private Gender(char code, String name) {
        this.code = code;
        this.name = name;
    }

    public char getCode() {
        return code;
    }
    public String getName() {
        return name;
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



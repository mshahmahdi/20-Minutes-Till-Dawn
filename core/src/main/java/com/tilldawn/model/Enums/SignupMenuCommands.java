package com.tilldawn.model.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignupMenuCommands {

    Password("(?=.*[A-Z])(?=.*\\d)(?=.*[@%$#&*()_]).{8,}")

    ;

    private final String pattern;

    SignupMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(pattern).matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }

}

package main.Passport;

import javax.inject.Inject;

@PassportNumber
public class NumberGenerator {
    @Inject
    @PassportNumber
    private String prefix;
    @Inject
    @PassportNumber
    private int number;
}

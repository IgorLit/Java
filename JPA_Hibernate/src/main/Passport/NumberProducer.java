package main.Passport;

import javax.enterprise.inject.Produces;

public class NumberProducer {
    @Produces
    @PassportNumber
    private String prefix = "MC";
    @Produces
    @PassportNumber
    private int number=216987;
}

package main.Adress;

import javax.enterprise.inject.Alternative;

@Alternative
public class AlternativeAddress extends Address{
    public AlternativeAddress() {
    }
    private String country = "Belarus";

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

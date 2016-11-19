package main.Adress;

@AddressWithCountry
public class FullAddress extends Address {
    public FullAddress() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String country="USA";
}

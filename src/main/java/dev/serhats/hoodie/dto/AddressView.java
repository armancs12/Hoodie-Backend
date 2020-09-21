package dev.serhats.hoodie.dto;

import dev.serhats.hoodie.domain.Address;
import lombok.Data;

@Data
public class AddressView {
    private long id;
    private String country;
    private String city;
    private String county;
    private String neighbourhood;

    public AddressView() {
    }

    public AddressView(Address address) {
        this.id = address.getId();
        this.country = address.getCountry();
        this.city = address.getCity();
        this.county = address.getCounty();
        this.neighbourhood = address.getNeighbourhood();
    }
}

package com.echoandrich.task.location;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "country_id", length = 2, nullable = false)
    private String countryId;

    @Column(name = "country_name", length = 40)
    private String countryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "region_id", nullable = false)
    private Region region;
}

package com.practitioner.backend.location;

import com.practitioner.backend.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocationRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private LocationRepository locationRepository;
    private static final String CITY = "Basel";
    private static final String COUNTRY = "Switzerland";
    private static final String NAME = "Main Clinic";

    @Test
    void saveAndFindLocation() {
        Location location = new Location();
        location.setName(NAME);
        location.setStreetAndNo("Spalengraben 8");
        location.setZipCode("4051");
        location.setCity(CITY);
        location.setCountry(COUNTRY);

        locationRepository.save(location);

        Location found = locationRepository.findById(location.getId()).orElseThrow();

        // direct comparision
        assertThat(found.getCity()).isEqualTo(CITY);
        assertThat(found.getName()).contains(NAME);

        // round-trip through the whole entity:
        assertThat(found)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(location);
    }

    @Test
    void savingLocationWithoutNameFails() {

        // violation because no name is set
        Location invalid = new Location();
        invalid.setStreetAndNo("Spalengraben 8");
        invalid.setZipCode("4051");
        invalid.setCity(CITY);
        invalid.setCountry(COUNTRY);
        
        assertThatThrownBy(() -> locationRepository.saveAndFlush(invalid))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}

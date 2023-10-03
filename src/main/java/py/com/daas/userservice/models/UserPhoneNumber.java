package py.com.daas.userservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_phone_numbers")
public class UserPhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city_code")
    private Long cityCode;

    @Column(name = "country_code")
    private Long countryCode;

    public UserPhoneNumber(String phoneNumber, Long cityCode, Long countryCode) {
        this.phoneNumber = phoneNumber;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
    }

}

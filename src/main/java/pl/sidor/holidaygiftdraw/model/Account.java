package pl.sidor.holidaygiftdraw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String name;
    private String surname;
    private String email;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime dateAdded;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthday;

    @ManyToMany(mappedBy = "accountSet")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Family> familySet;

    @OneToMany(mappedBy = "account")
    private Set<Gift> giftSet;

    @ManyToMany(mappedBy = "accountSet")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<HolidayEvent> holidayEventSet;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Cascade(value = {org.hibernate.annotations.CascadeType.DETACH})
    private Set<AccountRole> roles;

    @OneToMany(mappedBy = "giver")
    private List<GiftDraw> drawsAsGiver;
    @OneToMany(mappedBy = "receiver")
    private List<GiftDraw> drawsAsReceiver;


    public boolean isAdmin() {
        return roles.stream()
                .map(AccountRole::getName)
                .anyMatch(s -> s.equals("ADMIN"));
    }
}

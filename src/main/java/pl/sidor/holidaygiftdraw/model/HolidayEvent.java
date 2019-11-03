package pl.sidor.holidaygiftdraw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class HolidayEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identifier; //UUID
    private String name;
    @CreationTimestamp
    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateAdded;
    @Column(name = "_period")
    private int period; //chciałbym domyślną wartość ustawić, jak ????

    private boolean isDrawn = false;

    public LocalDate getDrawDate() {
        return dateAdded.toLocalDate().plusDays(period);
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Account> accountSet = new HashSet<>();

    private int giftMaxPrice;
    @OneToMany(mappedBy = "holidayEvent")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<GiftDraw> giftDraws;

    @ManyToOne
    private Account creator;
}

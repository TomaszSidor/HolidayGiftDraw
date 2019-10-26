package pl.sidor.holidaygiftdraw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class HolidayEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @CreationTimestamp
    @Column(updatable = false)
    @DateTimeFormat
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime dateAdded;
    private int period; //chciałbym domyślną wartość ustawić, jak ????

    private boolean isDrawn = false;

    @Formula(value = "(date_add(dateAdded, interval period day))")
    private LocalDate drawDate;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @DateTimeFormat
    private LocalDate eventDate;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Account> accountSet;

    private Long giftMaxPrice;

}

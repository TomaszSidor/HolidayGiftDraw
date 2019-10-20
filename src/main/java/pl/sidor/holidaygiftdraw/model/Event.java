package pl.sidor.holidaygiftdraw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime dateAdded;
    private int period = 10;

    @Formula(value = "(date_add(dateAdded, interval period day))")
    private LocalDateTime drawDate;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime eventDate;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Account> accountSet;

    private Long giftMaxPrice;

}

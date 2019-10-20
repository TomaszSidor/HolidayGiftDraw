package pl.sidor.holidaygiftdraw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime birthday;

    @ManyToMany(mappedBy = "accountSet")
    private Set<Family> familySet;

    @OneToOne
    private WishList wishList;
}

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
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime dateAdded;

    @OneToOne
    private Account account;
    @ManyToMany(mappedBy = "wishListSet")
    private Set<Gift> giftSet;

}



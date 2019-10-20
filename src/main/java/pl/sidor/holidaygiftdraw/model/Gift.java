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
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime dateAdded;

    private String name;
    private String URL;
    private int price;
    private GiftPriority giftPriority;
    
    @ManyToMany(mappedBy = "giftSet")
    private Set<WishList> wishListSet;
}

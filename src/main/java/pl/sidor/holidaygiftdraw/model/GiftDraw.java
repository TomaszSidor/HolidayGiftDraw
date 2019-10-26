package pl.sidor.holidaygiftdraw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftDraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account giver;
    @ManyToOne
    private Account receiver;
    @ManyToOne
    private HolidayEvent holidayEvent;



}

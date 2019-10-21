package pl.sidor.holidaygiftdraw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

//DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventCreationRequest {

    @NotEmpty
    @Size(min = 4)
    private String name;

    @NotEmpty
    @Size(min = 6, max = 100)
    private int period;
    private String eventDate;
    private Long giftMaxPrice;

    private String passwordConfirm;

}


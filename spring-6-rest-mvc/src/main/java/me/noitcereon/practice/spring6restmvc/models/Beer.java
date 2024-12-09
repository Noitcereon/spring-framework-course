package me.noitcereon.practice.spring6restmvc.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * A beer model made with the help of Lombok.
 *
 * <h2>Lombok with IntelliJ</h2>
 * To work optimally with Lombok you should enable Annotation Processing.
 * In IntelliJ v2022.3.3 Ultimate Edition it is located at:
 * <pre>"Settings" -> "Build, Execution, Deployment" -> "Compiler" -> "Annotation Processors" -> "Enable annotation processing" checkbox.</pre>
 *
 * <h2>De-lomboking</h2>
 * A note on "delomboking": In IntelliJ the Lombok plugin (installed by default) can "delombok" by:
 * <pre>
 *   1. Go to the file you want to delombok
 *   2. Menu -> Refactor -> Delombok.
 *</pre>
 */
@Data
@Builder
public class Beer {
    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}

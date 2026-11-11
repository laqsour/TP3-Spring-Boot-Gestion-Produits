package ma.oussama.gestionproduits.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire.")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères.")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Le prix est obligatoire.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à zéro.")
    private Double price;

    @NotNull(message = "La quantité est obligatoire.")
    @Min(value = 0, message = "La quantité ne peut pas être négative.")
    private Integer quantity;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères.")
    private String description;
}

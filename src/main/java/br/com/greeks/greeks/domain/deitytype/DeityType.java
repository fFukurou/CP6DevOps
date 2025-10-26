package br.com.greeks.greeks.domain.deitytype;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "deity_type")
@Getter
@Setter
@NoArgsConstructor
public class DeityType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deity_type_seq")
    @SequenceGenerator(name = "deity_type_seq", sequenceName = "DEITY_TYPE_SEQ", allocationSize = 1)
    @Column(name = "id_type")
    private Long id;

    @NotBlank(message = "Type name cannot be blank.")
    @Size(max = 100)
    @Column(name = "type_name", length = 100, nullable = false, unique = true)
    private String typeName;

    @Size(max = 250)
    @Column(name = "origin", length = 250)
    private String origin;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @Size(max = 100)
    @Column(name = "lifespan", length = 100)
    private String lifespan;

    @Size(max = 150)
    @Column(name = "power_source", length = 150)
    private String powerSource;

    @OneToMany(mappedBy = "deityType", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Deity> deities;
}
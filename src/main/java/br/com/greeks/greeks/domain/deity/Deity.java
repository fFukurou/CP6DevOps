package br.com.greeks.greeks.domain.deity;

import br.com.greeks.greeks.domain.deitytype.DeityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deity")
@Getter
@Setter
@NoArgsConstructor
public class Deity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deity_seq")
    @SequenceGenerator(name = "deity_seq", sequenceName = "DEITY_SEQ", allocationSize = 1)
    @Column(name = "id_deity")
    private Long id;

    @NotBlank(message = "Name cannot be blank.")
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Size(max = 150)
    @Column(name = "domain", length = 150)
    private String domain;

    @Size(max = 100)
    @Column(name = "roman_name", length = 100)
    private String romanName;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @NotNull(message = "Deity Type is required.")
    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch is generally preferred for performance
    @JoinColumn(name = "id_type", referencedColumnName = "id_type", nullable = false)
    private DeityType deityType;
}
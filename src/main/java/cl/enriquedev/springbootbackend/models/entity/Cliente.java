package cl.enriquedev.springbootbackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "clientes",
        uniqueConstraints = @UniqueConstraint(
                columnNames = "email",
                name = "UK_clientes_email"
        )
)
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "no puede estar vacío")
    @Size(min=4,max=12, message = "el tamaño debe estar entre 4 y 12 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "no puede estar vacío")
    private String apellido;

    @NotEmpty(message = "no puede estar vacío")
    @Email(message = "no es una dirección de correo bien formada")
    @Column(nullable = false,unique = true)
    private String email;

    @NotNull(message = "no puede estar vacío")
    @Column(name = "create_at")
    @Temporal(value = TemporalType.DATE)
    private Date createAt;

    private String foto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id",foreignKey = @ForeignKey(name = "FK_cliente_region"))
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Region region;

    // @PrePersist
    public void prePersist(){
        createAt = new Date();
    }
}

package cl.enriquedev.springbootbackend.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "usuarios",
    uniqueConstraints = {
            @UniqueConstraint(
                    columnNames = "username",
                    name = "UK_usuarios_username"
            ),
            @UniqueConstraint(
                    columnNames = "email",
                    name = "UK_usuarios_email"
            )
    }
)
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String username;

    private String nombre;
    private String apellido;
    private String email;


    @Column(length = 60)
    private String clave;

    private boolean activo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(
                    name = "usuario_id",
                    foreignKey = @ForeignKey(name = "FK_usuario_usuario_roles")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "rol_id",
                    foreignKey = @ForeignKey(name = "FK_roles_usuario_roles")
            ),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"usuario_id","rol_id"},
                    name = "UK_usuario_roles"
            )
    )
    private List<Rol> roles;
}

package cl.enriquedev.springbootbackend.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "roles",
        uniqueConstraints = @UniqueConstraint(
                columnNames = "nombre",
                name = "UK_roles_nombre"
        )
)
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String nombre;

    /*
    sirve para obtener usuarios
    asociados a roles, pero no vale la pena en este caso
     */
    //@ManyToMany(mappedBy = "roles")
    //List<Usuario> usuarios;
}

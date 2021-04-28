package cl.enriquedev.springbootbackend.models.dao;

import cl.enriquedev.springbootbackend.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUsuarioDao extends JpaRepository<Usuario,Long> {

    Usuario findByUsernameAndActivoTrue(String username);

    @Query("select u from Usuario u where u.username=?1 and u.activo = true ")
    Usuario getUsuarioActivo(String username);

}

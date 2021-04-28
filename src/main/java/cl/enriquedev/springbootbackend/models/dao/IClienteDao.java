package cl.enriquedev.springbootbackend.models.dao;

import cl.enriquedev.springbootbackend.models.entity.Cliente;
import cl.enriquedev.springbootbackend.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IClienteDao extends JpaRepository<Cliente,Long> {

    @Query("from Region")
    List<Region> findAllRegiones();

}

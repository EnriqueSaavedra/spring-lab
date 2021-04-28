package cl.enriquedev.springbootbackend.models.services;

import cl.enriquedev.springbootbackend.models.entity.Cliente;
import cl.enriquedev.springbootbackend.models.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    Page<Cliente> findAll(Pageable pageable);

    List<Cliente> findAll();

    Cliente findById(Long id);

    Cliente save(Cliente cliente);

    void delete(Long id);

    List<Region> findAllRegiones();
}

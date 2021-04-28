package cl.enriquedev.springbootbackend.models.services;

import cl.enriquedev.springbootbackend.models.entity.Usuario;

public interface IUsuarioService {

    Usuario findByUsername(String username);
}

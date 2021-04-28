package cl.enriquedev.springbootbackend.models.services.impl;

import cl.enriquedev.springbootbackend.models.dao.IUsuarioDao;
import cl.enriquedev.springbootbackend.models.entity.Usuario;
import cl.enriquedev.springbootbackend.models.services.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UserDetailsService, IUsuarioService {

    private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsernameAndActivoTrue(s);
        if(usuario == null){
            logger.error("No existe usuario en sistema");
            throw new UsernameNotFoundException("Usuario "+s+" no se encuentra en el sistema");
        }
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .peek(r -> logger.info("Rol- "+r.getNombre()))
                .map(r -> new SimpleGrantedAuthority(r.getNombre()))
                .collect(Collectors.toList());

        return new User(
                usuario.getUsername(),
                usuario.getClave(),
                usuario.isActivo(),
                true,
                true,
                true,
                authorities
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioDao.findByUsernameAndActivoTrue(username);
    }
}

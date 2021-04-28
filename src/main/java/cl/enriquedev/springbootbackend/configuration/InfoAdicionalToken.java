package cl.enriquedev.springbootbackend.configuration;

import cl.enriquedev.springbootbackend.models.entity.Usuario;
import cl.enriquedev.springbootbackend.models.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

    @Autowired
    private IUsuarioService usuarioService;

    /**
     * Agrega datos adicionales al token
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Usuario usuario = usuarioService.findByUsername(authentication.getName());
        Map<String,Object> info = new HashMap<>();
        info.put("nombre",usuario.getNombre());
        info.put("apellido",usuario.getApellido());
        info.put("email",usuario.getEmail());
        /*
        El objeto OAuth2AccessToken no tiene el metodo setAdditionalInformation, por eso lo casteamos
        a DefaultOAuth2AccessToken que es hijo de OAuth2AccessToken
         */
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}

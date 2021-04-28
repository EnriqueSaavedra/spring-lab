package cl.enriquedev.springbootbackend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private InfoAdicionalToken infoAdicionalToken;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManager") // nombre de metodo, sirve para cuando tienes mas de una implementacion
    private AuthenticationManager authenticationManager;

    /**
     * Da acceso a la autenticación (endpoint oauth token)
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()") //-> da permisos para logear
                .checkTokenAccess("isAuthenticated()"); //-> para checkear el accesos
    }

    /**
     * Se configuran los secret de los clientes que acceden
     * a los servicios (cada persona debe tener sus credenciales)
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*
        significa que el cliente que haga la petición tiene que
        enviar el cliente y secret además del usuario y pass del
        que quiere logear
         */
        clients.inMemory().withClient("angularapp")
                .secret(passwordEncoder.encode("12345"))
                .scopes("read","write") //permite lectura y escritura
                .authorizedGrantTypes("password","refresh_token") // password se usan credenciales (requiere username y contraseña)
                .accessTokenValiditySeconds(3600) //caducidad del token, en segundo
                .refreshTokenValiditySeconds(3600); // lo mismo pero para el refresh
    }

    /**
     * Configura los endpoints porpios de oauth2 para la
     * autenticación
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        /*
        Para agregar el enhacer o mejorador de token, es necesario injectar la clase
        y agregar el access token converter
         */
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken,accessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain); //-> aquí se agrega final mente el enhacer
    }

    /**
     * Administra la persistencia de token
     * @return JwtTokenStore
     */
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter()); // implementa jwt
    }

    /**
     * Administra los claims del token y además las mejoras
     * del token, como agregar datos, también maneja la encriptación
     * y desencriptación de este
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVATE);
        jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLIC);
        return jwtAccessTokenConverter;
    }
}

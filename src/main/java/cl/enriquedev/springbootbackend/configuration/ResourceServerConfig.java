package cl.enriquedev.springbootbackend.configuration;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * configura accesos al sistema (por el lado de oauth)
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*
        Configuración de permisos
        Partir de lo mas especifico a lo mas genericos
         */
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/clientes","/api/clientes/page/**","/api/upload/img/**","/images/**").permitAll() //-> para esa ruta todos puedes entrar
                //.antMatchers(HttpMethod.GET,"/api/clientes/{id}").hasAnyRole("USER","ADMIN")
                //.antMatchers(HttpMethod.POST,"/api/clientes/upload").hasAnyRole("USER","ADMIN")
                //.antMatchers(HttpMethod.POST,"api/clientes").hasRole("ADMIN") // los post de clientes tiene que ser admin
                //.antMatchers("/api/clientes/**").hasRole("ADMIN") //lo que no se especificó será para admin (POST,PUT,DELETE)
                .anyRequest().authenticated() //-> siempre va al final (para el resto de rutas pide autenticar)
                .and() // vuelve al objeto http
                .cors().configurationSource(corsConfigurationSource());
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://example.com"));//dominios donde reside nuestra aplicación
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS")); // option es porque algunos navegadores usan ese para login
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration); // para registrar nuestro cors
        return source;
    }

    /**
     * Crea filtro de cors con prioridad alta
     * para poder acceder a los elementos de oauth
     * @return
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filter;
    }

}

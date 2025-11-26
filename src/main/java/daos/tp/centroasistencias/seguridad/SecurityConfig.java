package daos.tp.centroasistencias.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    // Usuarios en memoria
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.withUsername("user")
                .password("user")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // para el TP sirve
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

        return auth.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(cs -> cs.disable());
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint));
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth

                // LOGIN sin token
                .requestMatchers("/login").permitAll()

                // S01 ASISTIDOS
                .requestMatchers(HttpMethod.GET, "/api/asistidos/**")
                    .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/asistidos/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/asistidos/**")
                    .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/asistidos/**")
                    .hasRole("ADMIN")

                // S02 RACIONES
                .requestMatchers("/api/raciones/**")
                    .hasRole("ADMIN")

                // S03 ASISTENCIAS
                .requestMatchers("/api/asistencias/**")
                    .hasRole("ADMIN")

                // S04 RECETAS
                .requestMatchers("/api/recetas/**")
                    .hasRole("ADMIN")

                // Cualquier otra cosa → permitir
                .anyRequest().permitAll()
        );

        // Agregar filtro antes del de usuario/contraseña
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

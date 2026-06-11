package com.nutrisnap.repository;


import com.nutrisnap.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 -------------------------------------------------------
    Repositorio encargado de la comunicación con la
    base de datos para la entidad Usuario.
 -------------------------------------------------------
 */

@Repository

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email Correo del usuario.
     * @return Usuario encontrado.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si un correo ya existe.
     *
     * @param email Correo a verificar.
     * @return true si existe.
     */
    boolean existsByEmail(String email);
}


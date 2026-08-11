package com.nutrisnap.repository;

import com.nutrisnap.entity.AnalisisComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalisisComidaRepository
        extends JpaRepository<AnalisisComida, Long> {

    List<AnalisisComida> findByUsuarioIdOrderByFechaRegistroDesc(Long usuarioId);

    List<AnalisisComida> findByUsuarioIdAndFechaRegistroBetween(
            Long usuarioId,
            LocalDateTime inicio,
            LocalDateTime fin
    );

    List<AnalisisComida> findByUsuarioIdAndFechaRegistroBetweenOrderByFechaRegistroDesc(
            Long usuarioId,
            LocalDateTime inicio,
            LocalDateTime fin
    );
}
package com.nutrisnap.repository;

import com.nutrisnap.entity.AnalisisComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalisisComidaRepository
        extends JpaRepository<AnalisisComida, Long> {

    List<AnalisisComida> findByUsuarioIdOrderByFechaRegistroDesc(Long usuarioId);
}
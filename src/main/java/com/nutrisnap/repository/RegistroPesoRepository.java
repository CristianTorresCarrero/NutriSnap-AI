package com.nutrisnap.repository;

import com.nutrisnap.entity.RegistroPeso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroPesoRepository extends JpaRepository<RegistroPeso, Long> {

    List<RegistroPeso> findByUsuarioIdOrderByFechaRegistroDesc(Long usuarioId);
}
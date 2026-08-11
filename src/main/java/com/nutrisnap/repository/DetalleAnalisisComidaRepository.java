package com.nutrisnap.repository;

import com.nutrisnap.entity.DetalleAnalisisComida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleAnalisisComidaRepository
        extends JpaRepository<DetalleAnalisisComida, Long> {
}
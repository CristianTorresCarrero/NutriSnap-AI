package com.nutrisnap.repository;

import com.nutrisnap.entity.Alimento;
import com.nutrisnap.enums.CategoriaAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {

    Optional<Alimento> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);

    List<Alimento> findByCategoriaAndActivoTrue(
            CategoriaAlimento categoria
    );

    List<Alimento> findByActivoTrue();

    List<Alimento> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);

}
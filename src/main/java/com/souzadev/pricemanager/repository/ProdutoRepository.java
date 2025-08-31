package com.souzadev.pricemanager.repository;

import com.souzadev.pricemanager.models.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Produto findByNomeIgnoreCase(String nome);

    void deleteByNomeIgnoreCase(String nome);
}


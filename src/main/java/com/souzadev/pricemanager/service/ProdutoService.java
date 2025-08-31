package com.souzadev.pricemanager.service;

import com.souzadev.pricemanager.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    @Transactional
    @Modifying
    public void deletarPorNome(String nome) {
        produtoRepository.deleteByNomeIgnoreCase(nome);
    }
}

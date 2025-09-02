package com.souzadev.pricemanager.controller;


import com.souzadev.pricemanager.models.entities.Produto;
import com.souzadev.pricemanager.service.ExcelService;
import com.souzadev.pricemanager.service.ProdutoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.souzadev.pricemanager.repository.ProdutoRepository;

import java.io.IOException;

@Controller
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;
    private final ExcelService excelService;
    private double margemlucro = 45.0;

    public ProdutoController(ProdutoRepository produtoRepository,
                             ProdutoService produtoService,
                             ExcelService excelService) {
        this.produtoRepository = produtoRepository;
        this.produtoService = produtoService;
        this.excelService = excelService;
    }

    @GetMapping("/produtos/export")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        var produtos = produtoRepository.findAll(); // pega todos os produtos
        excelService.exportarProdutosParaExcel(produtos, response);
    }

    @GetMapping ({"/", "/produtos"})
    public String index(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("margemlucro", margemlucro);
        return "index";
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(@RequestParam String nome, @RequestParam double precoCompra, @RequestParam int quantidade) {
        System.out.println("Tentando adicionar produto: " + nome + ", " + precoCompra + ", " + quantidade);
        Produto p = produtoRepository.findByNomeIgnoreCase(nome);
        if (p != null) {
            p.setCusto(precoCompra);
            p.setQuantidade(quantidade);
            p.calcularPrecoVenda();
        } else {
            p = new Produto(nome, precoCompra, quantidade, margemlucro);
        }
        produtoRepository.save(p);
        return "redirect:/produtos";
    }

    @PostMapping("/limpar")
    public String limparLista() {
        produtoRepository.deleteAll();
        return "redirect:/produtos";
    }

    @PostMapping("/produto/deletarPorNome")
    public String deletarProdutoPorNome(@RequestParam String nome) {
        produtoService.deletarPorNome(nome);
        return "redirect:/produtos";
    }

    @PostMapping("/alterarMargemProduto")
    public String alterarMargemProduto(@RequestParam Long id, @RequestParam double novaMargem) {
        Produto p = produtoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setMargem(novaMargem);
            p.calcularPrecoVenda();
            produtoRepository.save(p);
        }
        return "redirect:/produtos";
    }

}

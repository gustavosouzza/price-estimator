package com.souzadev.pricemanager.controller;


import com.souzadev.pricemanager.models.entities.Produto;
import com.souzadev.pricemanager.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.souzadev.pricemanager.repository.ProdutoRepository;

@Controller
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;


    private double margemlucro = 45.0;

    @GetMapping ({"/", "/produtos"})
    public String index(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("margemlucro", margemlucro);
        return "index";
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(@RequestParam String nome,
                                   @RequestParam double precoCompra,
                                   @RequestParam int quantidade) {
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

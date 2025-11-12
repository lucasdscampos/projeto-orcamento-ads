package com.finan.orcamento.controller;

import com.finan.orcamento.model.ClienteModel;
import com.finan.orcamento.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public String showClientePage(Model model) {
        model.addAttribute("clientes", clienteService.buscarTodos());
        model.addAttribute("clienteModel", new ClienteModel());
        return "clientePage";
    }

    @PostMapping("/clientes")
    public String cadastrarCliente(@ModelAttribute ClienteModel clienteModel) {
        clienteService.cadastrar(clienteModel);
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/{id}/deletar")
    public String deletarCliente(@PathVariable Long id) {
        clienteService.deletar(id);
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/{id}/editar")
    public String showPageEditarCliente(@PathVariable Long id, Model model) {
        ClienteModel cliente = clienteService.buscarPorId(id);
        model.addAttribute("clienteModel", cliente);
        return "clientePage-edit";
    }

    @PostMapping("/clientes/{id}/editar")
    public String editarCliente(@PathVariable Long id, @ModelAttribute ClienteModel clienteModel) {
        clienteService.atualizar(id, clienteModel);
        return "redirect:/clientes";
    }
}
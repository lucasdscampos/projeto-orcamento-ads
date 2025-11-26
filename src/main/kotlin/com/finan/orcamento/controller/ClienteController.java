package com.finan.orcamento.controller;

import com.finan.orcamento.model.ClienteModel;
import com.finan.orcamento.service.ClienteService;
import com.finan.orcamento.service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private OrcamentoService orcamentoService;

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

    @GetMapping("/clientes/relatorio")
    public String gerarRelatorio(
            @RequestParam(value = "clienteId", required = false) Long clienteId,
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            Model model) {

        model.addAttribute("clientes", clienteService.buscarTodos());
        model.addAttribute("clienteModel", new ClienteModel());

        model.addAttribute("relatorioOrcamentos", orcamentoService.buscarRelatorio(clienteId, dataInicio, dataFim));
        
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        model.addAttribute("clienteSelecionadoId", clienteId);

        return "clientePage";
    }
}
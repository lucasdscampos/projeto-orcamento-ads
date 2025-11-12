package com.finan.orcamento.controller;

import com.finan.orcamento.model.ClienteModel;
import com.finan.orcamento.model.OrcamentoModel;
import com.finan.orcamento.model.UsuarioModel;
import com.finan.orcamento.service.ClienteService;
import com.finan.orcamento.service.OrcamentoService;
import com.finan.orcamento.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private OrcamentoService orcamentoService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/usuarios")
    public String getUsuarioPage(Model model) {
        model.addAttribute("usuarioModel", new UsuarioModel());
        model.addAttribute("usuarios", usuarioService.buscarUsuario());
        return "usuarioPage";
    }

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsuarioModel> cadastraUsuario(@ModelAttribute UsuarioModel usuarioModel) {
        return ResponseEntity.ok(usuarioService.cadastrarUsuario(usuarioModel));
    }

    @GetMapping("/usuarios/pesquisa")
    public String listarUsuarios(Model model) {
        List<UsuarioModel> usuarios = usuarioService.buscarUsuario();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioModel", new UsuarioModel());
        return "usuarioPage";
    }

    @GetMapping("/orcamentos")
    public String showOrcamentoPage(Model model) {
        model.addAttribute("orcamentoModel", new OrcamentoModel());
        
        model.addAttribute("orcamentos", orcamentoService.buscarCadastro());
        return "orcamentoPage"; 
    }

    @PostMapping("/orcamentos")
    public String cadastrarOrcamento(@ModelAttribute OrcamentoModel orcamentoModel,
                                     @RequestParam(name = "clienteId", required = false) Long clienteId,
                                     @RequestParam(name = "usuarioId", required = false) Long usuarioId) {
        
        if (clienteId != null) {
            ClienteModel cliente = clienteService.buscarPorId(clienteId);
            orcamentoModel.setCliente(cliente);
        } else if (usuarioId != null) {
            UsuarioModel usuario = usuarioService.buscaId(usuarioId);
            orcamentoModel.setUsuario(usuario);
        }
        
        orcamentoService.cadastrarOrcamento(orcamentoModel);
        
        return "redirect:/orcamentos"; 
    }

    @GetMapping("/clientes/search")
    @ResponseBody
    public List<ClienteModel> searchClientes(@RequestParam("termo") String termo) {
        List<ClienteModel> porNome = clienteService.buscarPorNome(termo);
        if (!porNome.isEmpty()) {
            return porNome;
        }
        return clienteService.buscarPorCpf(termo); 
    }
    
    @GetMapping("/usuarios/search")
    @ResponseBody
    public List<UsuarioModel> searchUsuarios(@RequestParam("nome") String nome) {
        return usuarioService.buscaPorNome(nome); 
    }
}
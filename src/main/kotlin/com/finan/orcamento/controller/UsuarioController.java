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
    public String cadastraUsuario(@ModelAttribute UsuarioModel usuarioModel) {
        usuarioService.cadastrarUsuario(usuarioModel);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/pesquisa")
    public String listarUsuarios(Model model) {
        List<UsuarioModel> usuarios = usuarioService.buscarUsuario();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuarioModel", new UsuarioModel());
        return "usuarioPage";
    }

    @GetMapping("/usuarios/{id}/deletar")
    public String deletarUsuario(@PathVariable Long id) {
        usuarioService.deletaUsuario(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/{id}/editar")
    public String showPageEditarUsuario(@PathVariable Long id, Model model) {
        UsuarioModel usuario = usuarioService.buscaId(id);
        model.addAttribute("usuarioModel", usuario);
        return "usuarioPage-edit";
    }

    @PostMapping("/usuarios/{id}/editar")
    public String editarUsuario(@PathVariable Long id, @ModelAttribute UsuarioModel usuarioModel) {
        usuarioService.atualizaUsuario(usuarioModel, id);
        return "redirect:/usuarios";
    }

    // --- MÉTODOS DE ORÇAMENTO ---

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
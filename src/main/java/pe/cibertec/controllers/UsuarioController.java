package pe.cibertec.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.entites.Usuario;
import pe.cibertec.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Función para registrar un nuevo usuario
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario){
        Usuario usuarioRegistrado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioRegistrado);
    }

    // Función para mostrar todos los usuarios registrados
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
}

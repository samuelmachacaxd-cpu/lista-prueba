package pe.cibertec.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.entites.ItemLista;
import pe.cibertec.entites.ListaCompra;
import pe.cibertec.entites.Usuario;
import pe.cibertec.repository.ItemListaRepository;
import pe.cibertec.repository.ListaCompraRepository;
import pe.cibertec.repository.UsuarioRepository;

import javax.swing.border.Border;
import java.util.List;

@RestController
@RequestMapping("/api/listas")
public class ListaCompraController {
    private final UsuarioRepository usuarioRepository;
    private final ListaCompraRepository listaCompraRepository;
    private final ItemListaRepository itemListaRepository;

    public ListaCompraController(UsuarioRepository usuarioRepository, ListaCompraRepository listaCompraRepository, ItemListaRepository itemListaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.listaCompraRepository = listaCompraRepository;
        this.itemListaRepository = itemListaRepository;
    }

    @PostMapping("/{idUsuario}/crear")
    public ResponseEntity<?> crear(@PathVariable Long idUsuario, @RequestBody ListaCompra listaCompra){
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if(usuario == null){
            return ResponseEntity.badRequest().body("Usuario no encontrado");

        }
        listaCompra.setUsuario(usuario);
        return ResponseEntity.ok(listaCompraRepository.save(listaCompra));
    }

    @PostMapping("/{idLista}/agregar-item")
    public ResponseEntity<?> agregarItem(@PathVariable Long idLista, @RequestBody ItemLista itemLista){
        ListaCompra listaCompra = listaCompraRepository.findById(idLista).orElse(null);
        if(listaCompra == null){
            return ResponseEntity.notFound().build();

        }
        itemLista.setLista(listaCompra);
        return ResponseEntity.ok(itemListaRepository.save(itemLista));
    }

     @PutMapping("/item/{idItem}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long idItem, @RequestParam String estado){
        return itemListaRepository.findById(idItem)
                .map(item ->{
                    item.setEstado(estado);
                    return ResponseEntity.ok(itemListaRepository.save(item));
                }).orElse(ResponseEntity.notFound().build());

     }
    @GetMapping("/usuario/{idUsuario}")
    public List<ListaCompra> historial(@PathVariable Long idUsuario){
        return listaCompraRepository.findByUsuarioId(idUsuario);
    }
    @GetMapping("/{idLista}")
    public ResponseEntity<List<ItemLista>> detalle(@PathVariable Long idLista){
        List<ItemLista> items = itemListaRepository.detalleLista(idLista);
        if(items.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }
    @GetMapping("/{idLista}/items")
    public ResponseEntity<List<ItemLista>> obtenerItemsPorEstado(@PathVariable Long idLista, @RequestParam String estado){
        List<ItemLista> items = itemListaRepository.buscarPorEstado(idLista, estado);
        if (items.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/usuario/{idUsuario}/paginado")
    public Page<ListaCompra> historialPaginado(@PathVariable Long idUsuario, @RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page, size);
        return listaCompraRepository.findByUsuarioPaginado(idUsuario, pageable);
    }
    @GetMapping("//usuario/{idUsuario}/paginado/ordenado")
    public Page<ListaCompra> historiaPaginadoOrdenado( @PathVariable Long idUsuario, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "FechaCreacion")String sortBy,@RequestParam(defaultValue = "desc")String order){

        Sort sort = order.equalsIgnoreCase("asc") ?
        Sort.by(sortBy).ascending() :
        Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return listaCompraRepository.findByUsuarioId(idUsuario, pageable);

    }


}

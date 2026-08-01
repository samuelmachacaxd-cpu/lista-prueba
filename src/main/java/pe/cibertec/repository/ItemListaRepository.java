package pe.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.cibertec.entites.ItemLista;

import java.util.List;

public interface ItemListaRepository extends JpaRepository<ItemLista, Long> {
    @Query("SELECT i FROM ItemLista i where  i.lista.id = :idlista")
    List<ItemLista> detalleLista(@Param("idlista") Long idlista);

    @Query("select i from ItemLista i where i.lista.id =:idlista AND i.estado = :estado  ")
    List<ItemLista> buscarPorEstado(@Param("idlista") Long idlista, @Param("estado")String estado);



}

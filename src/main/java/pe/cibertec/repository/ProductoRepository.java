package pe.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.cibertec.entites.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombre(String nombre);

    @Query("select p from Producto p where lower(p.nombre) LIKE lower(CONCAT('%',:texto, '%')) ")
    List<Producto> buscarPorNombre(@Param("texto")String texto);
}

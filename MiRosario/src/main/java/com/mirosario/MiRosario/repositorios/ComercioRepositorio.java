package com.mirosario.MiRosario.repositorios;

import com.mirosario.MiRosario.entidades.Comercio;
import com.mirosario.MiRosario.entidades.Producto;
import com.mirosario.MiRosario.enums.Rubro;
import com.mirosario.MiRosario.enums.Zona;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComercioRepositorio extends JpaRepository<Comercio, String>{
    
    @Query("SELECT c FROM Comercio c WHERE c.cuit = :cuit")
    public Comercio buscarPorCuit(@Param("cuit") String cuit);
    
    @Query("SELECT c FROM Comercio c WHERE c.nombreUsuario = :nombreUsuario")
    public Comercio buscarPorUsuario(@Param("nombreUsuario") String nombreUsuario);

    //solo con el buscador
    @Query("SELECT c FROM Comercio c WHERE c.nombreComercio LIKE :q OR c.descripcion LIKE :q")
    public List<Comercio> buscarComercio(@Param("q") String q);
    
    //buscador + filtro de rubro
    @Query("SELECT c FROM Comercio c WHERE c.nombreComercio LIKE :q OR c.descripcion LIKE :q AND c.rubro = :rubro")
    public List<Comercio> buscarComercioRubro(@Param("q") String q, @Param("rubro") Rubro rubro);
 
    //buscador + filtro de zona
    @Query("SELECT c FROM Comercio c WHERE c.nombreComercio LIKE :q OR c.descripcion LIKE :q AND c.zona = :zona")
    public List<Comercio> buscarComercioZona(@Param("q") String q, @Param("zona") Zona zona);
    
    //buscador + zona + rubro
    @Query("SELECT c FROM Comercio c WHERE c.nombreComercio LIKE :q OR c.descripcion LIKE :q AND c.zona = :zona AND rubro = :rubro")
    public List<Comercio> buscarComercioRubroZona(@Param("q") String q, @Param("zona") Zona zona, @Param("rubro") Rubro rubro);
   
    //zona + rubro
    @Query("SELECT c FROM Comercio c WHERE c.zona = :zona AND rubro = :rubro")
    public List<Comercio> buscarPorRubroZona(@Param("zona") Zona zona, @Param("rubro") Rubro rubro);
    
    //rubro
    @Query("SELECT c FROM Comercio c WHERE c.rubro = :rubro ")
    public List<Comercio> buscarPorRubro(@Param("rubro") Rubro rubro);

    //zona
    @Query("SELECT c FROM Comercio c WHERE c.zona = :zona ")
    public List<Comercio> buscarPorZona(@Param("zona") Zona zona);
    
    //buscandoProducto
    @Query("select c.producto from Comercio c where c.id = :id and c.producto.id = :idProducto ")
    public Optional<Producto> buscarProductoPorId(@Param("id") String id,@Param("idProducto") String idProducto);
}

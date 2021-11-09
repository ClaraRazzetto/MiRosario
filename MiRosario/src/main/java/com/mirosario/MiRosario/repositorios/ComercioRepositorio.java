package com.mirosario.MiRosario.repositorios;

import com.mirosario.MiRosario.entidades.Comercio;
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

    @Query("SELECT c FROM Comercio c WHERE c.nombreComercio LIKE = :q OR c.descripcion LIKE :q")
    public Comercio buscarComercio(@Param("q") String q);
    
    @Query("SELECT c FROM Comercio c WHERE c.rubro = :rubro ")
    public Comercio buscarPorRubro(@Param("rubro") String rubro);

    @Query("SELECT c FROM Comercio c WHERE c.zona = :zona ")
    public Comercio buscarPorZona(@Param("zona") String zona);
}

package com.mirosario.MiRosario.repositorios;

import com.mirosario.MiRosario.entidades.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComercioRepositorio extends JpaRepository<Comercio, String>{
    
    @Query("SELECT c FROM Comercio c WHERE c.cuit =:cuit")
    public Comercio buscarPorCuit(@Param("cuit") String cuit);
    
    @Query("SELECT c FROM Comercio c WHERE c.nombreUsuario =:nombreUsuario")
    public Comercio buscarPorUsuario(@Param("nombreUsuario") String nombreUsuario);

}

package com.mirosario.MiRosario.repositorios;

import com.mirosario.MiRosario.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>{

    @Query("SELECT c FROM Cliente c WHERE c.nombreUsuario = :nombreUsuario")
    public Cliente buscarNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
    
    @Query("SELECT c FROM Cliente c WHERE c.dni = :dni")
    public Cliente buscarDni(@Param("dni") String dni);
}

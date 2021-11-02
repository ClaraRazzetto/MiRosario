package com.mirosario.MiRosario.repositorios;

import com.mirosario.MiRosario.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
    
    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario =: nombreUsuario")
    public Usuario buscarNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

}

package com.mirosario.MiRosario.repositorios;

import com.mirosario.MiRosario.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, String>{

}

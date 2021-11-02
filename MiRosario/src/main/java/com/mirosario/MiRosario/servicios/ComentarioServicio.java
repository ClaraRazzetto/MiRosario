package com.mirosario.MiRosario.servicios;

import com.mirosario.MiRosario.entidades.Comentario;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.repositorios.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServicio {
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    
    public Comentario guardar(String descripcion) throws ErrorServicio{
    
        if (!(descripcion == null || descripcion.isEmpty())) {
          
                Comentario comentario=new Comentario();
                comentario.setDescripcion(descripcion);
                comentario.s
                        return comentarioRepositorio.save(comentario);
          
        } else{
        throw new ErrorServicio("comentario nulo o vacio");
        }
        
        
        
    }

}

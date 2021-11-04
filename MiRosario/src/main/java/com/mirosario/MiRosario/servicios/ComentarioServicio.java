package com.mirosario.MiRosario.servicios;

import com.mirosario.MiRosario.entidades.Comentario;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.repositorios.ComentarioRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServicio {
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ComercioServicio comercioServicio;
    @Transactional
    public Comentario guardar(String descripcion, String idCliente,String idComercio) throws ErrorServicio{
        validacion(descripcion, idCliente, idComercio);
        
        Comentario comentario=new Comentario();
        comentario.setDescripcion(descripcion);
        comentario.setCliente(clienteServicio.findById(idCliente));
        comentario.setComercio(comercioServicio.findById(idComercio));
        comentario.setAlta(true);
                
        return comentarioRepositorio.save(comentario);
          
    }

    @Transactional
    public Comentario editar(String id, String descripcion,String idCliente, String idComercio) throws ErrorServicio{
         validacion(descripcion, idCliente, idComercio);
        Comentario comentario =findById(id);
        comentario.setDescripcion(descripcion);
        
    return comentarioRepositorio.save(comentario);
    }
    
    
    public void validacion(String descripcion,String idCliente,String idComercio) throws ErrorServicio{

        if (descripcion== null || descripcion.isEmpty()) {
            throw new ErrorServicio("descripcion nula o vacia");
        }
        if (descripcion.length() > 100 ) {
            throw new ErrorServicio("El comentario no puede exceder los 100 caracteres");
        }
         if (idCliente== null || idCliente.isEmpty()) {
            throw new ErrorServicio("cliente nulo o vacio");
        }
          if (idComercio== null || idComercio.isEmpty()) {
            throw new ErrorServicio("comercio nulo o vacio");
        }
    }
             
    public List<Comentario> mostrarListaComentarios(){
        
    return comentarioRepositorio.findAll();
    }
    
    public Comentario findById(String id) throws ErrorServicio{
        
        Optional<Comentario> opcional = comentarioRepositorio.findById(id);
        if (opcional.isPresent()){
            return opcional.get();
        }else{
            throw new ErrorServicio("No se encuentra el comentario solicitado");
        }
    }
    
    @Transactional
    public void DarDeAlta(String id) throws ErrorServicio{
        Comentario comentario = findById(id);
        comentario.setAlta(true);
        comentarioRepositorio.save(comentario);
    }
    
    @Transactional
    public void DarDeBaja(String id) throws ErrorServicio{
        Comentario comentario = findById(id);
        comentario.setAlta(false);
        comentarioRepositorio.save(comentario);
    }
     
}

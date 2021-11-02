package com.mirosario.MiRosario.servicios;

import com.mirosario.MiRosario.entidades.Foto;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.repositorios.FotoRepositorio;
import java.io.IOException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {
    @Autowired
    private FotoRepositorio fotoRepositorio;
@Transactional
    public Foto guardar(MultipartFile archivo) throws Exception{
        if (archivo != null) {
            try {
                 Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println("error"+e.getMessage());
            }
        }
        return null; 
    }
}

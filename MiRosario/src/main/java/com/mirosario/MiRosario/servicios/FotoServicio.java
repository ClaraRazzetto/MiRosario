package com.mirosario.MiRosario.servicios;

import com.mirosario.MiRosario.entidades.Foto;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.repositorios.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional
    public Foto guardar(MultipartFile archivo) throws Exception {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                foto.setAlta(Boolean.TRUE);
                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println("error" + e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public Foto editar(String idFoto, MultipartFile archivo) throws Exception {
        Foto foto = null;
        if (archivo != null && !archivo.isEmpty()) {
            try {
                foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            if (idFoto != null) {
                Optional<Foto> resp = fotoRepositorio.findById(idFoto);
                if (resp.isPresent()) {
                    foto = resp.get();
                }
                return foto;
            }
        }
        //si el archivo falla creo uno en blanco
        return null;

    }

    @Transactional
    public void borrarFoto(String id) {
        fotoRepositorio.deleteById(id);
    }

    public Foto findById(String id) throws ErrorServicio {
        Optional<Foto> opcional = fotoRepositorio.findById(id);
        if (opcional.isPresent()) {
            return opcional.get();
        } else {
            throw new ErrorServicio("No se encuentra la foto solicitada");
        }
    }

    @Transactional
    public void darDeAlta(String id) throws ErrorServicio {
        Foto foto = findById(id);
        foto.setAlta(true);
        fotoRepositorio.save(foto);
    }

    @Transactional
    public void darDeBaja(String id) throws ErrorServicio {
        Foto foto = findById(id);
        foto.setAlta(false);
        fotoRepositorio.save(foto);
    }
}

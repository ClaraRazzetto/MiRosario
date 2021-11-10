package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Comentario;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ComentarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comentario")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @PostMapping("/guardar")
    public String agregarComentarioPost(ModelMap modelo, @RequestParam String descripcion, HttpSession sesion, @RequestParam String idComercio) {
        try {
            
            comentarioServicio.guardar(descripcion, sesion.getId(), idComercio);
        
            return "redirect:/vista-cliente.html";
        
        } catch (Exception ex) {
            
            System.out.println("error" + ex.getMessage());
            modelo.addAttribute("descripcion", descripcion);
            modelo.addAttribute("idCliente", sesion.getId());
            modelo.addAttribute("idComercio", idComercio);
           
            return "formulario-comentario.html";
        }
    }
    
    @PostMapping("/editar")
    public String editarComentarioPost(ModelMap modelo, @RequestParam String descripcion, @RequestParam String idComentario, @RequestParam String idComercio, HttpSession sesion) {
        try {
            Comentario comentario = comentarioServicio.findById(idComentario);
            if (!comentario.getCliente().getId().equals(sesion.getId()) || !comentario.getComercio().getId().equals(idComercio)) {
                modelo.put("error", "No tiene permisos parar relizar esta acción");
            }
            comentario = comentarioServicio.editar(idComentario,descripcion,sesion.getId(),idComercio);
        } catch (ErrorServicio error) {
           modelo.put("error", error);
        }
        return "redirect:/vista-cliente.html";
    }

    @GetMapping("/baja")
    public String darBajaComentario(ModelMap modelo, @RequestParam String idComentario, HttpSession sesion, @RequestParam String idComercio) throws ErrorServicio {
        try {
            Comentario comentario = comentarioServicio.findById(idComentario);
            if (!comentario.getCliente().getId().equals(sesion.getId()) || !comentario.getComercio().getId().equals(idComercio)) {
                modelo.put("error", "No tiene permisos parar relizar esta acción");
            }
            comentarioServicio.darDeBaja(idComentario);
        } catch (ErrorServicio error) {
           modelo.put("error", error);
        }
        return "redirect:/vista-cliente.html";
    }

}

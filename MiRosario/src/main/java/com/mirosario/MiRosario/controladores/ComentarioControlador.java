package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Cliente;
import com.mirosario.MiRosario.entidades.Comentario;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ComentarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/comentario")
@PreAuthorize("hasAnyRole('ROLE_CLIENTE')")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @PostMapping("/guardar")
    public String agregarComentarioPost(ModelMap modelo, @RequestParam String descripcion, HttpSession sesion, @RequestParam String idComercio, RedirectAttributes redirect) {
        Cliente cliente = null;
        try {
            cliente = (Cliente) sesion.getAttribute("usuariosesion");

            if (cliente == null) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/";
            }

            comentarioServicio.guardar(descripcion, cliente.getId(), idComercio);
            redirect.addFlashAttribute("exito", "El comentario se ha guardado con exito");
            
            sesion.setAttribute("usuarioSesion", cliente);

        } catch (Exception ex) {

            redirect.addFlashAttribute("error", ex.getMessage());
            redirect.addFlashAttribute("descripcion", descripcion);
            redirect.addFlashAttribute("idCliente", cliente.getId());
            redirect.addFlashAttribute("idComercio", idComercio);

        }

        return "redirect:/perfil-comercio?id=" + idComercio;
    }

    @PostMapping("/editar")
    public String editarComentarioPost(ModelMap modelo, @RequestParam String descripcion, @RequestParam String idComentario, @RequestParam String idComercio, HttpSession sesion, RedirectAttributes redirect) {
        try {
            Cliente cliente = (Cliente) sesion.getAttribute("usuariosesion");

            if (cliente == null) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/";
            }

            Comentario comentario = comentarioServicio.findById(idComentario);
            
            if (!comentario.getCliente().getId().equals(cliente.getId()) || !comentario.getComercio().getId().equals(idComercio)) {
                modelo.put("error", "No tiene permisos parar relizar esta acción");
            }
            
            comentario = comentarioServicio.editar(idComentario, descripcion, cliente.getId(), idComercio);
            
            redirect.addFlashAttribute("exito", "El comentario se ha actualizado con exito");
        
        } catch (ErrorServicio error) {
            modelo.put("error", error);
        }
        
        return "redirect:/perfil-comercio?id=" + idComercio;
    }

    @PostMapping("/baja")
    public String darBajaComentario(ModelMap modelo, @RequestParam String idComentario, HttpSession sesion, @RequestParam String idComercio, RedirectAttributes redirect) {
        try {
            Cliente cliente = (Cliente) sesion.getAttribute("usuariosesion");

            if (cliente == null) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/";
            }

            Comentario comentario = comentarioServicio.findById(idComentario);
            
            if (!comentario.getCliente().getId().equals(cliente.getId()) || !comentario.getComercio().getId().equals(idComercio)) {
                modelo.put("error", "No tiene permisos parar relizar esta acción");
            }
            
            comentarioServicio.darDeBaja(idComentario);
            
            sesion.setAttribute("usuarioSesion", cliente);
 
            redirect.addFlashAttribute("exito", "El comentario se ha eliminado con exito");
        
        } catch (ErrorServicio error) {
            
            modelo.put("error", error);
        }
        return "redirect:/perfil-comercio?id=" + idComercio;
    }

}

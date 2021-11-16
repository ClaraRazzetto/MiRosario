package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Cliente;
import com.mirosario.MiRosario.entidades.Comercio;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ComentarioServicio;
import com.mirosario.MiRosario.servicios.ComercioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class MainControlador {

    @Autowired
    private ComercioServicio comercioServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("")
    public String inicio() {
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_COMERCIO','ROLE_CLIENTE')")
    @GetMapping("/perfil-comercio")
    public String perfilComercio(ModelMap modelo, @RequestParam(required = false) String id, HttpSession sesion, RedirectAttributes redirect) {

        Comercio comercio = null;
        try {
            if (sesion.getAttribute("usuariosesion") instanceof Cliente) {

                comercio = comercioServicio.findById(id);
                modelo.addAttribute("comercio", comercio);

            } else {
                comercio = (Comercio) sesion.getAttribute("usuariosesion");

                if (comercio == null) {
                    redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos necesarios para realizar esa accion");
                    return "redirect:/";
                }
                modelo.addAttribute("comercio", comercio);
                modelo.put("productos", comercioServicio.listaProducto(comercio.getId()));
            }
        } catch (ErrorServicio error) {
            redirect.addFlashAttribute("error", error);
            return "redirect:/vista-cliente";
        }

        modelo.put("comentarios", comentarioServicio.listaComentariosPorComercio(comercio.getId()));

        return "perfil-comercio.html";
    }

}

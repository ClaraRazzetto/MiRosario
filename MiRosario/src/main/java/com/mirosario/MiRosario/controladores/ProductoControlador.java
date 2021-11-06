package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Comercio;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ProductoServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/guardar")
    public String agregarProducto() {

        return "formulario-producto.html";
    }

    @PostMapping("/guardar")
    public String agregarProductoPost(ModelMap modelo, @RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam Double precio, @RequestParam String descripcion) {
        try {
            productoServicio.guardar(nombre, precio, descripcion, archivo);
            return "perfil-comercio.html";
        } catch (Exception ex) {
            System.out.println("error" + ex.getMessage());
            modelo.addAttribute("nombre", nombre);
            modelo.addAttribute("precio", precio);
            modelo.addAttribute("descripcion", descripcion);
            return "formulario-producto.html";
        }
    }

    @GetMapping("/editar")
    public String editarProducto(HttpSession httpsession, ModelMap modelo, @RequestParam String id, RedirectAttributes redirect) {
        try {
            Comercio comercio = (Comercio) httpsession.getAttribute("usuarioSesion");
            if (comercio == null || !comercio.getId().equals(id)) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/inicio";
            }
            modelo.addAttribute("producto", productoServicio.findById(id));

        } catch (ErrorServicio ex) {
            System.out.println("error" + ex.getMessage());

        }
        return "editar-producto.html";
    }

    @PostMapping("/editar")
    public String editarProductoPost(HttpSession httpsession, Model modelo, @RequestParam String id, @RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam Double precio, @RequestParam String descripcion, RedirectAttributes redirect) {
        try {
            Comercio comercio = (Comercio) httpsession.getAttribute("usuarioSesion");
            if (comercio == null || !comercio.getId().equals(id)) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/inicio";
            }
            modelo.addAttribute("producto", productoServicio.findById(id));
            //por terminar//
        } catch (ErrorServicio ex) {
            System.out.println("error" + ex.getMessage());

        }
        return "perfil-comercio.html";
    }

    @GetMapping("/baja")
    public String darBajaProducto(@RequestParam(required = true) String id) throws ErrorServicio {
        productoServicio.darDeBaja(id);
        return "perfil-comercio.html";
    }

}

package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Comercio;
import com.mirosario.MiRosario.entidades.Producto;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ComercioServicio;
import com.mirosario.MiRosario.servicios.ProductoServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('ROLE_COMERCIO')")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private ComercioServicio comercioServicio;

    @GetMapping("/guardar")
    public String agregarProducto() {
        return "formulario-producto.html";
    }

    @PostMapping("/guardar")
    public String agregarProductoPost(ModelMap modelo, HttpSession sesion, @RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam Double precio, @RequestParam String descripcion, RedirectAttributes redirect) {
        try {

            Comercio comercio = (Comercio) sesion.getAttribute("usuariosesion");

            if (comercio == null) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos necesarios para realizar esa accion");
                return "redirect:/";
            }

            comercioServicio.guardarProducto(comercio.getId(), archivo, nombre, precio, descripcion);

            sesion.setAttribute("usuariosesion", comercio);

            return "redirect:/perfil-comercio.html";

        } catch (Exception ex) {

            System.out.println("error" + ex.getMessage());
            modelo.addAttribute("nombre", nombre);
            modelo.addAttribute("precio", precio);
            modelo.addAttribute("descripcion", descripcion);

            return "formulario-producto.html";
        }
    }

    @GetMapping("/editar")
    public String editarProducto(HttpSession sesion, @RequestParam String idComercio, ModelMap modelo, @RequestParam String id, RedirectAttributes redirect) {

        try {

            Comercio comercio = (Comercio) sesion.getAttribute("usuariosesion");
            Producto producto = productoServicio.findById(id);

            if (comercio == null || !comercio.getId().equals(idComercio) || !comercio.getProducto().contains(producto)) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos necesarios para realizar esa accion");
                return "redirect:/";
            }
            
            modelo.put("producto", producto);
            modelo.put("idComercio", comercio.getId());
            
            return "editar-producto.html";
        } catch (ErrorServicio error) {

            modelo.put("error", error);

        }

        return "editar-producto.html";
    }

    @PostMapping("/editar")
    public String editarProductoPost(HttpSession sesion, Model modelo, @RequestParam String idComercio, @RequestParam String id, @RequestParam MultipartFile archivo, @RequestParam String nombre, @RequestParam Double precio, @RequestParam String descripcion, RedirectAttributes redirect) throws Exception {

        Producto producto = null;

        try {

            Comercio comercio = (Comercio) sesion.getAttribute("usuariosesion");
            producto = productoServicio.findById(id);

            if (comercio == null || !comercio.getId().equals(idComercio) || !comercio.getProducto().contains(producto)) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos necesarios para realizar esa accion");
                return "redirect:/";
            }

            productoServicio.editar(id, nombre, precio, descripcion, archivo);

            sesion.setAttribute("usuariosesion", comercio);

        } catch (ErrorServicio error) {

            System.out.println("error" + error.getMessage());
            modelo.addAttribute("producto", producto);
            return "redirect:/producto/editar";

        }
        return "redirect:/perfil-comercio";
    }

    @GetMapping("/baja")
    public String darBajaProducto() {

        return "eliminar.html";
    }

    @PostMapping("/baja")
    public String darBajaProductoPost(ModelMap modelo, HttpSession sesion, @RequestParam String idComercio, @RequestParam String id, RedirectAttributes redirect) throws ErrorServicio {
        try {
            Comercio comercio = (Comercio) sesion.getAttribute("usuariosesion");

            if (comercio == null || !comercio.getId().equals(idComercio) || !comercio.getProducto().contains(productoServicio.findById(id))) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos necesarios para realizar esa accion");
                return "redirect:/";
            }

            productoServicio.darDeBaja(id);
            
            sesion.setAttribute("usuariosesion", comercio);

        } catch (ErrorServicio e) {

            modelo.addAttribute("error", e.getMessage());
            return "redirect:/producto/editar";

        }

        return "redirect:/logout";
    }

}

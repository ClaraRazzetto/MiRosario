package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Comercio;
import com.mirosario.MiRosario.enums.Rubro;
import com.mirosario.MiRosario.enums.Zona;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ComercioServicio;
import com.mirosario.MiRosario.servicios.RubroServicio;
import com.mirosario.MiRosario.servicios.ZonaServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comercio")
public class ComercioControlador {

    @Autowired
    private ComercioServicio comercioServicio;

    @Autowired
    private ZonaServicio zonaServicio;

    @Autowired
    private RubroServicio rubroServicio;

    @GetMapping("/registro") 
    public String registro(ModelMap modelo) {

        modelo.put("zonas", zonaServicio.listarZonas());
        modelo.put("rubros", rubroServicio.listarRubros());

        return "formulario-comercio.html";
    }

    @PostMapping("/registro")
    public String registroPost(RedirectAttributes redirect, ModelMap modelo, MultipartFile archivo, @RequestParam String nombreUsuario, @RequestParam String password, @RequestParam String password2, @RequestParam String cuit, @RequestParam String nombreComercio, @RequestParam Rubro rubro, @RequestParam String direccion, @RequestParam Zona zona, @RequestParam String descripcion, @RequestParam String telefono, @RequestParam String mail) throws Exception {

        
    try {
        comercioServicio.guardar( archivo, nombreUsuario, password, password2, cuit, nombreComercio, rubro, direccion, zona, descripcion, telefono, mail);
        redirect.addFlashAttribute("exito", "Te registraste con exito!");
        
    }   
    catch (ErrorServicio error){
        
        modelo.put("error", error.getMessage());
        
        modelo.put("nombreUsuario", nombreUsuario);
        modelo.put("cuit", cuit);
        modelo.put("nombreComercio", nombreComercio);
        modelo.put("direccion", direccion);
        modelo.put("zonas", zonaServicio.listarZonas());
        modelo.put("rubros", rubroServicio.listarRubros());
        modelo.put("telefono", telefono);
        modelo.put("mail", mail);
        modelo.put("descripcion", descripcion);
            
        return "formulario-comercio.html";
        }
        return "redirect:/";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_COMERCIO')")
    @GetMapping("/editar")
    public String editar(ModelMap modelo, HttpSession sesion, @RequestParam String id, RedirectAttributes redirect){
       
        modelo.put("zonas", zonaServicio.listarZonas());
        modelo.put("rubros", rubroServicio.listarRubros());
        
        try {
            
            Comercio comercio = (Comercio) sesion.getAttribute("usuariosesion");
            
            if(comercio == null || !comercio.getId().equals(id)){
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos necesarios para realizar esa accion");
                
                return "redirect:/";
            }
            
            modelo.put("comercio", comercioServicio.findById(id)); 
               
        } catch (ErrorServicio error) {
            
            modelo.put("error", error.getMessage());
        }
        return "editar-comercio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_COMERCIO')")
    @PostMapping("/editar")
    public String editarPost( HttpSession sesion, @RequestParam String id, RedirectAttributes redirect, ModelMap modelo, MultipartFile archivo, @RequestParam String nombreUsuario, @RequestParam String password, @RequestParam String password2, @RequestParam String cuit, @RequestParam String nombreComercio, @RequestParam Rubro rubro, @RequestParam String direccion, @RequestParam Zona zona, @RequestParam String descripcion, @RequestParam String telefono, @RequestParam String mail) throws Exception {
                
        Comercio comercio = null;
        
        try{
            
        comercio = (Comercio) sesion.getAttribute("usuariosesion");
       
        if(comercio == null || !comercio.getId().equals(id)){
            redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos necesarios para realizar esa accion");
            return "redirect:/";
        }
                
        comercio = comercioServicio.editar(id, archivo, nombreUsuario, password, password2, cuit, nombreComercio, rubro, direccion, zona, descripcion, telefono, mail);
        
        sesion.setAttribute("usuariosesion", comercio);
        redirect.addFlashAttribute("exito", "Los cambios se guardaron con éxito");        
        }catch(ErrorServicio error){
            modelo.put("error", error.getMessage());
            modelo.put("comercio", comercio);   
        }
        
        return "redirect:/perfil-comercio?id=" + id;
    }

    @PreAuthorize("hasAnyRole('ROLE_COMERCIO')")
    @PostMapping("/baja")
    public String darDeBajaPost(HttpSession sesion, ModelMap modelo, @RequestParam String id, RedirectAttributes redirect) throws Exception {
        Comercio comercio = null;
        
        try{
            comercio = (Comercio) sesion.getAttribute("usuariosesion");
            if(comercio == null || !comercio.getId().equals(id)){
                redirect.addFlashAttribute("error", "tu usuario no tiene permiso para realizar esta acción");
                return "redirect:/";
            }
            comercioServicio.darDeBaja(comercio.getId());
            redirect.addFlashAttribute("exito", "Su usuario se ha eliminado correctamente");

        }catch (ErrorServicio error){
            modelo.put("error", error.getMessage());
        }

        return "redirect:/logout";
    }
    
}

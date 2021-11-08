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

    @GetMapping("/regritro")
    public String registro(ModelMap modelo) {

        modelo.put("zona", zonaServicio.listarZonas());
        modelo.put("rubro", rubroServicio.listarRubros());

        return "formulario-comercio.html";
    }

    @PostMapping("/registro")
    public String registroPost(ModelMap modelo, MultipartFile archivo, @RequestParam String nombreUsuario, @RequestParam String password, @RequestParam String password2, @RequestParam String cuit, @RequestParam String nombreComercio, @RequestParam Rubro rubro, @RequestParam String direccion, @RequestParam Zona zona, @RequestParam String descripcion, @RequestParam String telefono, @RequestParam String mail) throws Exception {

        
    try {
        comercioServicio.guardar( archivo, nombreUsuario, password, password2, cuit, nombreComercio, rubro, direccion, zona, descripcion, telefono, mail);
        
        return "vista-comercio.html";
    }   
    catch (ErrorServicio error){
        
        modelo.put("error", error.getMessage());
        
        modelo.put("nombreUsuario", nombreUsuario);
        modelo.put("cuit", cuit);
        modelo.put("nombreComercio", nombreComercio);
        modelo.put("direccion", direccion);
        modelo.put("zona", zonaServicio.listarZonas());
        modelo.put("rubro", rubroServicio.listarRubros());
        modelo.put("telefono", telefono);
        modelo.put("mail", mail);
        modelo.put("descripcion", descripcion);
            
        return "perfil-comercio.html";

        }
    }
    
    @GetMapping("/editar")
    public String editar(ModelMap modelo, HttpSession httpssesion, @RequestParam String id, RedirectAttributes redirect){
        Comercio comercio = (Comercio) httpssesion.getAttribute("usuarioSesion");
        
        if(comercio == null || !comercio.getId().equals(id)){
            redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos necesarios para realizar esa accion");
            
            return "redirect:/inicio";
        }
       

        return "editar-comercio.html";
    }

    @PostMapping("/editar")
    public String editarPost() {
        return "perfil-comercio.html";
    }

    @GetMapping("/baja")
    public String darDeBaja() {
        return "eliminar.html";
    }

    @PostMapping("/baja")
    public String darDeBajaPost() {
        return "inicio.html";
    }

}

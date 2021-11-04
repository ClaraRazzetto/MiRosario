package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Cliente;
import com.mirosario.MiRosario.enums.Zona;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ClienteServicio;
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
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    public ClienteServicio clienteServicio;
    
    @Autowired
    public ZonaServicio zonaServicio;

    @GetMapping("/registro")
    public String registro(ModelMap modelo){
        
        modelo.put("zonas", zonaServicio.listarZonas()); 
        
        return "formulario-cliente.html";
    }
    
    @PostMapping("/registro")
    public String registroPost(ModelMap modelo, MultipartFile archivo, @RequestParam String nombreUsuario,@RequestParam String password, @RequestParam String password2,@RequestParam String dni,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String direccion,@RequestParam String telefono,@RequestParam String mail,@RequestParam Zona zona) throws Exception{
        
        try {
            clienteServicio.guardar(nombreUsuario, password, password2, dni, nombre, apellido, direccion, telefono, mail, zona, archivo);
            
            return "vista-cliente.html";
            
        } catch (ErrorServicio error) {
            
            modelo.put("error", error.getMessage());
            
            modelo.put("nombreUsuario", nombreUsuario);
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("direccion", direccion);
            modelo.put("telefono", telefono);
            modelo.put("mail", mail);
            modelo.put("zonas", zonaServicio.listarZonas());
           
            return "formulario-cliente.html";
        }
    }
    
    @GetMapping("/editar")
    public String editar(ModelMap modelo, HttpSession httpsession, @RequestParam String id, RedirectAttributes redirect){
        Cliente cliente = (Cliente) httpsession.getAttribute("usuarioSesion");
        if (cliente == null || !cliente.getId().equals(id)) {
            redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
            return "redirect:/inicio";
        }
        return "editar-cliente.html";
    }
    
    @PostMapping("/editar")
    public String editarPost(){
        return "vista-cliente.html";
    }
    
    @GetMapping("/baja")
    public String darDeBaja(){
        return "eliminar.html";
    }
    
    @PostMapping("/baja")
    public String darDeBajaPost(){
        return "inicio.html";
    }
    
}

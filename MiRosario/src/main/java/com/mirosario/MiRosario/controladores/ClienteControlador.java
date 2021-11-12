package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Cliente;
import com.mirosario.MiRosario.enums.Rubro;
import com.mirosario.MiRosario.enums.Zona;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ClienteServicio;
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
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ComercioServicio comercioServicio;
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @Autowired
    private ZonaServicio zonaServicio;
    
    @Autowired
    private RubroServicio rubroServicio;
    
//      @GetMapping("/vista-cliente")
//    public String vistaCliente(){
//     return "vista-cliente.html";
//}
    
    

    @GetMapping("/vista-cliente")
    public String vistaCliente(ModelMap modelo, @RequestParam(required = false) String q, @RequestParam(required = false) Rubro rubro,@RequestParam(required = false) Zona zona){
        
        modelo.put("zonas", zonaServicio.listarZonas());
        modelo.put("rubros", rubroServicio.listarRubros());
        
        modelo.put("comercios", comercioServicio.listar(q, rubro, zona));
        
        return "vista-cliente.html";
    }
    
    @GetMapping("/registro")
    public String registro(ModelMap modelo){
        
        modelo.put("zonas", zonaServicio.listarZonas()); 
        
        return "formulario-cliente.html";
    }
    
    
    @PostMapping("/registro")
    public String registroPost(ModelMap modelo, MultipartFile archivo, @RequestParam String nombreUsuario,@RequestParam String password, @RequestParam String password2,@RequestParam String dni,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String direccion,@RequestParam String telefono,@RequestParam String mail,@RequestParam Zona zona, RedirectAttributes redirect) throws Exception{
        
        try {
            clienteServicio.guardar(nombreUsuario, password, password2, dni, nombre, apellido, direccion, telefono, mail, zona, archivo);
            redirect.addFlashAttribute("exito", "Te registraste con exito!");
//            return "redirect:/vista-cliente";
return "redirect:/";
            
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
    public String editar(ModelMap modelo, HttpSession sesion, @RequestParam String id, RedirectAttributes redirect){
       
        modelo.put("zonas", zonaServicio.listarZonas());
        try {
            Cliente cliente = (Cliente) sesion.getAttribute("usuariosesion");
            if (cliente == null || !cliente.getId().equals(id)) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/";
            }
            modelo.addAttribute("cliente", clienteServicio.findById(id));
        } catch (ErrorServicio error) {
            modelo.put("error", error.getMessage());
        }
        return "editar-cliente.html";
    }
    
    @PostMapping("/editar")
    public String editarPost(ModelMap modelo, MultipartFile archivo,@RequestParam String id, @RequestParam String nombreUsuario,@RequestParam String password, @RequestParam String password2,@RequestParam String dni,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String direccion,@RequestParam String telefono,@RequestParam String mail,@RequestParam Zona zona, RedirectAttributes redirect,HttpSession sesion) throws Exception{
        Cliente cliente = null;
        try {
            cliente = (Cliente) sesion.getAttribute("usuariosesion");
            if (cliente == null || !cliente.getId().equals(id)) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/";
            }
            cliente = clienteServicio.editar(id, nombreUsuario, password, password2, dni, nombre, apellido, direccion, telefono, mail, zona, archivo);
            sesion.setAttribute("usuarioSesion", cliente); 
            redirect.addFlashAttribute("exito","Los cambios se guardaron con exito");
        } catch (ErrorServicio error) {
            modelo.put("error", error.getMessage());
            modelo.put("cliente", cliente);
            return "editar-cliente.html";
        }
        return "redirect:/vista-cliente.html";
    }
        
    @PostMapping("/baja")
    public String darDeBajaPost(ModelMap modelo,HttpSession sesion, @RequestParam String id, RedirectAttributes redirect){
        Cliente cliente = null;
        try {
            cliente = (Cliente) sesion.getAttribute("usuariosesion");
            if (cliente == null || !cliente.getId().equals(id)) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/";
            }
            clienteServicio.darDeBaja(cliente.getId());
            redirect.addFlashAttribute("exito", "Tu usuario se ha eliminado correctamente");
        } catch (ErrorServicio error){
            modelo.put("error", error.getMessage()); 
        }
        return "redirect:/logout";
    }
    
    @GetMapping("/comercios-favoritos")
    public String listaComercios(ModelMap modelo,HttpSession sesion,@RequestParam String id,RedirectAttributes redirect){
        Cliente cliente = null;
        try {
            cliente = (Cliente) sesion.getAttribute("usuariosesion");
            if (cliente == null || !cliente.getId().equals(id)) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/";
            }
            modelo.put("comerciosFavoritos",clienteServicio.listaComercios(cliente.getId()));
        } catch (ErrorServicio error) {
            modelo.put("error", error);
        }
        return "comercios-favoritos.html";
    }
    
    @PostMapping("/comercio-favorito")
    public String guardarComercio(ModelMap modelo,HttpSession sesion,@RequestParam String id,@RequestParam String idComercio, RedirectAttributes redirect){
       Cliente cliente = null;
        try {
            cliente = (Cliente) sesion.getAttribute("usuariosesion");
            if (cliente == null || !cliente.getId().equals(id)) {
                redirect.addFlashAttribute("error", "Tu usuario no tiene los permisos para realizar esta acción");
                return "redirect:/";
            }
            clienteServicio.guardarComercios(cliente.getId(), idComercio);
            sesion.setAttribute("usuarioSesion", cliente);  
            redirect.addFlashAttribute("exito", "El comercio se ha guardado en tu lista de comercios favoritos");
        } catch (ErrorServicio error) {
            redirect.addFlashAttribute("error", error);
        }
        //arreglar
        return "redirect:/perfil-comercio";
    }
    
}

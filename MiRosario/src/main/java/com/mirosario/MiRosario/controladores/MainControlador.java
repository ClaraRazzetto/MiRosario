package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Cliente;
import com.mirosario.MiRosario.entidades.Comercio;
import com.mirosario.MiRosario.enums.Rubro;
import com.mirosario.MiRosario.enums.Zona;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ComentarioServicio;
import com.mirosario.MiRosario.servicios.ComercioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String inicio(){
        return "inicio.html";
    }
//     @GetMapping("/perfil-comercio")
//     public String perfilComercio(){
//         return "perfil-comercio";
//                
//             
//     }
             
    
    @GetMapping("/perfil-comercio")
    public String perfilComercio(ModelMap modelo, @RequestParam(required = false) String id, HttpSession sesion, RedirectAttributes redirect){
        
        Comercio comercio = null;
 
        if (sesion.getAttribute("usuariosesion") instanceof Cliente) {
            try {
                    comercio = comercioServicio.findById(id);
                    modelo.addAttribute("comercio", comercio);
            } catch (ErrorServicio error) {
                redirect.addFlashAttribute("error", error);
                return "redirect:/vista-cliente";
            }
            
        }else{
            comercio = (Comercio) sesion.getAttribute("usuariosesion");
            modelo.addAttribute("comercio", comercio);
        }
        
        //modelo.put("comentarios", comentarioServicio.listaComentariosPorComercio(comercio.getId()));
        
        return "perfil-comercio.html";
    }
    
}

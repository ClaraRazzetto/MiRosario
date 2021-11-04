package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("")
    public String login(ModelMap model, @RequestParam(required = false) String error, @RequestParam(required = false) String logout, @RequestParam(required = false) String nombreUsuario){
        if (error != null) {
            model.put("error", "El usuario o la contraseña son incorrectos");
        }
        if(logout != null){
            model.put("exito", "Has cerrado sesión correctamente");
        }
        if(nombreUsuario != null){
            model.put("nombreUsuario", nombreUsuario);
        }
        return "login.html";
    }

}

package com.mirosario.MiRosario.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainControlador {

    @GetMapping("")
    public String inicio(){
        return "inicio.html";
    }
    
    @GetMapping("/quienes-somos")
    public String quienesSomos(){
        return "quienes-somos.html";
    }
    
    @GetMapping("/perfil-comercio")
    public String perfilComercio(){
        return "perfil-comercio.html";
    }
}

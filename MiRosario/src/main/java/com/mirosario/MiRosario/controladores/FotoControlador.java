package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.excepciones.ErrorServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/foto")
public class FotoControlador {
      @GetMapping("/guardar")
      public String agregarComentario() {
          
            return "formulario-comentario.html";
    }
//      @PostMapping("/guardar")
//      public String agregarComentarioPost(ModelMap modelo,@RequestParam String descripcion,@RequestParam String idCliente,@RequestParam String idComercio){
//        try {
//            comentarioServicio.guardar(descripcion, idCliente, idComercio);
//            return "vista-cliente.html";
//        } catch (Exception ex) {
//            System.out.println("error"+ex.getMessage());
//            modelo.addAttribute("descripcion",descripcion);
//            modelo.addAttribute("idCliente",idCliente);
//            modelo.addAttribute("idComercio",idComercio);
//            return "formulario-comentario.html";
//        }
//      }
//      @GetMapping("/editar")
//      public String editarComentario(ModelMap modelo,@RequestParam String id) {
//       
//            return "editar-comentario.html";
//    }
//      @PostMapping("/editar")
//      public String editarComentarioPost(@RequestParam MultipartFile archivo, @RequestParam String nombre,@RequestParam Double precio,@RequestParam String descripcion){
//           return "vista-cliente.html";
//      }
//    
//        @GetMapping("/baja")
//    public String darBajaComentario(@RequestParam(required = true) String id) throws ErrorServicio{
//        comentarioServicio.darDeBaja(id);
//        return "vista-cliente.html";
//    }

}

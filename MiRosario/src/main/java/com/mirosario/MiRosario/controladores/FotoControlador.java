package com.mirosario.MiRosario.controladores;

import com.mirosario.MiRosario.entidades.Cliente;
import com.mirosario.MiRosario.entidades.Comercio;
import com.mirosario.MiRosario.entidades.Producto;

import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.servicios.ClienteServicio;
import com.mirosario.MiRosario.servicios.ComercioServicio;
import com.mirosario.MiRosario.servicios.ProductoServicio;
import com.mirosario.MiRosario.servicios.UsuarioServicio;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private ComercioServicio comercioServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/cliente")
    public ResponseEntity<byte[]> fotoCliente(@RequestParam String id, HttpSession sesion) {
        try {
//            Cliente cliente = (Cliente) sesion.getAttribute("usuariosesion");;
                     Cliente cliente = clienteServicio.findById(id);
            if (cliente.getFoto() == null) {
                throw new ErrorServicio("El usuario no posee una foto asignada");
            }

            byte[] foto = cliente.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);

        } catch (ErrorServicio ex) {
            System.out.println("error" + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
      @GetMapping("/comercio")
    public ResponseEntity<byte[]> fotoComercio(@RequestParam String id, HttpSession sesion) {
        try {
//            Cliente cliente = (Cliente) sesion.getAttribute("usuariosesion");;
                     Comercio comercio = comercioServicio.findById(id);
            if (comercio.getFoto() == null) {
                throw new ErrorServicio("El usuario no posee una foto asignada");
            }

            byte[] foto = comercio.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);

        } catch (ErrorServicio ex) {
            System.out.println("error" + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
      @GetMapping("/producto")
    public ResponseEntity<byte[]> fotoProducto(@RequestParam String id, HttpSession sesion) {
        try {
//            Cliente cliente = (Cliente) sesion.getAttribute("usuariosesion");;
                     Producto producto = productoServicio.findById(id);
            if (producto.getFoto() == null) {
                throw new ErrorServicio("El usuario no posee una foto asignada");
            }

            byte[] foto = producto.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);

        } catch (ErrorServicio ex) {
            System.out.println("error" + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

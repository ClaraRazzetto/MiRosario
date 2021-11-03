package com.mirosario.MiRosario.servicios;

import com.mirosario.MiRosario.entidades.Cliente;
import com.mirosario.MiRosario.entidades.Comercio;
import com.mirosario.MiRosario.entidades.Foto;
import com.mirosario.MiRosario.enums.Rol;
import com.mirosario.MiRosario.enums.Zona;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.repositorios.ClienteRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private FotoServicio fotoServicio;
    
    @Autowired
    private ComercioServicio comercioServicio;
    
    @Autowired
    private ComentarioServicio comentarioServicio;

    @Transactional
    public Cliente guardar(String nombreUsuario, String password, String password2, String dni, String nombre, String apellido, String direccion, String telefono, String mail, Zona zona, MultipartFile archivo) throws ErrorServicio, Exception {

        validar(nombreUsuario, password, password2, dni, nombre, apellido, direccion, telefono, mail, zona);

        if (clienteRepositorio.buscarNombreUsuario(nombreUsuario) != null) {
            throw new ErrorServicio("Ya existe un usuario con el nombre de usuario ingresado");
        }

        if (clienteRepositorio.buscarDni(dni) != null) {
            throw new ErrorServicio("Ya existe un usuario con el dni ingresado");
        }

        Cliente cliente = new Cliente();
        cliente.setAlta(Boolean.TRUE);
        cliente.setNombreUsuario(nombreUsuario);
        cliente.setPassword(new BCryptPasswordEncoder().encode(password));
        cliente.setRol(Rol.CLIENTE);
        cliente.setDni(dni);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setMail(mail);
        cliente.setZona(zona);

        Foto foto = fotoServicio.guardar(archivo);
        cliente.setFoto(foto);

        return clienteRepositorio.save(cliente);
    }

    @Transactional
    public Cliente editar(String id, String nombreUsuario, String password, String password2, String dni, String nombre, String apellido, String direccion, String telefono, String mail, Zona zona, MultipartFile archivo) throws ErrorServicio, Exception {

        validar(nombreUsuario, password, password2, dni, nombre, apellido, direccion, telefono, mail, zona);

        Cliente cliente = findById(id);
        cliente.setNombreUsuario(nombreUsuario);
        cliente.setPassword(new BCryptPasswordEncoder().encode(password));
        cliente.setDni(dni);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setMail(mail);
        cliente.setZona(zona);

        String idFoto = null;
        if (cliente.getFoto() != null) {
            idFoto = cliente.getFoto().getId();
        }
        Foto foto = fotoServicio.editar(idFoto, archivo);
        cliente.setFoto(foto);

        return clienteRepositorio.save(cliente);
}

public void validar(String nombreUsuario, String password, String password2, String dni, String nombre, String apellido, String direccion, String telefono, String mail, Zona zona) throws ErrorServicio {

        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new ErrorServicio("El nombre de usuario no puede ser nulo");
        }
        if (password == null || password2 == null || password.isEmpty() || password2.isEmpty()) {
            throw new ErrorServicio("La contraseña no puede ser nula");
        }
        if (password.length() < 5 || password2.length() < 5 || password.length() > 10  || password2.length() > 10){
            throw new ErrorServicio("La contraseña no puede ser nula");
        }
        if (!password.equals(password2)) {
            throw new ErrorServicio("Las contraseñas deben ser iguales");
        }
        if (dni == null || dni.isEmpty()) {
            throw new ErrorServicio("El DNI no puede ser nulo");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido no puede ser nulo");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new ErrorServicio("La direccióno no puede ser nula");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new ErrorServicio("El telefono no puede ser nulo");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("El mail no puede ser nulo");
        }
        if (zona == null) {
            throw new ErrorServicio("La zona no puede ser nula");
        }
    }
    
    public Cliente findById(String id) throws ErrorServicio{
        Optional<Cliente> opcional = clienteRepositorio.findById(id);
        if (opcional.isPresent()){
            return opcional.get();
        }else{
            throw new ErrorServicio("No se encuentra el usuario solicitado");
        }
    }
    
    @Transactional
    public void darDeAlta(String id) throws ErrorServicio{
        Cliente cliente = findById(id);
        cliente.setAlta(true);
        clienteRepositorio.save(cliente);
    }
    
    @Transactional
    public void darDeBaja(String id) throws ErrorServicio{
        Cliente cliente = findById(id);
        cliente.setAlta(false);
        clienteRepositorio.save(cliente);
    }
    
    public void comentar(String id, String descripcion, String idComercio) throws ErrorServicio {
        comentarioServicio.guardar(id, descripcion, idComercio);
    }
    
    public List<Comercio> guardarComercios(String id, String idComercio) throws ErrorServicio{
        
        Cliente cliente = findById(id);
        
        Comercio comercio = comercioServicio.findById(idComercio);
        
        cliente.getComercios().add(comercio);
        
        return cliente.getComercios();
    }
}

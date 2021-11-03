package com.mirosario.MiRosario.servicios;

import com.mirosario.MiRosario.entidades.Comercio;
import com.mirosario.MiRosario.entidades.Foto;
import com.mirosario.MiRosario.enums.Rol;
import com.mirosario.MiRosario.enums.Rubro;
import com.mirosario.MiRosario.enums.Zona;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.repositorios.ComercioRepositorio;
import com.mirosario.MiRosario.repositorios.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ComercioServicio {

  @Autowired  
  private ComercioRepositorio comercioRepositorio; 
  @Autowired
  private ComercioServicio comercioServicio;
  
  @Autowired
  private FotoRepositorio fotoRepositorio;
  @Autowired
  private FotoServicio fotoServicio;
  
  
  @Transactional
  public Comercio guardar(MultipartFile archivo, String nombreUsuario, String password, String password2, String cuit, String nombreComercio, Rubro rubro, String direccion, Zona zona, String descripcion, String mail) throws ErrorServicio, Exception{
    
    validar(nombreUsuario, password, password2, cuit, nombreComercio, rubro, direccion, zona, descripcion, mail);  
    
    if(comercioRepositorio.buscarPorUsuario(nombreUsuario) != null){
        throw new ErrorServicio("ya existe un comercio registrado con ese usuario");
    }
    
    if(comercioRepositorio.buscarPorCuit(cuit) != null){
        throw new ErrorServicio("ya existe un comercio regisgrado con ese cuit");
    } 
    
    Comercio comercio = new Comercio();
    
    comercio.setAlta(Boolean.TRUE);
    comercio.setRol(Rol.COMERCIO);
    comercio.setNombreUsuario(nombreUsuario);
    comercio.setPassword(new BCryptPasswordEncoder().encode(password));
    comercio.setCuit(cuit); 
    comercio.setNombreComercio(nombreComercio);
    comercio.setRubro(rubro);
    comercio.setDireccion(direccion);
    comercio.setZona(zona);
    comercio.setDescripcion(descripcion);
    comercio.setMail(mail);
    
    Foto foto = fotoServicio.guardar(archivo);
    comercio.setFoto(foto);
    
    
    return comercioRepositorio.save(comercio);
  }
  
  public Comercio findById(String id) throws ErrorServicio {
        Optional<Comercio> respuesta = comercioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("No se encuentra el usuario solicitado");
        }
    }
  
  
  public void validar (String nombreUsuario, String password, String password2, String cuit, String nombreComercio, Rubro rubro, String direccion, Zona zona, String descripcion, String mail)throws ErrorServicio{
    if(nombreUsuario == null || nombreUsuario.isEmpty()){
      throw new ErrorServicio("el usuario no puede estar vacio");
    }
    
    if( password == null || password2 == null || password2.isEmpty() || password.isEmpty()){
      throw new ErrorServicio("la contraseña no puede estar vacia");
    }
    
    if (password.length() > 10 || password2.length() > 10 || password.length() < 5 || password2.length() < 5) {
            throw new ErrorServicio("La contraseña debe tener un mínimo de 5 caracteres y un máximo de 10 caracteres");
        }
    if (!password.equals(password2)) {
            throw new ErrorServicio("Las contraseñas deben ser identicas");
    }
    
    if(cuit == null || cuit.isEmpty()){
      throw new ErrorServicio("el numero de cuit no puede estar vacio");
    }
    
    if(nombreComercio == null || nombreComercio.isEmpty()){
      throw new ErrorServicio("el nombre no puede estar vacio");
    }
    
    if(rubro == null || rubro.toString().isEmpty()){
      throw new ErrorServicio("el rubro no puede estar vacio");
    }
    
    if(direccion == null || direccion.isEmpty()){
      throw new ErrorServicio("la direccion no puede estar vacio");
    }
    
    if(zona == null || zona.toString().isEmpty()){
      throw new ErrorServicio("la zona no puede estar vacia");
    }
    
    if(descripcion == null || descripcion.isEmpty()){
      throw new ErrorServicio("la descripcion no puede estar vacia");
    }
    
    if(mail == null || mail.isEmpty()){
      throw new ErrorServicio("la direccion de e-mail no puede estar vacia");
    }
  }
  
}

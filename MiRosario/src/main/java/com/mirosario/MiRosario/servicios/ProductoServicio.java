package com.mirosario.MiRosario.servicios;
import com.mirosario.MiRosario.entidades.Foto;
import com.mirosario.MiRosario.entidades.Producto;
import com.mirosario.MiRosario.excepciones.ErrorServicio;
import com.mirosario.MiRosario.repositorios.ProductoRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoServicio {
    @Autowired
    private FotoServicio fotoServicio;
    @Autowired
    private ProductoRepositorio productoRepositorio;
 @Transactional
    public Producto guardar( String nombre, Double precio,String descripcion, MultipartFile archivo) throws ErrorServicio, Exception{
              validacion(nombre, precio, descripcion);
                Producto producto=new Producto();
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                producto.setDescripcion(descripcion);
                Foto foto = fotoServicio.guardar(archivo);
                producto.setFoto(foto);
                producto.setAlta(true);
                        return productoRepositorio.save(producto);
    }
    public void validacion(String nombre, Double precio,String descripcion) throws ErrorServicio{
        if (nombre==null || nombre.isEmpty()) {
            throw new ErrorServicio("nombre nulo o vacio");
        }
          if (precio==null || precio <1) {
            throw new ErrorServicio("precio nulo o menor a 1");
        }
            if (descripcion==null || descripcion.isEmpty()) {
            throw new ErrorServicio("descripcion nula o vacia");
        }
    }
        public List<Producto> mostrarListaProductos(){
    return productoRepositorio.findAll();
    }
}

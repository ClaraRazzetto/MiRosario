package com.mirosario.MiRosario.repositorios;

import com.mirosario.MiRosario.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, String>{

   
}

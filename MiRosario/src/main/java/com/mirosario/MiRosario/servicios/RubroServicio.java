package com.mirosario.MiRosario.servicios;

import com.mirosario.MiRosario.enums.Rubro;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RubroServicio {

    public List<Rubro> listarRubros() {

        List<Rubro> rubros = new ArrayList<>();

        rubros.add(Rubro.ALIMENTOS);
        rubros.add(Rubro.BARES);
        rubros.add(Rubro.FARMACIA);
        rubros.add(Rubro.FERRETERIA);
        rubros.add(Rubro.HELADERIAS);
        rubros.add(Rubro.LIBRERIAS);
        rubros.add(Rubro.SEXSHOP);
        rubros.add(Rubro.VIVIENDA);
        
        return rubros;
    }

}

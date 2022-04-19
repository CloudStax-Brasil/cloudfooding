package com.cloudstax.cloudfooding;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.group.temperatura.Temperatura;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class Data {
    
     public void cadastrarSistema() {
        Looca looca = new Looca();
        Sistema sistema = looca.getSistema();
        Memoria memoria = looca.getMemoria();
        Temperatura temperatura = looca.getTemperatura();
        Processador processador = looca.getProcessador();
        System.out.println(memoria);
        System.out.println(temperatura);
        System.out.println(processador);
        System.out.println(sistema);


    }    
}

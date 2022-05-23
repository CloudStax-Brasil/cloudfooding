/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudstax.cloudfooding;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author gabriel.aguiar@VALEMOBI.CORP
 */
public class DatabaseConnection {

    Connection config = new Connection();
    JdbcTemplate template = new JdbcTemplate(config.getDataSource());
    HardwareData hwData = new HardwareData();
    private String emailGerente = "";
    private String senhaGerente = "";

    public void setConnection(String email, String senha) {
        this.emailGerente = email;
        this.senhaGerente = senha;
    }

    public String getEmail() {
        String result = "";
        String select = String.format("SELECT emailGerente FROM gerente "
                + "WHERE emailGerente = '%s' AND senhaGerente = '%s'",
                this.emailGerente, this.senhaGerente);

        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            result = "Email não encontrado";
            System.out.println(result);
        }
        return result;
    }

    public String getSenha() {
        String result = "";
        String select = String.format("SELECT senhaGerente FROM gerente "
                + "WHERE emailGerente = '%s' AND senhaGerente = '%s'",
                this.emailGerente, this.senhaGerente);
        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            result = "Senha não encontrada";
            System.out.println(result);
        }
        return result;
    }

    public String getNome() {
        String result = "";
        String select = String.format("SELECT nomeGerente FROM gerente "
                + "WHERE emailGerente = '%s'",
                this.emailGerente);
        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            result = "Nome não encontrado";
            System.out.println(result);
        }
        return result;
    }

    public String getGerente() {
        String result = "";
        String select = String.format("SELECT idGerente FROM gerente "
                + "WHERE emailGerente = '%s'",
                this.emailGerente);
        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            result = "Gerente não encontrado";
            System.out.println(result);
        }
        return result;
    }

    public Boolean verifyHostname() {
        String hostname = hwData.getHostname();
        Boolean result;
        String select = String.format("SELECT hostname FROM maquina "
                + "WHERE hostname = '%s'",
                hostname);
        try {
            template.queryForObject(select, String.class);
            result = false;
        } catch (EmptyResultDataAccessException exception) {
            result = true;
        }
        return result;
    }

    public String getMachineId() {
        String hostname = hwData.getHostname();
        String result = null;
        String select = String.format("SELECT idMaquina FROM maquina "
                + "WHERE hostname = '%s'",
                hostname);
        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            System.out.println("Maquina não encontrada");
        }
        return result;
    }

    public void saveHardwareData() {
        String fkGerente = this.getGerente();
        hwData.setHostname();
        String hostname = hwData.getHostname();
        String memoriaTotal = hwData.getMemoryData().getTotal().toString();
        String sistema = hwData.getSistema().getSistemaOperacional();
        String insert = String.format("INSERT INTO maquina(hostname, memoriaTotal, sistemaOperacional,"
                + "fkGerente)"
                + "VALUES ('%s', %s, '%s', %s)",
                hostname, memoriaTotal, sistema, fkGerente);
        if (this.verifyHostname() == true) {
            try {
                template.update(insert);
                System.out.println("\nMáquina inserida com sucesso\n");
            } catch (DataAccessException error) {
//                System.out.println("fkGerente: " + fkGerente);
//                System.out.println("hostname: " + hostname);
//                System.out.println("memoriaTotal: " + memoriaTotal);
//                System.out.println("sistema: " + sistema);
                System.out.println("Erro ao inserir máquina no banco");
            }
        } else {
            System.out.println("\nMáquina já cadastrada\n");
        }
    }

    public String saveCpuAndMemoryDataInLoop() {
        String result;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        String temperatura = hwData.getTemperatura().toString();
        Double usoCpu = hwData.getProcessador().getUso();
        String usoRam = hwData.getMemoryData().getEmUso().toString();
        String hora = formatter.format(date);
        String fkMaquina = this.getMachineId();

        String insertCpu = String.format("INSERT INTO cpu(temperatura, uso, hora,"
                + "fkMaquina)"
                + "VALUES ('%s', %d, '%s', %s)",
                temperatura, usoCpu, hora, fkMaquina);

        String insertMemory = String.format("INSERT INTO memoriaRam(uso, horario, fkMaquina)"
                + "VALUES (%s, '%s', %s)",
                usoRam, hora, fkMaquina);

        try {
            template.update(insertCpu);
            template.update(insertMemory);
            result = "\nDados inseridos com sucesso\n";
            System.out.println(result);
        } catch (DataAccessException error) {
            result = "Erro ao inserir dados no banco";
            System.out.println(result);
        }

        return result;
    }

}

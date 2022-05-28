/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudstax.cloudfooding;

import java.util.Date;
import java.text.SimpleDateFormat;
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
        String result = null;
        String select = String.format("SELECT emailGerente FROM gerente "
                + "WHERE emailGerente = '%s' AND senhaGerente = '%s'",
                this.emailGerente, this.senhaGerente);

        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            select = String.format("SELECT emailFuncionario FROM funcionario "
                    + "WHERE emailFuncionario = '%s' AND senhaFuncionario = '%s'",
                    this.emailGerente, this.senhaGerente);
            try {
                result = template.queryForObject(select, String.class);
            } catch (EmptyResultDataAccessException e) {
                System.out.println("Email não cadastrado");
            }
        }
        return result;
    }

    public String getSenha() {
        String result = null;
        String select = String.format("SELECT senhaGerente FROM gerente "
                + "WHERE emailGerente = '%s' AND senhaGerente = '%s'",
                this.emailGerente, this.senhaGerente);
        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            select = String.format("SELECT senhaFuncionario FROM funcionario "
                    + "WHERE emailFuncionario = '%s' AND senhaFuncionario = '%s'",
                    this.emailGerente, this.senhaGerente);
            try {
                result = template.queryForObject(select, String.class);
            } catch (EmptyResultDataAccessException e) {
                System.out.println("Senha incorreta");
            }
        }
        return result;
    }

    public String getNome() {
        String result = null;
        String select = String.format("SELECT nomeGerente FROM gerente "
                + "WHERE emailGerente = '%s'",
                this.emailGerente);
        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            select = String.format("SELECT nomeFuncionario FROM funcionario "
                    + "WHERE emailFuncionario = '%s' AND senhaFuncionario = '%s'",
                    this.emailGerente, this.senhaGerente);
            try {
                result = template.queryForObject(select, String.class);
            } catch (EmptyResultDataAccessException e) {
                System.out.println("Nome não encontrado");
            }
        }
        return result;
    }

    public String getGerente() {
        String result = null;
        String select = String.format("SELECT idGerente FROM gerente "
                + "WHERE emailGerente = '%s'",
                this.emailGerente);
        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            if (this.getFuncionario() != null) {
                System.out.println("\nLogando como funcionário");
            } else {
                System.out.println("Gerente não encontrado");
            }
        }
        return result;
    }

    public String getFuncionario() {
        String result = null;
        String select = String.format("SELECT idFuncionario FROM funcionario "
                + "WHERE emailFuncionario = '%s'", this.emailGerente);
        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            if (this.getGerente() != null) {
                System.out.println("\nLogando como gerente");
            } else {
                System.out.println("Funcionario não encontrado");
            }
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
                System.out.println("Erro ao inserir máquina no banco");
            }
        } else {
            System.out.println("\nMáquina já cadastrada\n");
        }
    }

    public void saveCpuAndMemoryDataInLoop(User funcionario) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        String temperatura = hwData.getTemperatura();
        Double usoCpu = hwData.getProcessador().getUso();
        String usoRam = hwData.getMemoryData().getEmUso().toString();
        String hora = formatter.format(date);
        String fkMaquina = this.getMachineId();
        String fkFuncionario = this.getFuncionario();

        String insertCpu = String.format("INSERT INTO cpu(temperatura, uso, hora,"
                + "fkMaquina, fkFuncionario)"
                + "VALUES ('%s', %s, '%s', %s, %s)",
                temperatura, usoCpu, hora, fkMaquina, fkFuncionario);

        String insertMemory = String.format("INSERT INTO memoriaRam(uso, horario, "
                + "fkMaquina, fkFuncionario)"
                + "VALUES (%s, '%s', %s, %s)",
                usoRam, hora, fkMaquina, fkFuncionario);

        try {
            template.update(insertCpu);
            template.update(insertMemory);
            System.out.println("\nDados inseridos com sucesso\n");
        } catch (DataAccessException error) {
            System.out.println("\nErro ao inserir dados no banco\n");
        }
    }
}

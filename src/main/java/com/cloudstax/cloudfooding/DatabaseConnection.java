/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudstax.cloudfooding;

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
    private String emailEmpresa = "";
    private String senhaEmpresa = "";

    public void setConnection(String email, String senha) {
        this.emailEmpresa = email;
        this.senhaEmpresa = senha;
    }

    public String getEmail() {
        String result = "";
        String select = String.format("SELECT emailEmpresa FROM empresa "
                + "WHERE emailEmpresa = '%s' AND senhaEmpresa = '%s'",
                this.emailEmpresa, this.senhaEmpresa);

        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            result = "Email não encontrado";
        }
        return result;
    }

    public String getSenha() {
        String result = "";
        String select = String.format("SELECT senhaEmpresa FROM empresa "
                + "WHERE emailEmpresa = '%s' AND senhaEmpresa = '%s'",
                this.emailEmpresa, this.senhaEmpresa);
        try {
            result = template.queryForObject(select, String.class);
        } catch (EmptyResultDataAccessException exception) {
            result = "Senha não encontrada";
        }
        return result;
    }

    public String getNome() {
        String result = "";
        String select = String.format("SELECT nomeEmpresa FROM empresa "
                + "WHERE emailEmpresa = '%s'",
                this.emailEmpresa);
        try {
            result = template.queryForObject(select, String.class);
        }catch(EmptyResultDataAccessException exception){
            result = "Nome não encontrado";
        }
        return result;
    }

    public String saveHardwareData() {
        String result;
        String hostname = hwData.getHostname();
        String memoriaTotal = hwData.getMemoryData().getTotal().toString();
        String sistema = hwData.getSistema().getSistemaOperacional();
        String insert = String.format("INSERT INTO maquina(hostname, memoriaTotal, sistemaOperacional"
                + "fkFranquias)"
                + "VALUES ('%s', '%s', '%s', '%s')",
                hostname, memoriaTotal, sistema);
        try {
            result = template.queryForObject(insert, String.class);
            System.out.println("Dado de cpu inserido com sucesso");
        } catch (DataAccessException error) {
            result = "Erro ao inserir dado de cpu";
        }
        return result;
    }
}

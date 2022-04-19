package com.cloudstax.cloudfooding;

public class User {
 
    private Integer id;
    private String nome;
    private String senha;

    public User() {
        this.id = 1;
        this.nome = "dudu";
        this.senha = "123";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String telefone) {
        this.senha = telefone;
    }   
}


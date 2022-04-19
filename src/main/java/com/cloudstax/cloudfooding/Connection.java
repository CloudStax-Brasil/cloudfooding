package com.cloudstax.cloudfooding;

import org.apache.commons.dbcp2.BasicDataSource;

public class Connection {
      private BasicDataSource dataSource;

    public Connection() {
        this.dataSource = new BasicDataSource();
        
         dataSource​.setDriverClassName("org.h2.Driver");
         dataSource​.setUrl("jdbc:h2:file:/home/eduardo.cardoso@VALEMOBI.CORP/NetBeansProjects/ProjetoBanco");
         dataSource​.setUsername("sa");
         dataSource​.setPassword(""); 
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }
}

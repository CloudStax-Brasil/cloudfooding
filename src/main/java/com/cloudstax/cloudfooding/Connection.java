package com.cloudstax.cloudfooding;
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class Connection {
      private BasicDataSource dataSource;

    public Connection() {
        this.dataSource = new BasicDataSource();
        
         dataSource​.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         dataSource​.setUrl("jdbc:sqlserver://cloudstax.database.windows.net:1433;database=cloudstax;user=cloudstax@cloudstax;password=#Sptech2022;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
         dataSource​.setUsername("cloudstax");
         dataSource​.setPassword("#Sptech2022"); 
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }
}

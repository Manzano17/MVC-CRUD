/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Manza
 */

public class ConexionBD {
    Connection conn;
    String driver = "com.mysql.cj.jdbc.Driver";
    String dbName = "inventarioLlaves";
    String url = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    String usuario = "mecanico";
    String contrasenia = "123456";
    
    public Connection conectarBD() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuario, contrasenia);
            System.out.println("Conexión exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexión: " + e);
        }
        return conn;
    }
}


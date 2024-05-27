/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Controlador.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Manza
 */
public class ProductoDAO {

    ConexionBD conexion = new ConexionBD(); // Instancia de conexion a la BD
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List listar() {
        String sql = "select * from llave";
        List<Producto> lista = new ArrayList<>();
        try {
            con = conexion.conectarBD();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt(1));
                producto.setNombre(rs.getString(2));
                producto.setPrecio(rs.getDouble(3));
                producto.setInventario(rs.getInt(4));
                lista.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error listar:" + e);
        }
        return lista;
    }

    // Funcion agregar
    public void agregar(Producto producto) {
        String sql = "insert into llave(nombre, precio, inventario) values (?, ?, ?)";
        try {
            con = conexion.conectarBD();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getInventario());
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "El producto ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error en agregar: " + e);
        }
    }

    // Metodo actualizar
    public void actualizar(Producto producto) {
        String sql = "update llave set nombre=?, precio=?, inventario=? where codigo=?";
        try {
            con = conexion.conectarBD();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getInventario());
            ps.setInt(4, producto.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en actualizarDAO: " + e);
        }
    }

    // Metodo ELIMINAR
    public void borrar(int id) {
        String sql = "delete from llave where codigo=" + id;
        try {
            con = conexion.conectarBD();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en borrarDAO: " + e);
        }
    }

}

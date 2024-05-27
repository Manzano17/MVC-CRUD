/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Modelo.Producto;
import Modelo.ProductoDAO;
import Vista.Interfaz;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Manza
 */
public class ControladorProducto implements ActionListener {

    // Instancias de cada objeto.
    Producto producto = new Producto();
    ProductoDAO productodao = new ProductoDAO();
    Interfaz vista = new Interfaz();
    DefaultTableModel modeloTabla = new DefaultTableModel();

    // Variables globales
    private int codigo = 0;
    private String nombre;
    private double precio;
    private int inventario;

    public ControladorProducto(Interfaz vista) {
        this.vista = vista;
        vista.setVisible(true);
        agregarEventos();
        listarTabla();
    }

    private void agregarEventos() {
        vista.getBtnAgregar().addActionListener(this);
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnEliminar().addActionListener(this);
        vista.getBtnLimpiar().addActionListener(this);

        vista.getTblTabla().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                autocompletarCampos(e);
            }
        });
    }

    private void listarTabla() {
        String[] titulos = new String[]{"Codigo", "Nombre", "Precio", "Inventario"};
        modeloTabla = new DefaultTableModel(titulos, 0);
        List<Producto> listaProductos = productodao.listar();
        for (Producto producto : listaProductos) {
            modeloTabla.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getPrecio(), producto.getInventario()});
        }
        vista.getTblTabla().setModel(modeloTabla);
        vista.getTblTabla().setPreferredSize(new Dimension(350, modeloTabla.getRowCount() * 16));
    }

    private void autocompletarCampos(MouseEvent e) {
        JTable target = (JTable) e.getSource();
        codigo = (int) vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 0);
        vista.getjTextField1().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 1).toString());
        vista.getjTextField2().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 2).toString());
        vista.getjTextField3().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 3).toString());
    }

    // Valida el Formulario
    private boolean validarDatos() {
        if ("".equals(vista.getjTextField1().getText()) || "".equals(vista.getjTextField2().getText()) || "".equals(vista.getjTextField3().getText())) {
            JOptionPane.showMessageDialog(null, "Los campos no deben de estar vacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Carga los valores, para despues parsearlos para 
    private boolean cargarDatos() {
        try {
            nombre = vista.getjTextField1().getText();
            precio = Double.parseDouble(vista.getjTextField2().getText());
            inventario = Integer.parseInt(vista.getjTextField3().getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los campos precio e inventario deben ser numericos.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al cargar Datos: " + e);
            return false;
        }
    }

    private void limpiarCampos() {
        vista.getjTextField1().setText("");
        vista.getjTextField2().setText("");
        vista.getjTextField3().setText("");
        codigo = 0;
        nombre = "";
        precio = 0;
        inventario = 0;

    }

    // Metodo agregar
    private void agregarProducto() {
        try {
            if (validarDatos()) {
                if (cargarDatos()) {
                    Producto producto = new Producto(nombre, precio, inventario);
                    productodao.agregar(producto);
                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                    limpiarCampos();
                }
            }
        } catch (HeadlessException e) {
            System.out.println("Error AgregarC:" + e);
        } finally {
            listarTabla();
        }
    }

    private void actualizarProducto() {
        try {
            if (validarDatos()) {
                if (cargarDatos()) {
                    Producto producto = new Producto(codigo, nombre, precio, inventario);
                    productodao.actualizar(producto);
                    JOptionPane.showMessageDialog(null, "Registro actualizado");
                    limpiarCampos();
                }
            }
        } catch (HeadlessException e) {
            System.out.println("Error actualizarC: " + e);
        } finally {
            listarTabla();
        }
    }

    private void borrarProducto() {
        try {
            if (codigo != 0) {
                productodao.borrar(codigo);
                JOptionPane.showMessageDialog(null, "Registro borrado");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Debe de seleccionar un producto de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException e) {
            System.out.println("Error borrarC: " + e);
        } finally {
            listarTabla();
        }
    }
    // Acciona los botones

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vista.getBtnAgregar()) {
            agregarProducto();
        }
        if (ae.getSource() == vista.getBtnActualizar()) {
            actualizarProducto();
        }
        if (ae.getSource() == vista.getBtnEliminar()) {
            borrarProducto();
        }
        if (ae.getSource() == vista.getBtnLimpiar()) {
            limpiarCampos();
        }
    }
}

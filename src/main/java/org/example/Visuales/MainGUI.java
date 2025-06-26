package org.example.Visuales;

import org.example.logica.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class MainGUI extends JFrame {
    private Controladora controladora;
    private JTabbedPane tabbedPane;
    
    // Paneles
    private JPanel panelClientes;
    private JPanel panelProductos;
    private JPanel panelPedidos;
    
    // Tablas
    private JTable tablaClientes;
    private JTable tablaProductos;
    private JTable tablaPedidos;
    
//    public MainGUI(Controladora controladora) {
//        this.controladora = controladora;
//        initComponents();
//        setTitle("Sistema de Gestión de Pedidos");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//    }
    
    public MainGUI(Controladora controladora) {
    this.controladora = controladora;
    System.out.println("Constructor MainGUI ejecutado"); // Debug
    initComponents();
    setTitle("Sistema de Gestión de Pedidos");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    System.out.println("Interfaz configurada"); // Debug
}
    
    
    private void initComponents() {
        tabbedPane = new JTabbedPane();
        
        // Panel de Clientes
        panelClientes = new JPanel(new BorderLayout());
        crearPanelClientes();
        tabbedPane.addTab("Clientes", panelClientes);
        
        // Panel de Productos
        panelProductos = new JPanel(new BorderLayout());
        crearPanelProductos();
        tabbedPane.addTab("Productos", panelProductos);
        
        // Panel de Pedidos
        panelPedidos = new JPanel(new BorderLayout());
        crearPanelPedidos();
        tabbedPane.addTab("Pedidos", panelPedidos);
        
        add(tabbedPane);
        
        // Actualizar datos al iniciar
        actualizarTablaClientes();
        actualizarTablaProductos();
        actualizarTablaPedidos();
    }
    
    private void crearPanelClientes() {
        // Tabla de clientes
        tablaClientes = new JTable();
        JScrollPane scrollClientes = new JScrollPane(tablaClientes);
        panelClientes.add(scrollClientes, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotonesClientes = new JPanel(new FlowLayout());
        
        JButton btnAgregarCliente = new JButton("Agregar");
        btnAgregarCliente.addActionListener(e -> agregarCliente());
        
        JButton btnEditarCliente = new JButton("Editar");
        btnEditarCliente.addActionListener(e -> editarCliente());
        
        JButton btnEliminarCliente = new JButton("Eliminar");
        btnEliminarCliente.addActionListener(e -> eliminarCliente());
        
        panelBotonesClientes.add(btnAgregarCliente);
        panelBotonesClientes.add(btnEditarCliente);
        panelBotonesClientes.add(btnEliminarCliente);
        
        panelClientes.add(panelBotonesClientes, BorderLayout.SOUTH);
    }
    
    private void crearPanelProductos() {
        // Tabla de productos
        tablaProductos = new JTable();
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);
        panelProductos.add(scrollProductos, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotonesProductos = new JPanel(new FlowLayout());
        
        JButton btnAgregarProducto = new JButton("Agregar");
        btnAgregarProducto.addActionListener(e -> agregarProducto());
        
        JButton btnEditarProducto = new JButton("Editar");
        btnEditarProducto.addActionListener(e -> editarProducto());
        
        JButton btnEliminarProducto = new JButton("Eliminar");
        btnEliminarProducto.addActionListener(e -> eliminarProducto());
        
        panelBotonesProductos.add(btnAgregarProducto);
        panelBotonesProductos.add(btnEditarProducto);
        panelBotonesProductos.add(btnEliminarProducto);
        
        panelProductos.add(panelBotonesProductos, BorderLayout.SOUTH);
    }
    
    private void crearPanelPedidos() {
        // Tabla de pedidos
        tablaPedidos = new JTable();
        JScrollPane scrollPedidos = new JScrollPane(tablaPedidos);
        panelPedidos.add(scrollPedidos, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotonesPedidos = new JPanel(new FlowLayout());
        
        JButton btnAgregarPedido = new JButton("Agregar");
        btnAgregarPedido.addActionListener(e -> agregarPedido());
        
        JButton btnEditarPedido = new JButton("Editar");
        btnEditarPedido.addActionListener(e -> editarPedido());
        
        JButton btnEliminarPedido = new JButton("Eliminar");
        btnEliminarPedido.addActionListener(e -> eliminarPedido());
        
        JButton btnResumenVentas = new JButton("Resumen Ventas");
        btnResumenVentas.addActionListener(e -> mostrarResumenVentas());
        
        panelBotonesPedidos.add(btnAgregarPedido);
        panelBotonesPedidos.add(btnEditarPedido);
        panelBotonesPedidos.add(btnEliminarPedido);
        panelBotonesPedidos.add(btnResumenVentas);
        
        panelPedidos.add(panelBotonesPedidos, BorderLayout.SOUTH);
    }
    
    // Métodos para actualizar las tablas
private void actualizarTablaClientes() {
    List<Cliente> clientes = controladora.traerClientes();
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("Customer ID");
    model.addColumn("Nombre"); 
    
    for (Cliente cliente : clientes) {
        model.addRow(new Object[]{
            cliente.getId(),
            cliente.getCustomerID(),
            cliente.getNombre() 
        });
    }
    
    tablaClientes.setModel(model);
}
    
    private void actualizarTablaProductos() {
        List<Producto> productos = controladora.traerProductos();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Categoría");
        model.addColumn("Subcategoría");
        model.addColumn("Precio");
        
        for (Producto producto : productos) {
            model.addRow(new Object[]{
                producto.getId(),
                producto.getProduct(),
                producto.getProductCategory(),
                producto.getProductSubCategory(),
                producto.getPrecioIndividual()
            });
        }
        
        tablaProductos.setModel(model);
    }
    
    private void actualizarTablaPedidos() {
        List<Pedido> pedidos = controladora.traerPedidos();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Fecha");
        model.addColumn("Cliente");
        model.addColumn("Total General");
        
        for (Pedido pedido : pedidos) {
            model.addRow(new Object[]{
                pedido.getId(),
                pedido.getFechaPedido(),
                pedido.getCustomerID() != null ? pedido.getCustomerID().getCustomerID() : "N/A",
                pedido.getTotalGeneral()
            });
        }
        
        tablaPedidos.setModel(model);
    }
    
    // Métodos para operaciones CRUD
    private void agregarCliente() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        
        JTextField txtCustomerID = new JTextField();
        
        panel.add(new JLabel("Customer ID:"));
        panel.add(txtCustomerID);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Cliente", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                Cliente cliente = new Cliente();
                cliente.setCustomerID(new BigDecimal(txtCustomerID.getText()));
                
                controladora.crearCliente(cliente);
                actualizarTablaClientes();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al agregar cliente: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        BigDecimal id = (BigDecimal) tablaClientes.getValueAt(filaSeleccionada, 0);
        Cliente cliente = controladora.traerCliente(id.intValue());
        
        JPanel panel = new JPanel(new GridLayout(3, 2));
        
        JTextField txtCustomerID = new JTextField(cliente.getCustomerID().toString());
        
        panel.add(new JLabel("Customer ID:"));
        panel.add(txtCustomerID);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                cliente.setCustomerID(new BigDecimal(txtCustomerID.getText()));
                controladora.editarCliente(cliente);
                actualizarTablaClientes();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al editar cliente: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea eliminar este cliente?", 
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            BigDecimal id = (BigDecimal) tablaClientes.getValueAt(filaSeleccionada, 0);
            controladora.eliminarCliente(id.intValue());
            actualizarTablaClientes();
        }
    }
    
    private void agregarProducto() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        
        JTextField txtProductID = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtSubcategoria = new JTextField();
        JTextField txtCategoria = new JTextField();
        JTextField txtPrecio = new JTextField();
        
        panel.add(new JLabel("Product ID:"));
        panel.add(txtProductID);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Subcategoría:"));
        panel.add(txtSubcategoria);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Producto", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                Producto producto = new Producto();
                producto.setProductID(new BigDecimal(txtProductID.getText()));
                producto.setProduct(txtNombre.getText());
                producto.setProductSubCategory(txtSubcategoria.getText());
                producto.setProductCategory(txtCategoria.getText());
                producto.setPrecioIndividual(txtPrecio.getText());
                
                controladora.crearProducto(producto);
                actualizarTablaProductos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para editar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        BigDecimal id = (BigDecimal) tablaProductos.getValueAt(filaSeleccionada, 0);
        Producto producto = controladora.traerProducto(id.intValue());
        
        JPanel panel = new JPanel(new GridLayout(6, 2));
        
        JTextField txtProductID = new JTextField(producto.getProductID().toString());
        JTextField txtNombre = new JTextField(producto.getProduct());
        JTextField txtSubcategoria = new JTextField(producto.getProductSubCategory());
        JTextField txtCategoria = new JTextField(producto.getProductCategory());
        JTextField txtPrecio = new JTextField(producto.getPrecioIndividual());
        
        panel.add(new JLabel("Product ID:"));
        panel.add(txtProductID);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Subcategoría:"));
        panel.add(txtSubcategoria);
        panel.add(new JLabel("Categoría:"));
        panel.add(txtCategoria);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Producto", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                producto.setProductID(new BigDecimal(txtProductID.getText()));
                producto.setProduct(txtNombre.getText());
                producto.setProductSubCategory(txtSubcategoria.getText());
                producto.setProductCategory(txtCategoria.getText());
                producto.setPrecioIndividual(txtPrecio.getText());
                
                controladora.editarProducto(producto);
                actualizarTablaProductos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al editar producto: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea eliminar este producto?", 
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            BigDecimal id = (BigDecimal) tablaProductos.getValueAt(filaSeleccionada, 0);
            controladora.eliminarProducto(id.intValue());
            actualizarTablaProductos();
        }
    }
    
    private void agregarPedido() {
        // Implementación similar a los métodos anteriores, pero más compleja
        // ya que un pedido tiene relaciones con otras entidades
        JOptionPane.showMessageDialog(this, "Funcionalidad de agregar pedido no implementada aún", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void editarPedido() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de editar pedido no implementada aún", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void eliminarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea eliminar este pedido?", 
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            BigDecimal id = (BigDecimal) tablaPedidos.getValueAt(filaSeleccionada, 0);
            controladora.eliminarPedido(id.intValue());
            actualizarTablaPedidos();
        }
    }
    
    private void mostrarResumenVentas() {
        // Implementar el resumen de ventas basado en los métodos comentados en Controladora
        StringBuilder resumen = new StringBuilder();
        resumen.append("Resumen de Ventas:\n");
        
        // Aquí podrías implementar la lógica para mostrar el resumen
        // usando los métodos comentados en la clase Controladora
        
        JOptionPane.showMessageDialog(this, "Funcionalidad de resumen de ventas no implementada aún", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            Controladora controladora = new Controladora();
//            MainGUI gui = new MainGUI(controladora);
//            gui.setVisible(true);
//        }
//        );
//    }
}

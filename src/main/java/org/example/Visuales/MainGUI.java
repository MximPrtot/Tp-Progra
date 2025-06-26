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
        
//        JButton btnEditarCliente = new JButton("Editar");
//        btnEditarCliente.addActionListener(e -> editarCliente());
        
        JButton btnEliminarCliente = new JButton("Eliminar");
        btnEliminarCliente.addActionListener(e -> eliminarCliente());
        
        panelBotonesClientes.add(btnAgregarCliente);
//        panelBotonesClientes.add(btnEditarCliente);
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
        
//        JButton btnEditarProducto = new JButton("Editar");
//        btnEditarProducto.addActionListener(e -> editarProducto());
        
        JButton btnEliminarProducto = new JButton("Eliminar");
        btnEliminarProducto.addActionListener(e -> eliminarProducto());
        
        panelBotonesProductos.add(btnAgregarProducto);
        //panelBotonesProductos.add(btnEditarProducto);
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
        
//        JButton btnEditarPedido = new JButton("Editar");
//        btnEditarPedido.addActionListener(e -> editarPedido());
        
        JButton btnEliminarPedido = new JButton("Eliminar");
        btnEliminarPedido.addActionListener(e -> eliminarPedido());
        
        JButton btnResumenVentas = new JButton("Resumen Ventas");
        btnResumenVentas.addActionListener(e -> mostrarResumenVentas());
        
        panelBotonesPedidos.add(btnAgregarPedido);
        //panelBotonesPedidos.add(btnEditarPedido);
        panelBotonesPedidos.add(btnEliminarPedido);
        panelBotonesPedidos.add(btnResumenVentas);
        
        panelPedidos.add(panelBotonesPedidos, BorderLayout.SOUTH);
    }
    
    // Métodos para actualizar las tablas
private void actualizarTablaClientes() {
    List<Cliente> clientes = controladora.traerClientes();
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
//    model.addColumn("Customer ID");
//    model.addColumn("Nombre");
//    model.addColumn("Teléfono");
    
    for (Cliente cliente : clientes) {
        model.addRow(new Object[]{
            cliente.getId()
//            cliente.getCustomerID()
//            cliente.getNombre(),
//            cliente.getTelefono()
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
    
//    private void editarCliente() {
//        int filaSeleccionada = tablaClientes.getSelectedRow();
//        if (filaSeleccionada == -1) {
//            JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar", 
//                    "Advertencia", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        
//        BigDecimal id = (BigDecimal) tablaClientes.getValueAt(filaSeleccionada, 0);
//        Cliente cliente = controladora.traerCliente(id.intValue());
//        
//        JPanel panel = new JPanel(new GridLayout(3, 2));
//        
//        JTextField txtCustomerID = new JTextField(cliente.getCustomerID().toString());
//        
//        panel.add(new JLabel("Customer ID:"));
//        panel.add(txtCustomerID);
//        
//        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", 
//                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//        
//        if (result == JOptionPane.OK_OPTION) {
//            try {
//                cliente.setCustomerID(new BigDecimal(txtCustomerID.getText()));
//                controladora.editarCliente(cliente);
//                actualizarTablaClientes();
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(this, "Error al editar cliente: " + e.getMessage(), 
//                        "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
    
    
    private void editarCliente() {
    int filaSeleccionada = tablaClientes.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Obtener ID del cliente seleccionado
        Long id = (Long) tablaClientes.getValueAt(filaSeleccionada, 0);
        
        // Obtener el cliente de la base de datos
        Cliente cliente = controladora.traerCliente(id.intValue());
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Panel de edición con campos actuales
        JPanel panel = new JPanel(new GridLayout(3, 2));
        
        JTextField txtCustomerID = new JTextField(cliente.getCustomerID().toString());
//        JTextField txtNombre = new JTextField(cliente.getNombre());
//        JTextField txtTelefono = new JTextField(cliente.getTelefono());
        
        panel.add(new JLabel("Customer ID:"));
        panel.add(txtCustomerID);
//        panel.add(new JLabel("Nombre:"));
//        panel.add(txtNombre);
//        panel.add(new JLabel("Teléfono:"));
//        panel.add(txtTelefono);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Actualizar los datos del cliente
            cliente.setCustomerID(new BigDecimal(txtCustomerID.getText()));
//            cliente.setNombre(txtNombre.getText());
//            cliente.setTelefono(txtTelefono.getText());
            
            // Guardar cambios
            controladora.editarCliente(cliente);
            actualizarTablaClientes();
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al editar cliente: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
//private void editarCliente() {
//    int filaSeleccionada = tablaClientes.getSelectedRow();
//    if (filaSeleccionada == -1) {
//        JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar", 
//                "Advertencia", JOptionPane.WARNING_MESSAGE);
//        return;
//    }
//
//    try {
//        // Obtener ID como Long
//        Long id = (Long) tablaClientes.getValueAt(filaSeleccionada, 0);
//        
//        // Verificar que el cliente existe
//        Cliente cliente = controladora.traerCliente(id.intValue());
//        if (cliente == null) {
//            JOptionPane.showMessageDialog(this, "Cliente no encontrado en la base de datos", 
//                    "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Panel de edición
//        JPanel panel = new JPanel(new GridLayout(1, 2));
//        JTextField txtCustomerID = new JTextField(cliente.getCustomerID().toString());
//        panel.add(new JLabel("Customer ID:"));
//        panel.add(txtCustomerID);
//
//        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente", 
//                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//        if (result == JOptionPane.OK_OPTION) {
//            cliente.setCustomerID(new BigDecimal(txtCustomerID.getText()));
//            controladora.editarCliente(cliente);
//            actualizarTablaClientes();
//            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente", 
//                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
//        }
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(this, "Error al editar cliente: " + e.getMessage(), 
//                "Error", JOptionPane.ERROR_MESSAGE);
//        e.printStackTrace();
//    }
//}
    
    
//    private void eliminarCliente() {
//        int filaSeleccionada = tablaClientes.getSelectedRow();
//        if (filaSeleccionada == -1) {
//            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar", 
//                    "Advertencia", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        
//        int confirmacion = JOptionPane.showConfirmDialog(this, 
//                "¿Está seguro que desea eliminar este cliente?", 
//                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
//        
//        if (confirmacion == JOptionPane.YES_OPTION) {
//            BigDecimal id = (BigDecimal) tablaClientes.getValueAt(filaSeleccionada, 0);
//            controladora.eliminarCliente(id.intValue());
//            actualizarTablaClientes();
//        }
//    }
    
private void eliminarCliente() {
    int filaSeleccionada = tablaClientes.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Obtener el ID del cliente seleccionado
    Long id = (Long) tablaClientes.getValueAt(filaSeleccionada, 0);
    
    // Confirmar eliminación
    int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea eliminar este cliente?\nEsta acción no se puede deshacer.", 
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    
    if (confirmacion == JOptionPane.YES_OPTION) {
        try {
            controladora.eliminarCliente(id.intValue());
            actualizarTablaClientes();
            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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
    JPanel panel = new JPanel(new GridLayout(6, 2));
    
    // Obtener listas para combobox
    List<Cliente> clientes = controladora.traerClientes();
    List<Producto> productos = controladora.traerProductos();
    List<Estado> estados = controladora.traerEstados();
    
    JComboBox<Cliente> cbCliente = new JComboBox<>(clientes.toArray(new Cliente[0]));
    JComboBox<Producto> cbProducto = new JComboBox<>(productos.toArray(new Producto[0]));
    JComboBox<Estado> cbEstado = new JComboBox<>(estados.toArray(new Estado[0]));
    JTextField txtCantidad = new JTextField();
    JTextField txtPrecioTotal = new JTextField();
    
    panel.add(new JLabel("Cliente:"));
    panel.add(cbCliente);
    panel.add(new JLabel("Producto:"));
    panel.add(cbProducto);
    panel.add(new JLabel("Estado:"));
    panel.add(cbEstado);
    panel.add(new JLabel("Cantidad:"));
    panel.add(txtCantidad);
    panel.add(new JLabel("Precio Total:"));
    panel.add(txtPrecioTotal);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Pedido", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            Pedido pedido = new Pedido();
            pedido.setCustomerID((Cliente) cbCliente.getSelectedItem());
            pedido.setStatusID((Estado) cbEstado.getSelectedItem());
            
            // Generar ID (podrías implementar una secuencia)
            pedido.setPedidoID(new BigDecimal(System.currentTimeMillis() % 10000));
            
            // Crear detalle del pedido
            DetallePedido detalle = new DetallePedido();
            Producto productoSeleccionado = (Producto) cbProducto.getSelectedItem();
            detalle.setProductID(productoSeleccionado);
            detalle.setCantidad(new BigDecimal(txtCantidad.getText()));
            detalle.setPrecioTotal(txtPrecioTotal.getText());
            
            // Establecer relaciones
            pedido.getDetallePedidoCollection().add(detalle);
            detalle.setPedidoID(pedido);
            
            controladora.crearPedido(pedido);
            actualizarTablaPedidos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar pedido: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
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
    List<Pedido> pedidos = controladora.traerPedidos();
    StringBuilder resumen = new StringBuilder();
    
    if (pedidos.isEmpty()) {
        resumen.append("No hay pedidos registrados.");
    } else {
        // Resumen general
        double totalVentas = pedidos.stream()
            .mapToDouble(p -> p.getTotalGeneral().doubleValue())
            .sum();
        
        resumen.append("Resumen General de Ventas:\n");
        resumen.append("Total Pedidos: ").append(pedidos.size()).append("\n");
        resumen.append("Total Ventas: $").append(String.format("%.2f", totalVentas)).append("\n\n");
        
        // Por cliente
        resumen.append("Ventas por Cliente:\n");
        controladora.traerClientes().forEach(cliente -> {
            double totalCliente = pedidos.stream()
                .filter(p -> p.getCustomerID().equals(cliente))
                .mapToDouble(p -> p.getTotalGeneral().doubleValue())
                .sum();
            
            if (totalCliente > 0) {
                resumen.append(cliente.getNombre())
                      .append(": $")
                      .append(String.format("%.2f", totalCliente))
                      .append("\n");
            }
        });
    }
    
    JOptionPane.showMessageDialog(this, resumen.toString(), 
            "Resumen de Ventas", JOptionPane.INFORMATION_MESSAGE);
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

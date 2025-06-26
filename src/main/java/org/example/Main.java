package org.example;

import java.util.List;
import javax.swing.JOptionPane;
import org.example.logica.Controladora;
import org.example.logica.DetallePedido;
import org.example.logica.Estado;
import org.example.logica.Vendedor;
import javax.swing.SwingUtilities;
import org.example.Visuales.MainGUI;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import org.example.logica.*;
public class Main{       
//        Controladora control = new Controladora();
//        
//        List<Vendedor> vendedor = control.traerVendedores();
//        for(Vendedor e : vendedor)
//            System.out.println("Pedido: " + e.toString());
public static void main(String[] args) {
        // Asegurarse de ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                Controladora controladora = new Controladora();
                MainGUI gui = new MainGUI(controladora);
                gui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error al iniciar la aplicación: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }   
   
//        Controladora control = new Controladora();
//        
//        List<Vendedor> vendedor = control.traerVendedores();
//        for(Vendedor e : vendedor)
//            System.out.println("Pedido: " + e.toString());

//        List<Vendedor> vendedor = control.traerVendedores();
//        for(Vendedor e : vendedor)
//            System.out.println("Pedido: " + e.toString());

//        Conexion conn = new Conexion();
//       
////         // Insertar una nuevo producto
////        boolean exito = conn.insertarProductos(10, "Remera Termica", "Remera", "Indumentaria", "30000");
//////        
////        if (exito) {
////            System.out.println("¡Producto insertada correctamente!");
////        } else {
////            System.out.println("Error al insertar.");
////        }
//        
//        
////        conn.mostrarProductos();
////        conn.mostrarClientes();
//
//            // Primero probamos buscar un cliente existente
//            System.out.println("\n=== Buscando cliente existente ===");
//            conn.buscarClientePorID(29488);  // Cambia el ID por uno que exista en tu BD
//            
//            // Probamos buscar un cliente que no existe
//            System.out.println("\n=== Buscando cliente inexistente ===");
//            conn.buscarClientePorID(9999);  // ID que probablemente no exista
//            
//            // Ahora probamos eliminar un cliente
//            System.out.println("\n=== Probando eliminar cliente ===");
//            int clienteAEliminar = 10;  // Cambia por un ID que puedas eliminar
//            
//            // Primero verificamos si existe
//            System.out.println("Verificando existencia del cliente...");
//            conn.buscarClientePorID(clienteAEliminar);
//            
//            // Intentamos eliminar
//            boolean eliminado = conn.eliminarCliente(clienteAEliminar);
//            
//            if (eliminado) {
//                System.out.println("¡Cliente eliminado correctamente!");
//                
//                // Verificamos que ya no existe
//                System.out.println("\nVerificando eliminación...");
//                conn.buscarClientePorID(clienteAEliminar);
//            } else {
//                System.out.println("No se pudo eliminar el cliente (puede que no exista)");
//            }
            
    }

/*
ejemplo clase hilo ( las funciones estan en la clase controladora
public class PedidoThread extends Thread{
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private Controladora control;

    public PedidoThread(Cliente cliente, Producto producto, int cantidad, Controladora control) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.control = control;
    }
    
    @Override
    public void run(){
        control.realizarPedidoSeguro(cliente,producto,cantidad);
    }

}

ejemplo main con los hilos
public class Preparcial {

    public static void main(String[] args) {
        Controladora dao = new Controladora();

        // Simular clientes y productos cargados previamente
        Cliente cliente1 = new Cliente( "Camila", "cami@mail.com");
        Cliente cliente2 = new Cliente( "Juan", "juan@mail.com");
        Cliente cliente3 = new Cliente("Laura", "laura@mail.com");
        Cliente cliente4 = new Cliente("Pedro", "pedro@mail.com");
        Cliente cliente5 = new Cliente("Lucia", "lucia@mail.com");
        dao.crearCliente(cliente1);
        dao.crearCliente(cliente2);
        dao.crearCliente(cliente3);
        dao.crearCliente(cliente4);
        dao.crearCliente(cliente5);
        Producto prod1 = new Producto( "Notebook", 25, 10);
        Producto prod2 = new Producto( "Mouse", 5 , 5);
        dao.crearProducto(prod1);
        dao.crearProducto(prod2);

        // Hilos simulando pedidos
        PedidoThread h1 = new PedidoThread(cliente1, prod1, 1, dao);
        PedidoThread h2 = new PedidoThread(cliente2, prod2, 2, dao);
        PedidoThread h3 = new PedidoThread(cliente1, prod2, 3, dao);
        PedidoThread h4 = new PedidoThread(cliente2, prod1, 1, dao);
        PedidoThread h5 = new PedidoThread(cliente1, prod1, 2, dao);
        PedidoThread h6 = new PedidoThread(cliente3, prod1, 1, dao);
        PedidoThread h7 = new PedidoThread(cliente4, prod2, 2, dao);
        PedidoThread h8 = new PedidoThread(cliente5, prod2, 1, dao);
        PedidoThread h9 = new PedidoThread(cliente3, prod2, 2, dao);
        PedidoThread h10 = new PedidoThread(cliente4, prod1, 1, dao);

        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();
        h6.start();
        h7.start();
        h8.start();
        h9.start();
        h10.start();

        try {
            h1.join(); h2.join(); h3.join(); h4.join(); h5.join();h6.join(); h7.join(); h8.join(); h9.join(); h10.join();
        } catch (InterruptedException e) {
        }

        dao.mostrarResumenVentasCliente();
        // Mostrar resumen
        dao.mostrarResumenVentas();
    }
}
*/
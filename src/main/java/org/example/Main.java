package org.example;

public class Main
{
    public static void main(String[] args)
    {

        Conexion conn = new Conexion();
       
//         // Insertar una nuevo producto
//        boolean exito = conn.insertarProductos(10, "Remera Termica", "Remera", "Indumentaria", "30000");
////        
//        if (exito) {
//            System.out.println("¡Producto insertada correctamente!");
//        } else {
//            System.out.println("Error al insertar.");
//        }
        
        
//        conn.mostrarProductos();
//        conn.mostrarClientes();

            // Primero probamos buscar un cliente existente
            System.out.println("\n=== Buscando cliente existente ===");
            conn.buscarClientePorID(29488);  // Cambia el ID por uno que exista en tu BD
            
            // Probamos buscar un cliente que no existe
            System.out.println("\n=== Buscando cliente inexistente ===");
            conn.buscarClientePorID(9999);  // ID que probablemente no exista
            
            // Ahora probamos eliminar un cliente
            System.out.println("\n=== Probando eliminar cliente ===");
            int clienteAEliminar = 10;  // Cambia por un ID que puedas eliminar
            
            // Primero verificamos si existe
            System.out.println("Verificando existencia del cliente...");
            conn.buscarClientePorID(clienteAEliminar);
            
            // Intentamos eliminar
            boolean eliminado = conn.eliminarCliente(clienteAEliminar);
            
            if (eliminado) {
                System.out.println("¡Cliente eliminado correctamente!");
                
                // Verificamos que ya no existe
                System.out.println("\nVerificando eliminación...");
                conn.buscarClientePorID(clienteAEliminar);
            } else {
                System.out.println("No se pudo eliminar el cliente (puede que no exista)");
            }
            
    }
}
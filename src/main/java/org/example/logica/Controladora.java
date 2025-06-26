package org.example.logica;

import java.util.List;
import org.example.Persistencia.ControladoraPersistencia;

public class Controladora {
    ControladoraPersistencia controlP = new ControladoraPersistencia();
    
     //________________CLIENTE______________________________________
    public void crearCliente(Cliente cliente){
        controlP.crearCliente(cliente);
    }
    public void eliminarCliente(int id){
        controlP.eliminarCliente(id);
    }
    public void  editarCliente(Cliente cliente){
        controlP.editarCliente(cliente);
    }
    public Cliente traerCliente(int id){
        return controlP.traerCliente(id);
    }
    public List<Cliente> traerClientes(){
        return controlP.traerClientes();
    }
     //________________DETALLEPEDIDO______________________________________
    public void crearDetallePedido(DetallePedido detallepedido){
        controlP.crearDetallePedido(detallepedido);
    }
    public void eliminarDetallePedido(int id){
        controlP.eliminarDetallePedido(id);
    }
    public void  editarDetallePedido(DetallePedido detallepedido){
        controlP.editarDetallePedido(detallepedido);
    }
    public DetallePedido traerDetallePedido(int id){
        return controlP.traerDetallePedido(id);
    }
    public List<DetallePedido> traerDetallePedidos(){
        return controlP.traerDetallePedidos();
    }
     //________________ESTADO______________________________________
    public void crearEstado(Estado estado){
        controlP.crearEstado(estado);
    }
    public void eliminarEstado(int id){
        controlP.eliminarEstado(id);
    }
    public void  editarEstado(Estado estado){
        controlP.editarEstado(estado);
    }
    public Estado traerEstado(int id){
        return controlP.traerEstado(id);
    }
    public List<Estado> traerEstados(){
        return controlP.traerEstados();
    }
     //________________PEDIDO______________________________________
    public void crearPedido(Pedido pedido){
        controlP.crearPedido(pedido);
    }
    public void eliminarPedido(int id){
        controlP.eliminarPedido(id);
    }
    public void  editarPedido(Pedido pedido){
        controlP.editarPedido(pedido);
    }
    public Pedido traerPedido(int id){
        return controlP.traerPedido(id);
    }
    public List<Pedido> traerPedidos(){
        return controlP.traerPedidos();
    }
     //________________PRODUCTO______________________________________
    public void crearProducto(Producto producto){
        controlP.crearProducto(producto);
    }
    public void eliminarProducto(int id){
        controlP.eliminarProducto(id);
    }
    public void  editarProducto(Producto producto){
        controlP.editarProducto(producto);
    }
    public Producto traerProducto(int id){
        return controlP.traerProducto(id);
    }
    public List<Producto> traerProductos(){
        return controlP.traerProductos();
    }
     //________________TERRITORIO______________________________________
    public void crearTerritorio(Territorio territorio){
        controlP.crearTerritorio(territorio);
    }
    public void eliminarTerritorio(int id){
        controlP.eliminarTerritorio(id);
    }
    public void  editarTerritorio(Territorio territorio){
        controlP.editarTerritorio(territorio);
    }
    public Territorio traerTerritorio(int id){
        return controlP.traerTerritorio(id);
    }
    public List<Territorio> traerTerritorios(){
        return controlP.traerTerritorios();
    }
     //________________VENDEDOR______________________________________
    public void crearVendedor(Vendedor vendedor){
        controlP.crearVendedor(vendedor);
    }
    public void eliminarVendedor(int id){
        controlP.eliminarVendedor(id);
    }
    public void  editarVendedor(Vendedor vendedor){
        controlP.editarVendedor(vendedor);
    }
    public Vendedor traerVendedor(int id){
        return controlP.traerVendedor(id);
    }
    public List<Vendedor> traerVendedores(){
        return controlP.traerVendedores();
    }
    
    /*
        //________________PEDIDO SEGURO_____________________________________
    public synchronized void realizarPedidoSeguro(Cliente cliente, Producto producto, int cantidad) {
        if (producto.getStock() >= cantidad) {
            producto.setStock(producto.getStock() - cantidad);
            this.editarProducto(producto);

            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setProducto(producto);
            pedido.setCantidad(cantidad);
            this.crearPedido(pedido);

            System.out.println("Pedido registrado: " + cliente.getNombre()
                   + " | Producto: " + producto.getNombre()
                   + " | Cantidad: " + cantidad
                   + " | Stock restante: " + producto.getStock());
        } else {
            System.out.println("Stock insuficiente para " + cliente.getNombre()
                   + " | Producto: " + producto.getNombre()
                   + " | Solicitado: " + cantidad
                   + " | Disponible: " + producto.getStock());
        }
}
//________________RESUMEN VENTA POR CLIENTE_____________________________________ 
    public void mostrarResumenVentasCliente() {
        List<Cliente> clientes = this.traerClientes();
        List<Pedido> pedidos = this.traerPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos realizados.");
            return;
        }

        System.out.println("RESUMEN DE VENTAS POR CLIENTE:");

        for (Cliente cliente : clientes) {
            int cantidadPedidos = 0;
            int unidadesCompradas = 0;
            double totalGastado = 0.0;
            for (Pedido pedido : pedidos) {
                if (pedido.getCliente().getId() == cliente.getId()) {
                    cantidadPedidos++;
                    unidadesCompradas += pedido.getCantidad();
                    totalGastado += pedido.getCantidad() * pedido.getProducto().getPrecio();
                }
            }
            if (cantidadPedidos > 0) {
                System.out.println("Cliente: " + cliente.getNombre()
                        + " | Pedidos: " + cantidadPedidos
                        + " | Unidades: " + unidadesCompradas
                        + " | Total gastado: $" + String.format("%.2f", totalGastado));
            }
        }
}
//________________RESUMEN VENTA TOTAL_____________________________________
    public void mostrarResumenVentas() {
        List<Pedido> pedidos = this.traerPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos realizados.");
            return;
        }

        int totalPedidos = pedidos.size();
        int totalUnidadesVendidas = 0;
        double totalVentas = 0;
        for (Pedido p : pedidos) {
            int cantidad = p.getCantidad();
            totalUnidadesVendidas += cantidad;
            double precioUnitario = p.getProducto().getPrecio();
            totalVentas += cantidad * precioUnitario;
        }

        System.out.println("Resumen de Ventas:");
        System.out.println("Total de pedidos: " + totalPedidos);
        System.out.println("Total unidades vendidas: " + totalUnidadesVendidas);
        System.out.println("Total ventas: $" + totalVentas);
    }

    */
}

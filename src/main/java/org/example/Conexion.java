
package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Conexion {
    
    private Connection conn;
    
    
    public Conexion(){
    
        conn=null;
        this.conectar();
    }
    
    
    public void conectar(){
    
        try {
            conn= DriverManager.getConnection("jdbc:sqlserver://localhost\\\\pruebasql:1433;databaseName=GestionDeVentas_BD;encrypt=false;trustServerCertificate=true;user=sa;password=42164319A");
            
            System.out.println("Se establecio la conexion con la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos. Error: "+ e.getMessage());
        
        }
        
    }
    
    public Connection getConexion(){
    
    return conn;
    }
    
    
//---------------------------------PRODUCTOS---------------------------------------------------------------------------
    public boolean insertarProductos(int idProducto , String producto, String subCategoriaProducto,String productoCategoria,String precioIndividual) {
    String sql = "INSERT INTO dbo.Producto (ProductID, Product,ProductSubCategory,ProductCategory,PrecioIndividual) VALUES (?, ?,?,?,?)";
    
    try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
        pstmt.setInt(1, idProducto);
        pstmt.setString(2, producto);
        pstmt.setString(3, subCategoriaProducto);
        pstmt.setString(4, productoCategoria);
        pstmt.setString(5, precioIndividual);
        
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al insertar: " + e.getMessage());
        return false;
    }
}
    
 public void mostrarProductos() {
    try (Statement stmt = this.conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM dbo.Producto")) {
        
        System.out.println("ID | Producto | Precio ");
        System.out.println("-----------------------------------------------");
        
        while (rs.next()) {
            System.out.println(
                rs.getInt("ProductID") + " " +
                rs.getString("Product") + " " +
                rs.getString("PrecioIndividual") + "  " 
            );       
        }
    } catch (SQLException e) {
        System.err.println("Error al mostrar Productos: " + e.getMessage());
    }
}
//---------------------------------CLIENTES---------------------------------------------------------------------------
     public boolean insertarCliente(int idCliente) {
    String sql = "INSERT INTO dbo.Cliente (CustomerID) VALUES (?)";
    
    try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
        pstmt.setInt(1, idCliente);
        
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al insertar: " + e.getMessage());
        return false;
    }
}  
 
 
 public void mostrarClientes() {
    try (Statement stmt = this.conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM dbo.Cliente")) {
        
        System.out.println("ID | ");
        System.out.println("-----------------------------------------------");
        
        while (rs.next()) {
            System.out.println(
                rs.getInt("CustomerID") + " " 
            );       
        }
    } catch (SQLException e) {
        System.err.println("Error al mostrar Clientes: " + e.getMessage());
    }
}
    
 // Buscar cliente por ID
public void buscarClientePorID(int customerID) {
    String sql = "SELECT * FROM dbo.Cliente WHERE CustomerID = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, customerID);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            System.out.println("\nCliente encontrado:");
            System.out.println("ID: " + rs.getInt("CustomerID"));
        } else {
            System.out.println("No se encontró cliente con ID: " + customerID);
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar cliente: " + e.getMessage());
    }
}
 
// Eliminar cliente
public boolean eliminarCliente(int customerID) {
    String sql = "DELETE FROM dbo.Cliente WHERE CustomerID = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, customerID);
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al eliminar cliente: " + e.getMessage());
        return false;
    }
}

 //---------------------------------CLIENTES---------------------------------------------------------------------------   
}

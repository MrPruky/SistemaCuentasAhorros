/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Lenovo
 */
public class ConexionDB {
    //atributos
    private String host;
    private int puerto;
    private String baseDatos;
    private String usuario;
    private String contraseña;
    protected Connection conexion;
    
    //constructor
    public ConexionDB() {
        this.host = "pg-2a484bb5-info-f47d.b.aivencloud.com";
        this.puerto = 17547;
        this.baseDatos = "CuentasAhorro";
        this.usuario = "avnadmin";
        this.contraseña = "AVNS_2RsbKJpXNi2dk7FGpFf";
        this.conexion = null;
    }
    public ConexionDB(String host, int puerto, String baseDatos, String usuario, String contraseña) {
        this.host = host;
        this.puerto = puerto;
        this.baseDatos = baseDatos;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.conexion = null;
    }
    
    //metodos
    public boolean conectar(){
        String url = "jdbc:postgresql://" + host + ":" + puerto + "/" + baseDatos;
    try {conexion = DriverManager.getConnection(url, usuario, contraseña);
        System.out.println("Conectado a " + baseDatos);
        return true;} 
    catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
        return false;}}
    
    public void desconectar(){
    try {if (conexion != null && !conexion.isClosed()) {conexion.close();
        System.out.println("Desconectado correctamente.");}} 
    catch (SQLException e) {
        System.out.println("Error al desconectar: " + e.getMessage());
}
}
}

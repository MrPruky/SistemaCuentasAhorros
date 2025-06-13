/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author Lenovo
 */
public class Cuenta extends ConexionDB {
    //atributos
    private int idCuenta;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private double capital;
    private String correo;
    private String fechaRegistro;
    //constructor
    public Cuenta() {
        this.idCuenta = 0;
        this.nombre = "";
        this.apellidoPaterno = "";
        this.apellidoMaterno = "";
        this.capital = 0.0;
        this.correo = "";
        this.fechaRegistro = "";
    }
    //propiedades

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    //método
    public boolean insertar(){
          PreparedStatement comando = null;
          try{
              //conectarnos al servidor
              super.conectar();
              //declarar sentencia sql
              String sql="INSERT INTO cuentas(nombre,apellido_Paterno,apellido_Materno,capital,email) VALUES(?,?,?,?,?)";
              comando = this.conexion.prepareStatement(sql);
              //agregar los datos de cada parametro
              comando.setString(1,this.nombre);
              comando.setString(2,this.apellidoPaterno);
              comando.setString(3,this.apellidoMaterno);
              comando.setDouble(4,this.capital);
              comando.setString(5,this.correo);
              //ejecutar sentencia INSERT en el servidor
              comando.executeUpdate();
              return true;
          }catch(SQLException e){
              System.out.println("Error al insertar " + e.getMessage());
              return false;
          }finally{
              try{
                  if(comando!=null){
                      comando.close();
                  }
                  if(this.conexion!=null){
                      super.desconectar();
                  }
              }catch(SQLException e){
                  System.out.println("Error al liberal recursos" + e.getMessage());
              }
          }
    }
    
    public boolean actualizar(){
        PreparedStatement stmt=null;
        try{ 
           super.conectar();
            String sql="UPDATE cuentas SET nombre=?,apellido_paterno=?,"
                + "apellido_materno=?,capital=?,email=? WHERE id_cuenta=?";
            stmt=conexion.prepareStatement(sql);
            stmt.setString(1, this.nombre);
            stmt.setString(2, this.apellidoPaterno);
            stmt.setString(3, this.apellidoMaterno);
            stmt.setDouble(4, this.capital);
            stmt.setString(5, this.correo);
            stmt.setInt(6, idCuenta);
           return  stmt.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error al actualizar" + e.getMessage());
            return false;
        }finally {
            try {
                if(stmt!=null) stmt.close();
                if(this.conexion!=null)super.desconectar();
            }catch (SQLException e){
                System.out.println("Error al cerrar los recursos:" + e.getMessage());
            }
        }
    }
    
    public boolean eliminar(){
        PreparedStatement stmt=null;
        try{ 
           super.conectar();
            String sql="DELETE FROM cuentas WHERE id_cuenta=?";
            stmt=conexion.prepareStatement(sql);
            stmt.setInt(1, idCuenta);
           return  stmt.executeUpdate()>0;
        }catch(SQLException e){
            System.out.println("Error al eliminar" + e.getMessage());
            return false;
        }finally {
            try {
                if(stmt!=null) stmt.close();
                if(this.conexion!=null)super.desconectar();
            }catch (SQLException e){
                System.out.println("Error al cerrar los recursos:" + e.getMessage());
            }
        }
    }
    public boolean buscar(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        boolean encontrado=false;
        try{ 
           super.conectar();
            String sql="SELECT * FROM cuentas WHERE id_cuenta=?";
             stmt=this.conexion.prepareStatement(sql);
             stmt.setInt(1, this.idCuenta);
             rs=stmt.executeQuery();
             if(rs.next()){
                   this.nombre=rs.getString("nombre");
                   this.apellidoPaterno=rs.getString("apellido_paterno");
                   this.apellidoMaterno=rs.getString("apellido_materno");
                   this.capital=rs.getDouble("capital");
                   this.correo=rs.getString("email");
                   this.fechaRegistro=rs.getString("fecha_registro");
                   encontrado=true;
             }
        }catch(SQLException e){
            System.out.println("Error al buscar" + e.getMessage());
        }finally {
            try {
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                if(this.conexion!=null)super.desconectar();
            }catch (SQLException e){
                System.out.println("Error al cerrar los recursos:" + e.getMessage());
            }
        }
        return encontrado;
    }
     public DefaultTableModel listadoGeneral(){
        String []columnas={"ID Cuenta","Nombre","Ap Paterno", "Ap Materno","Capita","Email","Fecha Registro"};
        DefaultTableModel modelo=new DefaultTableModel(columnas,0);
        String sql="SELECT * FROM cuentas";
        Statement stmt=null;
        ResultSet rs=null;
        try{ 
           super.conectar();
             stmt=this.conexion.createStatement();
             rs=stmt.executeQuery(sql);
             while(rs.next()){
                 Object [] fila={
                   rs.getInt("id_cuenta"),
                   rs.getString("nombre"),
                   rs.getString("apellido_paterno"),
                   rs.getString("apellido_materno"),
                   rs.getDouble("capital"),
                   rs.getString("email"),
                   rs.getString("fecha_registro")
                 };
                 modelo.addRow(fila);
             }
        }catch(SQLException e){
            System.out.println("Error al consultar" + e.getMessage());
        }finally {
            try {
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                if(this.conexion!=null)super.desconectar();
            }catch (SQLException e){
                System.out.println("Error al cerrar los recursos:" + e.getMessage());
            }
        }
        return modelo;
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date; // Para manejar la fecha de movimiento en Java (java.util.Date)
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat; // Necesario para formatear la fecha a String para la tabla

/**
 * Clase para interactuar con la tabla 'movimientos' en la base de datos.
 * NO HEREDA de Cuenta, sino que se relaciona por 'idCuenta'.
 * @author Lenovo
 */
public class Movimiento extends ConexionDB { // Asegúrate que ConexionDB es tu clase base para la conexión

    // Atributos de un movimiento (mapeados a los nombres de las columnas en la DB)
    private int idMovimiento; // Corresponde a 'id_movimientos' en la DB
    private int idCuenta; // Corresponde a 'id_cuenta' en la DB
    private String tipoMovimiento; // Corresponde a 'tipo' en la DB (CHAR(1))
    private double importe; // Corresponde a 'cantidad' en la DB
    private Date fechaMovimiento; // Corresponde a 'fecha' en la DB

    // Constructor vacío
    public Movimiento() {
        this.idMovimiento = 0;
        this.idCuenta = 0;
        this.tipoMovimiento = "";
        this.importe = 0.0;
        this.fechaMovimiento = null;
    }

    // Propiedades (Getters y Setters)
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    // --- Métodos CRUD ---

    /**
     * Inserta un nuevo movimiento en la base de datos.
     * Asume que idCuenta, tipoMovimiento, importe y fechaMovimiento están seteados en el objeto.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insertar() {
        PreparedStatement pstmt = null;
        try {
            super.conectar();
            // Nombres de columnas ajustados para coincidir con la DB: tipo, cantidad, fecha
            String sql = "INSERT INTO movimientos(id_cuenta, tipo, cantidad, fecha) VALUES(?,?,?,?)";
            pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, this.idCuenta);
            pstmt.setString(2, this.tipoMovimiento); // Mapea tipoMovimiento a la columna 'tipo'
            pstmt.setDouble(3, this.importe); // Mapea importe a la columna 'cantidad'
            
            // Usar setTimestamp para java.util.Date para compatibilidad con TIMESTAMP en DB
            if (this.fechaMovimiento != null) {
                pstmt.setTimestamp(4, new java.sql.Timestamp(this.fechaMovimiento.getTime())); // Mapea fechaMovimiento a la columna 'fecha'
            } else {
                pstmt.setNull(4, java.sql.Types.TIMESTAMP); // O el tipo de SQL apropiado si es obligatorio
            }

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar movimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (this.conexion != null) super.desconectar();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos después de insertar movimiento: " + e.getMessage());
            }
        }
    }

    /**
     * Actualiza un movimiento existente en la base de datos por su ID.
     * Asume que idMovimiento, idCuenta, tipoMovimiento, importe, fechaMovimiento ya están seteados en el objeto.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizar() {
        PreparedStatement pstmt = null;
        try {
            super.conectar();
            // Nombres de columnas ajustados: tipo -> tipo, importe -> cantidad, fecha_movimiento -> fecha
            // Clave primaria ajustada: id_movimiento -> id_movimientos
            String sql = "UPDATE movimientos SET id_cuenta=?, tipo=?, cantidad=?, fecha=? WHERE id_movimientos=?";
            pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, this.idCuenta);
            pstmt.setString(2, this.tipoMovimiento); // Mapea tipoMovimiento a la columna 'tipo'
            pstmt.setDouble(3, this.importe); // Mapea importe a la columna 'cantidad'
            
            // Convertir java.util.Date a java.sql.Timestamp para la DB
            if (this.fechaMovimiento != null) {
                pstmt.setTimestamp(4, new java.sql.Timestamp(this.fechaMovimiento.getTime())); // Mapea fechaMovimiento a la columna 'fecha'
            } else {
                pstmt.setNull(4, java.sql.Types.TIMESTAMP);
            }
            
            pstmt.setInt(5, this.idMovimiento); // Mapea idMovimiento a la columna 'id_movimientos'

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar movimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (this.conexion != null) super.desconectar();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos después de actualizar movimiento: " + e.getMessage());
            }
        }
    }

    /**
     * Elimina un movimiento de la base de datos por su ID.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminar() {
        PreparedStatement pstmt = null;
        try {
            super.conectar();
            // Clave primaria ajustada: id_movimiento -> id_movimientos
            String sql = "DELETE FROM movimientos WHERE id_movimientos=?";
            pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, this.idMovimiento); // Mapea idMovimiento a la columna 'id_movimientos'

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar movimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (this.conexion != null) super.desconectar();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos después de eliminar movimiento: " + e.getMessage());
            }
        }
    }

    /**
     * Busca un movimiento por su ID y carga sus datos en el objeto actual.
     * @return true si el movimiento fue encontrado, false en caso contrario.
     */
    public boolean buscar() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean encontrado = false;
        try {
            super.conectar();
            // Nombres de columnas ajustados: id_movimiento -> id_movimientos, tipo_movimiento -> tipo, importe -> cantidad, fecha_movimiento -> fecha
            String sql = "SELECT id_movimientos, id_cuenta, tipo, cantidad, fecha FROM movimientos WHERE id_movimientos = ?";
            pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, this.idMovimiento); // Mapea idMovimiento a la columna 'id_movimientos'
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.idCuenta = rs.getInt("id_cuenta"); // Lee de la columna 'id_cuenta'
                this.tipoMovimiento = rs.getString("tipo"); // Lee de la columna 'tipo'
                this.importe = rs.getDouble("cantidad"); // Lee de la columna 'cantidad'
                // Obtener la fecha como java.util.Date directamente (usar getTimestamp para TIMESTAMP en DB)
                this.fechaMovimiento = rs.getTimestamp("fecha"); // Lee de la columna 'fecha'
                encontrado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar movimiento: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (this.conexion != null) super.desconectar();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos después de buscar movimiento: " + e.getMessage());
            }
        }
        return encontrado;
    }

    /**
     * Devuelve un DefaultTableModel con los movimientos filtrados por id_cuenta.
     * @param idCuenta El ID de la cuenta para filtrar los movimientos.
     * @return DefaultTableModel con los datos de los movimientos.
     */
    public DefaultTableModel listadoMovimientos(int idCuenta) {
        String[] columnas = {"ID Movimiento", "ID Cuenta", "Tipo", "Importe", "Fecha"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
             @Override
            public boolean isCellEditable(int row, int column) {
               return false; // Las celdas de la tabla de listado no son editables
            }
        };

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            super.conectar();
            // Nombres de columnas ajustados: id_movimiento -> id_movimientos, tipo_movimiento -> tipo, importe -> cantidad, fecha_movimiento -> fecha
            String sql = "SELECT id_movimientos, id_cuenta, tipo, cantidad, fecha FROM movimientos WHERE id_cuenta = ? ORDER BY fecha DESC";
            pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idCuenta);
            rs = pstmt.executeQuery();

            // SimpleDateFormat para formatear la fecha a String para mostrar en la tabla
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_movimientos"), // Lee de 'id_movimientos'
                    rs.getInt("id_cuenta"), // Lee de 'id_cuenta'
                    rs.getString("tipo"), // Lee de la columna 'tipo'
                    rs.getDouble("cantidad"), // Lee de la columna 'cantidad'
                    sdf.format(rs.getTimestamp("fecha")) // Lee de la columna 'fecha' y formatear
                };
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar listado de movimientos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (this.conexion != null) super.desconectar();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos después de listadoMovimientos: " + e.getMessage());
            }
        }
        return modelo;
    }

    /**
     * Calcula el total de depósitos para una cuenta específica.
     * @param idCuenta El ID de la cuenta.
     * @return El total de depósitos.
     */
    public double getTotalDepositos(int idCuenta) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double total = 0.0;
        try {
            super.conectar();
            // Ajustado a columna 'cantidad' y 'tipo' con valor 'D' (asumiendo 'D' para Depósito, dado CHAR(1) en DB)
            String sql = "SELECT SUM(cantidad) AS total FROM movimientos WHERE id_cuenta = ? AND tipo = 'D'";
            pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idCuenta);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular total de depósitos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (this.conexion != null) super.desconectar();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos después de getTotalDepositos: " + e.getMessage());
            }
        }
        return total;
    }

    /**
     * Calcula el total de retiros para una cuenta específica.
     * @param idCuenta El ID de la cuenta.
     * @return El total de retiros.
     */
    public double getTotalRetiros(int idCuenta) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double total = 0.0;
        try {
            super.conectar();
            // Ajustado a columna 'cantidad' y 'tipo' con valor 'R' (asumiendo 'R' para Retiro, dado CHAR(1) en DB)
            String sql = "SELECT SUM(cantidad) AS total FROM movimientos WHERE id_cuenta = ? AND tipo = 'R'";
            pstmt = this.conexion.prepareStatement(sql);
            pstmt.setInt(1, idCuenta);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular total de retiros: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (this.conexion != null) super.desconectar();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos después de getTotalRetiros: " + e.getMessage());
            }
        }
        return total;
    }
}
package SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class Movimientos extends Cuenta {
    // Atributos propios de la clase
    private int idMovimiento;
    private String fecha;
    private String tipo; // "Depósito" o "Retiro"
    private double cantidad;

    // Constructor vacío
    public Movimientos() {
        super(); // Llama al constructor de Cuenta
        this.idMovimiento = 0;
        this.fecha = "";
        this.tipo = "";
        this.cantidad = 0.0;
    }

    // Getters y Setters
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    // Método para insertar un movimiento
    public boolean insertarMovimiento() {
        PreparedStatement stmt = null;
        try {
            super.conectar();
            String sql = "INSERT INTO movimientos(id_cuenta, fecha, tipo, cantidad) VALUES (?, ?, ?, ?)";
            stmt = this.conexion.prepareStatement(sql);
            stmt.setInt(1, this.getIdCuenta());
            stmt.setString(2, this.fecha);
            stmt.setString(3, this.tipo);
            stmt.setDouble(4, this.cantidad);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar movimiento: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (this.conexion != null) super.desconectar();
            } catch (SQLException e) {
                System.out.println("Error al liberar recursos: " + e.getMessage());
            }
        }
    }

    // Método para obtener el listado de movimientos de una cuenta específica
    public DefaultTableModel listadoMovimientos() {
        String[] columnas = {"ID Movimiento", "ID Cuenta", "Fecha", "Tipo", "Cantidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            super.conectar();
            String sql = "SELECT * FROM movimientos WHERE id_cuenta = ?";
            stmt = this.conexion.prepareStatement(sql);
            stmt.setInt(1, this.getIdCuenta());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_movimiento"),
                    rs.getInt("id_cuenta"),
                    rs.getString("fecha"),
                    rs.getString("tipo"),
                    rs.getDouble("cantidad")
                };
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener movimientos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (this.conexion != null) super.desconectar();
            } catch (SQLException e) {
                System.out.println("Error al liberar recursos: " + e.getMessage());
            }
        }

        return modelo;
    }
    
    public boolean actualizarMovimiento() {
    PreparedStatement stmt = null;
    try {
        super.conectar();
        String sql = "UPDATE movimientos SET fecha = ?, tipo = ?, cantidad = ? WHERE id_movimiento = ?";
        stmt = this.conexion.prepareStatement(sql);
        stmt.setString(1, this.fecha);
        stmt.setString(2, this.tipo);
        stmt.setDouble(3, this.cantidad);
        stmt.setInt(4, this.idMovimiento);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Error al actualizar movimiento: " + e.getMessage());
        return false;
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (this.conexion != null) super.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al liberar recursos: " + e.getMessage());
        }
    }
}
}
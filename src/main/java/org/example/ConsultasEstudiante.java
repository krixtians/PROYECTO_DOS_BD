package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultasEstudiante {

    // Crea la tabla si no existe
    public static void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS Estudiantes (" +
                "id_estudiante INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "apellido TEXT NOT NULL, " +
                "correo TEXT UNIQUE NOT NULL, " +
                "fecha_nacimiento TEXT);";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
            System.out.println("✅ Tabla Estudiantes creada o verificada.");
        } catch (SQLException e) {
            System.out.println("❌ Error creando tabla Estudiantes: " + e.getMessage());
        }
    }

    // Insertar estudiante
    public boolean insertar(String nombre, String apellido, String correo, String fechaNacimiento) {
        String sql = "INSERT INTO Estudiantes(nombre, apellido, correo, fecha_nacimiento) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, fechaNacimiento);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar estudiante: " + e.getMessage());
            return false;
        }
    }

    // Listar estudiantes
    public List<String> listarTodos() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT * FROM Estudiantes";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(rs.getInt("id_estudiante") + " | " +
                        rs.getString("nombre") + " " +
                        rs.getString("apellido") + " | " +
                        rs.getString("correo") + " | " +
                        rs.getString("fecha_nacimiento"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar estudiantes: " + e.getMessage());
        }
        return lista;
    }

    // Actualizar estudiante
    public boolean actualizar(int id, String nombre, String apellido, String correo, String fechaNacimiento) {
        String sql = "UPDATE Estudiantes SET nombre=?, apellido=?, correo=?, fecha_nacimiento=? WHERE id_estudiante=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, fechaNacimiento);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar estudiante: " + e.getMessage());
            return false;
        }
    }

    // Eliminar estudiante
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Estudiantes WHERE id_estudiante=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }
}

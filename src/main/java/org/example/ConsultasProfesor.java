package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultasProfesor {

    public static void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS Profesores (" +
                "id_profesor INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "apellido TEXT NOT NULL, " +
                "especialidad TEXT);";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
            System.out.println("✅ Tabla Profesores creada o verificada.");
        } catch (SQLException e) {
            System.out.println("❌ Error creando tabla Profesores: " + e.getMessage());
        }
    }

    public boolean insertar(String nombre, String apellido, String especialidad) {
        String sql = "INSERT INTO Profesores(nombre, apellido, especialidad) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, especialidad);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar profesor: " + e.getMessage());
            return false;
        }
    }

    public List<String> listarTodos() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT * FROM Profesores";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(rs.getInt("id_profesor") + " | " +
                        rs.getString("nombre") + " " +
                        rs.getString("apellido") + " | " +
                        rs.getString("especialidad"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar profesores: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(int id, String nombre, String apellido, String especialidad) {
        String sql = "UPDATE Profesores SET nombre=?, apellido=?, especialidad=? WHERE id_profesor=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, especialidad);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar profesor: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Profesores WHERE id_profesor=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar profesor: " + e.getMessage());
            return false;
        }
    }
}

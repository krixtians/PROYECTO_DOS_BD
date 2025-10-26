package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultasClase {

    public static void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS ClasesAsignadas (" +
                "id_clase INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_estudiante INTEGER, " +
                "id_profesor INTEGER, " +
                "nombre_clase TEXT NOT NULL, " +
                "horario TEXT, " +
                "FOREIGN KEY (id_estudiante) REFERENCES Estudiantes(id_estudiante) ON DELETE CASCADE, " +
                "FOREIGN KEY (id_profesor) REFERENCES Profesores(id_profesor) ON DELETE CASCADE);";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
            System.out.println("✅ Tabla ClasesAsignadas creada o verificada.");
        } catch (SQLException e) {
            System.out.println("❌ Error creando tabla ClasesAsignadas: " + e.getMessage());
        }
    }

    public boolean insertar(int idE, int idP, String nombreClase, String horario) {
        String sql = "INSERT INTO ClasesAsignadas(id_estudiante, id_profesor, nombre_clase, horario) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idE);
            ps.setInt(2, idP);
            ps.setString(3, nombreClase);
            ps.setString(4, horario);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar clase: " + e.getMessage());
            return false;
        }
    }

    public List<String> listarTodos() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT c.id_clase, c.nombre_clase, c.horario, " +
                "e.nombre || ' ' || e.apellido AS estudiante, " +
                "p.nombre || ' ' || p.apellido AS profesor " +
                "FROM ClasesAsignadas c " +
                "JOIN Estudiantes e ON c.id_estudiante = e.id_estudiante " +
                "JOIN Profesores p ON c.id_profesor = p.id_profesor;";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(rs.getInt("id_clase") + " | " +
                        rs.getString("nombre_clase") + " | " +
                        rs.getString("horario") + " | " +
                        "Estudiante: " + rs.getString("estudiante") + " | " +
                        "Profesor: " + rs.getString("profesor"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar clases: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM ClasesAsignadas WHERE id_clase=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar clase: " + e.getMessage());
            return false;
        }
    }
}

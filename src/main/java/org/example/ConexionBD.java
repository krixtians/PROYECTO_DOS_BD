package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase encargada de la conexión a SQLite y creación inicial de tablas.
 */
public class ConexionBD {
    private static final String URL = "jdbc:sqlite:gestion_universitaria.db";

    // Retorna una conexión y activa PRAGMA foreign_keys = ON;
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }

    // Método que crea las tablas si no existen (id autoincrementales, claves foráneas)
    public static void crearTablasSiNoExisten() {
        String sqlEstudiantes = "CREATE TABLE IF NOT EXISTS Estudiantes ("
                + "id_estudiante INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL,"
                + "apellido TEXT NOT NULL,"
                + "correo TEXT UNIQUE,"
                + "fecha_nacimiento TEXT"
                + ");";

        String sqlProfesores = "CREATE TABLE IF NOT EXISTS Profesores ("
                + "id_profesor INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT NOT NULL,"
                + "apellido TEXT NOT NULL,"
                + "especialidad TEXT"
                + ");";

        String sqlClases = "CREATE TABLE IF NOT EXISTS ClasesAsignadas ("
                + "id_clase INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_estudiante INTEGER NOT NULL,"
                + "id_profesor INTEGER NOT NULL,"
                + "nombre_clase TEXT NOT NULL,"
                + "horario TEXT,"
                + "FOREIGN KEY(id_estudiante) REFERENCES Estudiantes(id_estudiante) ON DELETE CASCADE,"
                + "FOREIGN KEY(id_profesor) REFERENCES Profesores(id_profesor) ON DELETE CASCADE"
                + ");";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sqlEstudiantes);
            st.execute(sqlProfesores);
            st.execute(sqlClases);
        } catch (SQLException e) {
            System.err.println("Error creando tablas: " + e.getMessage());
        }
    }
}

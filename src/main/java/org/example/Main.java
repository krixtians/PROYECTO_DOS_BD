package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ConsultasEstudiante ce = new ConsultasEstudiante();
    static ConsultasProfesor cp = new ConsultasProfesor();
    static ConsultasClase cc = new ConsultasClase();

    public static void main(String[] args) {
        // Crear las tablas al iniciar
        ConsultasEstudiante.crearTablaSiNoExiste();
        ConsultasProfesor.crearTablaSiNoExiste();
        ConsultasClase.crearTablaSiNoExiste();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- GESTION UNIVERSITARIA ---");
            System.out.println("1. Insertar estudiante");
            System.out.println("2. Listar estudiantes");
            System.out.println("3. Insertar profesor");
            System.out.println("4. Listar profesores");
            System.out.println("5. Insertar clase");
            System.out.println("6. Listar clases asignadas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int op = leerEntero();

            switch (op) {
                case 1 -> insertarEstudiante();
                case 2 -> listarEstudiantes();
                case 3 -> insertarProfesor();
                case 4 -> listarProfesores();
                case 5 -> insertarClase();
                case 6 -> listarClases();
                case 0 -> salir = true;
                default -> System.out.println("⚠️ Opción no válida");
            }
        }
        System.out.println("👋 Programa finalizado.");
    }

    static int leerEntero() {
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        int num = sc.nextInt();
        sc.nextLine();
        return num;
    }

    static void insertarEstudiante() {
        System.out.print("Nombre: ");
        String n = sc.nextLine();
        System.out.print("Apellido: ");
        String a = sc.nextLine();
        System.out.print("Correo: ");
        String c = sc.nextLine();
        System.out.print("Fecha nacimiento (YYYY-MM-DD): ");
        String f = sc.nextLine();

        if (ce.insertar(n, a, c, f)) System.out.println("✅ Estudiante agregado.");
    }

    static void listarEstudiantes() {
        List<String> lista = ce.listarTodos();
        System.out.println("\n--- ESTUDIANTES ---");
        lista.forEach(System.out::println);
    }

    static void insertarProfesor() {
        System.out.print("Nombre: ");
        String n = sc.nextLine();
        System.out.print("Apellido: ");
        String a = sc.nextLine();
        System.out.print("Especialidad: ");
        String e = sc.nextLine();

        if (cp.insertar(n, a, e)) System.out.println("✅ Profesor agregado.");
    }

    static void listarProfesores() {
        List<String> lista = cp.listarTodos();
        System.out.println("\n--- PROFESORES ---");
        lista.forEach(System.out::println);
    }

    static void insertarClase() {
        System.out.print("ID estudiante: ");
        int idE = leerEntero();
        System.out.print("ID profesor: ");
        int idP = leerEntero();
        System.out.print("Nombre clase: ");
        String nc = sc.nextLine();
        System.out.print("Horario: ");
        String h = sc.nextLine();

        if (cc.insertar(idE, idP, nc, h)) System.out.println("✅ Clase asignada.");
    }

    static void listarClases() {
        List<String> lista = cc.listarTodos();
        System.out.println("\n--- CLASES ASIGNADAS ---");
        lista.forEach(System.out::println);
    }
}

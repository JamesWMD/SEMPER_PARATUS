/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelos.Usuarios;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JAMES
 */
public class AccesoRegistroDAO {
    
    //Método para guardar un nuevo usuario en la BD
    public void guardar(Usuarios usuario) {
        String sql = "INSERT INTO usuarios (noIdentificacion, nombreUsuario, password, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establece los valores para cada columna de la tabla
            stmt.setString(1, usuario.getNoIdentificacion());  // Asumiendo que 'getNoIdentificacion' es el método correspondiente
            stmt.setString(2, usuario.getNombreUsuario());  // Método para obtener el nombre del usuario
            stmt.setString(3, usuario.getPassword());  // Método para obtener la contraseña
            stmt.setString(4, usuario.getEstado());  // Método para obtener el estado ('Activo' o 'Inactivo')

            // Ejecuta la inserción
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para validar usuario y contraseña
    public Usuarios validar(String noIdentificacion, String password) {
        Usuarios usuarioValido = null;
        // Ajustamos la consulta SQL para usar los nombres correctos de las columnas
        String sql = "SELECT * FROM usuarios WHERE noIdentificacion = ? AND password = ? AND estado = 'Activo'";

        try (Connection conn = ConexionDB.getConexion(); // Obtener la conexión
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, noIdentificacion);  // Usamos noIdentificacion en lugar de 'usuario'
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Creamos el objeto usuario con los datos correctos
                    usuarioValido = new Usuarios(
                        rs.getInt("idUsuarios"),           // idusuarios es de tipo int*/
                        rs.getString("noIdentificacion"),  // noIdentificacion
                        rs.getString("nombreUsuario"),     // nombreUsuario
                        rs.getString("password"),          // password
                        rs.getString("estado")             // estado
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioValido;
    }
}

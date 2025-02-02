package Controladores;

import DAO.UsuariosDAO;
import Modelos.Usuarios;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JAMES
 */
public class UsuariosServlet extends HttpServlet {

    private UsuariosDAO userDao = new UsuariosDAO();
    private final String pagListar = "panelPrincipal.jsp"; // pagina donde se va mostrar la lista de usuarios.
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
                
        switch (action) {
            case "listar":
                listar(request, response);
                break;
            case "nuevo":
                nuevo(request, response);
                break;
            case "guardar":
                guardar(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }
    
    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("usuarios", userDao.ListarTodosUsuario());
        request.getRequestDispatcher(pagListar).forward(request, response);

    }
    
    protected void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("usuarios", new Usuarios());
        request.getRequestDispatcher(pagListar).forward(request, response);

    }
    
    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener valores del formulario
        String noIdentificacion = request.getParameter("noIdentificacion");
        String nombreUsuario = request.getParameter("nombreUsuario");
        String password = request.getParameter("password");
        String estado = request.getParameter("estado");

        // Validar que no sean nulos o vacíos
        if (noIdentificacion == null || noIdentificacion.trim().isEmpty()
                || nombreUsuario == null || nombreUsuario.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || estado == null || estado.trim().isEmpty()) {

            request.setAttribute("mensaje", "Todos los campos son obligatorios");
            request.getRequestDispatcher(pagListar).forward(request, response);
            return; // Detener ejecución
        }

        // Crear objeto usuario
        Usuarios obj = new Usuarios();
        obj.setNoIdentificacion(noIdentificacion.trim());
        obj.setNombreUsuario(nombreUsuario.trim());
        obj.setPassword(password.trim());
        obj.setEstado(estado.trim()); // Estado ya se obtiene del select

        // Guardar usuario
        int result = userDao.registrarUsuario(obj);

        if (result > 0) {
            request.setAttribute("form_mensaje-usuario", "USUARIO REGISTRADO CORRECTAMENTE");
        } else {
            request.setAttribute("form_mensaje-usuario", "ERROR: USUARIO NO REGISTRADO");
            request.setAttribute("usuarios", obj); // Devolver datos en caso de error
        }

        request.getRequestDispatcher(pagListar).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

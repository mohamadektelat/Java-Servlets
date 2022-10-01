//----------------------------------------------------------------------------------------------------------------------

package hac.Servlets;

//----------------------------------------------------------------------------------------------------------------------

import hac.Classes.Data;

//----------------------------------------------------------------------------------------------------------------------

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.OutputStream;

//----------------------------------------------------------------------------------------------------------------------

@WebServlet(name = "JsonServlet", urlPatterns = "/getPoll")
public class JsonServlet extends HttpServlet{

    //------------------------------------------------------------------------------------------------------------------

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(response);
    }

    //------------------------------------------------------------------------------------------------------------------

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(response);
    }

    //------------------------------------------------------------------------------------------------------------------

    protected void processRequest(HttpServletResponse response)
            throws IOException {

        final Data data = (Data) this.getServletContext().getAttribute("data");

        if(data == null || data.ok())
            response.sendRedirect("/");

        else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            final JsonObject result = data.getData();

            try (OutputStream out = response.getOutputStream()) {
                JsonWriter jsonWriter = Json.createWriter(out);
                jsonWriter.write(result);
                jsonWriter.close();
            }
        }

    }

    //------------------------------------------------------------------------------------------------------------------

}

//----------------------------------------------------------------------------------------------------------------------
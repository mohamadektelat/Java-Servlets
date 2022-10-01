//----------------------------------------------------------------------------------------------------------------------

package hac.Servlets;

//----------------------------------------------------------------------------------------------------------------------

import hac.Classes.Data;

//----------------------------------------------------------------------------------------------------------------------

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//----------------------------------------------------------------------------------------------------------------------

@WebServlet(name = "VoteServlet", urlPatterns = "/vote")
public class VoteServlet extends HttpServlet{

    //------------------------------------------------------------------------------------------------------------------

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final Data data = (Data) this.getServletContext().getAttribute("data");
        final String optionId = request.getParameter("option");

        if (data != null && data.vote(optionId)) {
            setCookies(response);
        }

        response.sendRedirect("/");

    }

    //------------------------------------------------------------------------------------------------------------------

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("/");
    }

    //------------------------------------------------------------------------------------------------------------------

    protected void setCookies(HttpServletResponse response){
        Cookie cookie = new Cookie("voted", "true");
        cookie.setMaxAge(60*60*2); //two hours
        response.addCookie(cookie);
    }

    //------------------------------------------------------------------------------------------------------------------

}

//----------------------------------------------------------------------------------------------------------------------

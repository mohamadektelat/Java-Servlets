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

@WebServlet(name = "MainPageServlet", urlPatterns = "", initParams = {
})
public class MainPageServlet extends HttpServlet {

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void init() throws ServletException {
        super.init();
        final String pollFilename = getServletContext().getInitParameter("POLLFILE");
        final Data data = new Data(getServletContext().getRealPath(pollFilename));

        this.getServletContext().setAttribute("data", data);
    }

    //------------------------------------------------------------------------------------------------------------------

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    //------------------------------------------------------------------------------------------------------------------

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    //------------------------------------------------------------------------------------------------------------------

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final Data data = (Data) this.getServletContext().getAttribute("data");

        response.setContentType("text/html");
        request.getRequestDispatcher("header.html").include(request, response);

        if(data == null || data.ok())
            request.getRequestDispatcher("not_available.html").include(request, response);
        else
        {
            checkCookies(request, response);
            request.getRequestDispatcher("results.html").include(request, response);
        }

        request.getRequestDispatcher("footer.html").include(request, response);

    }

    //------------------------------------------------------------------------------------------------------------------

    protected void checkCookies(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] allCookies = request.getCookies();

        // if there are cookies, check if the cookie visited is present
        if (allCookies != null)
            for (Cookie c : allCookies) {
                if (c.getName().equals("voted")) {
                    if(c.getValue().equals("false"))
                        request.getRequestDispatcher("vote.html").include(request, response);
                }
            }
        else{
            request.getRequestDispatcher("vote.html").include(request, response);
        }
    }
}

//----------------------------------------------------------------------------------------------------------------------
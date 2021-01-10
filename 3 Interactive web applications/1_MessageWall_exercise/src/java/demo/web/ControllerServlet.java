package demo.web;

import demo.impl.MessageWall_and_RemoteLogin_Impl;
import demo.impl.UserAccess_Impl;
import demo.spec.Message;
import demo.spec.MessageWall;
import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControllerServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String view = perform_action(request);
        forwardRequest(request, response, view);
    }

    protected String perform_action(HttpServletRequest request)
            throws IOException, ServletException {

        String serv_path = request.getServletPath();
        HttpSession session = request.getSession();
//        String result_ok = "/wallview";
        String result_ok = "/view/wallview.jsp";

        if (serv_path.equals("/login.do")) {
            // Get user and password from login.html form
            String user = request.getParameter("user");
            String password = request.getParameter("password");
            session.setAttribute("user", user);
            session.setAttribute("password", password);

            // Get the wall from application scope, if don't exist create it
            MessageWall_and_RemoteLogin_Impl mw = (MessageWall_and_RemoteLogin_Impl) request.getServletContext().getAttribute("messagewall");
            if (mw == null) {
                mw = new MessageWall_and_RemoteLogin_Impl();
                request.getServletContext().setAttribute("messagewall", mw);
            }

            // UNCOMMENT LINES ABOVE WHEN NOT VERIFYING LOGIN (WallViewServlet view)
//             // Create an user access for this particular login, passing the mw   
//            UserAccess_Impl userAccess = new UserAccess_Impl(mw, user);
//             // Set the user access on the session scope
//            session.setAttribute("useraccess", userAccess);
//            return result_ok;
            
            // VERIFIED LOGIN
            UserAccess userAccess = mw.connect(user, password);
            if (userAccess == null) {
                return "/error-bad-login.html";
            } else {
                // Set the user access on the session scope
                session.setAttribute("useraccess", userAccess);
                return result_ok;
            }

        } else if (serv_path.equals("/put.do")) {
            UserAccess_Impl useraccess = (UserAccess_Impl) session.getAttribute("useraccess");
            // Get the message from html form
            String newMessage = request.getParameter("msg");
            useraccess.put(newMessage);
            // Update useraccess on session scope
            session.setAttribute("useraccess", useraccess);

            return result_ok;
        } else if (serv_path.equals("/refresh.do")) {
            // Last message is actually on session's scope usersession
            return result_ok;
        } else if (serv_path.equals("/delete.do")) {
            UserAccess_Impl useraccess = (UserAccess_Impl) session.getAttribute("useraccess");
            // Get the message index from html form
            int messageIndex = Integer.parseInt(request.getParameter("index"));
            useraccess.delete(messageIndex);
            // Update useraccess on session scope
            session.setAttribute("useraccess", useraccess);

            return result_ok;
        } else if (serv_path.equals("/logout.do")) {
            //...
            return "/goodbye.html";
        } else {
            return "/error-bad-action.html";
        }
    }

    public RemoteLogin getRemoteLogin() {
        return (RemoteLogin) getServletContext().getAttribute("remoteLogin");
    }

    public void forwardRequest(HttpServletRequest request, HttpServletResponse response, String view)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(view);
        if (dispatcher == null) {
            throw new ServletException("No dispatcher for view path '" + view + "'");
        }
        dispatcher.forward(request, response);
    }
}

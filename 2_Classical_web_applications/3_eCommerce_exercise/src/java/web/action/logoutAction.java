package web.action;

import javax.servlet.http.*;
import web.ViewManager;

public class logoutAction extends Action {

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("username", null);
        ViewManager.nextView(req, resp, "login.do");
    }
}

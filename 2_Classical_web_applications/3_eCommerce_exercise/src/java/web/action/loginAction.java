package web.action;

import javax.servlet.http.*;
import web.ViewManager;

public class loginAction extends Action {

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        ViewManager.nextView(req, resp, "/view/login.jsp");
    }
}

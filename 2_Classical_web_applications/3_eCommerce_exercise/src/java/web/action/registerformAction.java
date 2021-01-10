package web.action;

import javax.servlet.http.*;
import web.ViewManager;

public class registerformAction extends Action {

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        ViewManager.nextView(req, resp, "/view/register.jsp");
    }
}

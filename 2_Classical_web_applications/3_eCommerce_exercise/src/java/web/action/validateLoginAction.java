package web.action;

import entity.User;
import javax.servlet.http.*;
import model.UserModel;
import web.ViewManager;

public class validateLoginAction extends Action {

    UserModel userModel;
//    CategoryModel categoryModel;

    public validateLoginAction(UserModel userModel) {
        this.userModel = userModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        String providedUser = req.getParameter("username");
        String providedPassword = req.getParameter("password");
        for (User user : userModel.retrieveAll()) {
            if (user.getName().equals(providedUser) && user.getPassword().equals(providedPassword)) {
                req.getSession().setAttribute("username", user.getName());
                ViewManager.nextView(req, resp, "init.do");
            }
        }
        ViewManager.nextView(req, resp, "/view/badlogin.jsp");
    }
}

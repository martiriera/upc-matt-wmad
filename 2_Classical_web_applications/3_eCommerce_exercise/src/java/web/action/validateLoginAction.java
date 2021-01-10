package web.action;

import entity.User;
import javax.servlet.http.*;
import model.UserModel;
import model.UserXProductModel;
import web.ViewManager;

public class validateLoginAction extends Action {

    UserModel userModel;
    UserXProductModel userXProductModel;

    public validateLoginAction(UserModel userModel, UserXProductModel userXProductModel) {
        this.userModel = userModel;
        this.userXProductModel = userXProductModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        String providedUser = req.getParameter("username");
        String providedPassword = req.getParameter("password");
        for (User user : userModel.retrieveAll()) {
            if (user.getName().equals(providedUser) && user.getPassword().equals(providedPassword)) {
                req.getSession().setAttribute("username", user.getName());
                req.getSession().setAttribute("userId", user.getId());
                req.getSession().setAttribute("favourites", userXProductModel.retrieveProductIdsByUserId(user.getId()));
                ViewManager.nextView(req, resp, "init.do");
            }
        }
        ViewManager.nextView(req, resp, "/view/badlogin.jsp");
    }
}

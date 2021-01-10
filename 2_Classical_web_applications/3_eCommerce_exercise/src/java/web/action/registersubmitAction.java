package web.action;

import javax.servlet.http.*;
import javax.transaction.RollbackException;
import model.UserModel;
import web.ViewManager;

public class registersubmitAction extends Action {

    UserModel userModel;

    public registersubmitAction(UserModel userModel) {
        this.userModel = userModel;
    }

    public void perform(HttpServletRequest req, HttpServletResponse resp) throws RollbackException {
        String providedUser = req.getParameter("username");
        String providedPassword = req.getParameter("password");
        try {
            userModel.newUser(providedUser, providedPassword);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        ViewManager.nextView(req, resp, "/view/login.jsp");
    }
}

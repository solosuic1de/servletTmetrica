package controller.command.impl.orders;

import model.domain.entity.Order;
import model.domain.entity.User;
import model.domain.enums.Role;
import model.factory.ServiceFactory;
import model.factory.ServiceType;
import model.service.OrderService;
import controller.command.Command;
import controller.constants.ViewPathConstant;
import controller.data.Page;
import controller.util.AuthUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class NewOrderCommand implements Command {
    private OrderService orderService;


    public NewOrderCommand() {
        this.orderService = (OrderService) ServiceFactory.getService(ServiceType.ORDERS);
    }

    @Override
    public Page perform(HttpServletRequest request) throws IOException, ServletException {
        User user = AuthUtils.isAuthenticated(request);
        if (user == null) {
            return new Page(ViewPathConstant.LOGIN, true);
        }
        createOrder(request, user.getEmail());
        return new Page(ViewPathConstant.ACTIVITY, true);
    }

    private void createOrder(HttpServletRequest request, String email) {
        Order order = orderService.createNewActivityOrder(request.getParameter("activityName"), email);
        if (AuthUtils.hasAuthority(request, Role.ADMIN)) {
            orderService.approveOrder(order);
        }
    }

}
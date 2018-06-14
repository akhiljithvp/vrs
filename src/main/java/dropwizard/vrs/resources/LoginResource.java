package dropwizard.vrs.resources;


import dropwizard.vrs.beans.Customer;
import dropwizard.vrs.services.DataServices;
import dropwizard.vrs.services.impl.DataServiceImpl;
import dropwizard.vrs.views.AdminView;
import dropwizard.vrs.views.CustomerView;
import dropwizard.vrs.views.LoginView;
import com.yammer.dropwizard.views.View;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/login")
public class LoginResource {

    private DataServices dataServices = new DataServiceImpl();

    @GET
    public View login() {
        return new LoginView("login.ftl");
    }

    @POST
    public View login(@FormParam("customer") String customerName) {
        if ("admin".equals(customerName)) {
            AdminView adminView = new AdminView("admin.ftl");
            adminView.setMovies(dataServices.getAllMovies());
            return adminView;
        }
        CustomerView customerView = new CustomerView("customer.ftl");
        customerView.setMovies(dataServices.getAvailableMovies(customerName));
        Customer customer = dataServices.getCustomer(customerName);
        customerView.setCustomer(customer);
        return customerView;
    }

}

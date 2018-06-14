package dropwizard.vrs;

import dropwizard.vrs.configs.MainConfiguration;
import dropwizard.vrs.resources.LoginResource;
import dropwizard.vrs.resources.LogoutResource;
import dropwizard.vrs.resources.MovieResource;
import dropwizard.vrs.utils.XmlUtils;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class VrsMain extends Service<MainConfiguration> {

    public static void main(String[] args) throws Exception {
        new VrsMain().run(args);
    }

    public void initialize(Bootstrap<MainConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
    }

    public void run(MainConfiguration mainConfiguration, Environment environment) throws Exception {
        environment.addResource(new LoginResource());
        environment.addResource(new MovieResource());
        environment.addResource(new LogoutResource());
        XmlUtils.createXmlFiles();
    }

}

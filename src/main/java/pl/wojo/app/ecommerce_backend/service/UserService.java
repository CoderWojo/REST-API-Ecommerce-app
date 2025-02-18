package pl.wojo.app.ecommerce_backend.service;

import java.util.List;

import pl.wojo.app.ecommerce_backend.api_model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.model.LocalUser;

public interface UserService {
    
    List<LocalUser> findAll();

    LocalUser register(RegistrationBody registrationBody);
}

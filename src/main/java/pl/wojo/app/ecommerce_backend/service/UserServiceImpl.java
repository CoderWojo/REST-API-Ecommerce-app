package pl.wojo.app.ecommerce_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pl.wojo.app.ecommerce_backend.api_model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.model.LocalUser;
import pl.wojo.app.ecommerce_backend.repository.LocalUserRepository;

@Service
public class UserServiceImpl implements UserService{
    private LocalUserRepository repository;

    public UserServiceImpl(LocalUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LocalUser> findAll() {
        return repository.findAll();
    }

    @Override
    public LocalUser register(RegistrationBody registrationBody) {
        //konwercja
        LocalUser user = LocalUser.builder()
        .username(registrationBody.getUsername())
        //TODO: Encrypt password!
        .password(registrationBody.getPassword())
        .email(registrationBody.getEmail())
        .firstName(registrationBody.getFirstName())
        .lastName(registrationBody.getLastName())
        .build();
        
        return repository.save(user);
    }
    
}

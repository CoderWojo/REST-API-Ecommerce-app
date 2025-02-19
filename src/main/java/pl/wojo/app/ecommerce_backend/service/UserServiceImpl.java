package pl.wojo.app.ecommerce_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pl.wojo.app.ecommerce_backend.api_model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.exeption.EmailAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.exeption.UsernameAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.model.LocalUser;
import pl.wojo.app.ecommerce_backend.repository.LocalUserRepository;

@Service
public class UserServiceImpl implements UserService {
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
        String email = registrationBody.getEmail();
        String username = registrationBody.getUsername();
        boolean emailExists = repository.existsByEmailIgnoreCase(email);
        boolean usernameExists = repository.existsByUsernameIgnoreCase(username);

        if(emailExists) {
            throw new EmailAlreadyExistsException(email);
        } else if(usernameExists) {
            throw new UsernameAlreadyExistsException(username);
        }

        LocalUser user = registrationBodyToLocalUser(registrationBody);
        
        return repository.save(user);
    }

    @Override
    public boolean checkEmailAlreadyExists(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean checkUsernameAlreadyExists(String username) {
        return repository.existsByUsernameIgnoreCase(username);
    }

    static LocalUser registrationBodyToLocalUser(RegistrationBody registrationBody) {
        return LocalUser.builder()
        .username(registrationBody.getUsername())
        //TODO: Encrypt password!
        .password(registrationBody.getPassword())
        .email(registrationBody.getEmail())
        .firstName(registrationBody.getFirstName())
        .lastName(registrationBody.getLastName())
        .build();
    }
}

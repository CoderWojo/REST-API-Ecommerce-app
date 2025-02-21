package pl.wojo.app.ecommerce_backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import pl.wojo.app.ecommerce_backend.api_model.LoginBody;
import pl.wojo.app.ecommerce_backend.api_model.LoginResponse;
import pl.wojo.app.ecommerce_backend.api_model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.exeption.EmailAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.exeption.IncorrectCredentialsException;
import pl.wojo.app.ecommerce_backend.exeption.UsernameAlreadyExistsException;
import pl.wojo.app.ecommerce_backend.model.LocalUser;
import pl.wojo.app.ecommerce_backend.repository.LocalUserRepository;

@Service
public class UserServiceImpl implements UserService {
    private LocalUserRepository repository;
    private EncryptionService encryptionService;
    private JWTService jwtService;

    public UserServiceImpl(LocalUserRepository repository, 
        EncryptionService encryptionService,
        JWTService jwtService) {
        this.repository = repository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    @Override
    public LocalUser register(RegistrationBody registrationBody) {
        String email = registrationBody.getEmail();
        String username = registrationBody.getUsername();
        String password = registrationBody.getPassword();
        boolean emailExists = repository.existsByEmailIgnoreCase(email);
        boolean usernameExists = repository.existsByUsernameIgnoreCase(username);

        if(emailExists) {
            throw new EmailAlreadyExistsException(email);
        } else if(usernameExists) {
            throw new UsernameAlreadyExistsException(username);
        }

        // TODO: Encode password
        String encodedPassword = encryptionService.encode(password);
        registrationBody.setPassword(encodedPassword);

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

    @Override
    public LoginResponse login(LoginBody loginBody) throws IncorrectCredentialsException {
        String email = loginBody.getEmail();
        String password = loginBody.getPassword();

        Optional<LocalUser> opUser = repository.findByEmail(email);
        LocalUser user = null;
        if(opUser.isPresent()) {
            // porownaj hasla
            String encryptedPassword = encryptionService.encode(password);
            if(encryptedPassword.matches(opUser.get().getPassword())) {
                // password is correct, dołącz JWT do response
                user = opUser.get();
                String jwt = jwtService.generateJWT(user);
                LoginResponse loginResponse = new LoginResponse(user, jwt);

                return loginResponse;

            } else {
                System.out.println("11111111111");
                throw new IncorrectCredentialsException();
            }
        } else {
            System.out.println("222222222222");
            throw new IncorrectCredentialsException();
        }
    }
}

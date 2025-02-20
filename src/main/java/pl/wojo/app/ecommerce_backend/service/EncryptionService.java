package pl.wojo.app.ecommerce_backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {
    
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public EncryptionService() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    }

    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}

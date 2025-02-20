package pl.wojo.app.ecommerce_backend.api_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.wojo.app.ecommerce_backend.model.LocalUser;

@Data
@AllArgsConstructor
public class LoginResponse {
    private LocalUser user;

    private String jwt;
}

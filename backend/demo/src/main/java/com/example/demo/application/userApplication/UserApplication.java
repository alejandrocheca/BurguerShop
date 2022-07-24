package hamburgueseria.backend.application.userApplication;

import java.util.UUID;

import hamburgueseria.backend.security.AuthRequest;
import hamburgueseria.backend.security.AuthResponse;
import hamburgueseria.backend.application.Role;
import hamburgueseria.backend.application.userDTO;
import reactor.core.publisher.Mono;
public class UserApplication {
    public Mono<AuthResponse> registerNewUser(CreateUserDTO userDto, Role role);
    public Mono<Void> updateUser(String userId, UpdateUserDTO userDto);
    public Mono<AuthResponse> login(AuthRequest userRequest);
    public Mono<AuthResponse> refresh(String refreshToken);
    public Mono<Boolean> logout(UUID userId);
    public Mono<Void> resetTries(String userId);
    public Mono<UserDTO> getProfile(String userId);
}

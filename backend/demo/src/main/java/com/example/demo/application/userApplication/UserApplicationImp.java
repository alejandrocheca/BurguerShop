package hamburgueseria.backend.application.userApplication;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hamburgueseria.backend.demo.core.ApplicationBase;
import hamburgueseria.backend.demo.core.exceptions.BadRequestException;
import hamburgueseria.backend.demo.core.exceptions.NotFoundException;
import hamburgueseria.backend.demo.core.exceptions.UnauthorizedException;
import hamburgueseria.backend.demo.domain.userDomain.Role;
import hamburgueseria.backend.demo.domain.userDomain.User;
import hamburgueseria.backend.demo.domain.userDomain.UserDTO;
import hamburgueseria.backend.demo.domain.userDomain.UserWriteRepository;
import hamburgueseria.backend.demo.infraestructure.redisInfraestructure.RedisRepositoryInterface;
import hamburgueseria.backend.demo.security.AuthRequest;
import hamburgueseria.backend.demo.security.AuthResponse;
import hamburgueseria.backend.demo.security.UserLogInfo;
import hamburgueseria.backend.demo.security.authTokens.TokenProvider;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserApplicationImp extends ApplicationBase<User> implements UserApplication{
    private final UserWriteRepository userWriteRepository;
    private final RedisRepositoryInterface<UserLogInfo, String> logInfoRepository;
    private final RedisRepositoryInterface<UUID, String> refreshTokenRepository;
    private final ModelMapper modelMapper;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserApplicationImp(final UserWriteRepository userWriteRepository,
            final RedisRepositoryInterface<UserLogInfo, String> logInfoRepository,
            final RedisRepositoryInterface<UUID, String> refreshTokenRepository,
            final TokenProvider tokenProvider, final ModelMapper modelMapper) {
        super((id) -> userWriteRepository.findById(id));
        this.userWriteRepository = userWriteRepository;
        this.logInfoRepository = logInfoRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
            }

    @Override
    public Mono<AuthResponse> registerNewUser(CreateUserDTO userDto, Role role) {
        User newUser = this.modelMapper.map(userDto, User.class);
        newUser.setId(UUID.randomUUID());
        newUser.resetTries();
        newUser.setRole(role);
        newUser.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()));
        return newUser
                .validate("email", newUser.getEmail(), (email) -> this.userWriteRepository.exists(email))
                .then(this.userWriteRepository.save(newUser, true))
                .flatMap(dbUser -> {
                    log.info(this.serializeObject(dbUser, "added"));
                    return this.generateResponse(dbUser);
                });
    }
    public Mono<AuthResponse> login(AuthRequest userRequest) {
        return this.userWriteRepository
                .findUserByEmail(userRequest.getEmail())
                .flatMap(dbUser -> {
                    if (dbUser.validate(userRequest.getPassword())) {
                        log.info(this.serializeObject(dbUser, "login"));
                        return generateResponse(dbUser);
                    } else {
                        log.info(dbUser.toString().concat(" login failed: wrong password"));
                        return this.userWriteRepository.save(dbUser, false)
                                .then(Mono.error(new BadRequestException("Wrong password")));
                    }
                });
    }

    public Mono<Boolean> logout(UUID userId) {
        return this.logInfoRepository.removeFromID(userId.toString())
                .flatMap(removed -> {
                    if (removed) {
                        log.info(String.format("User with id %s has logged out successfully", userId.toString()));
                    }
                    return Mono.empty();
                });
    }
  

    public Mono<Void> updateUser(String userId, UpdateUserDTO userDto) {
        return this.findById(userId)
                .filter(dbUser -> dbUser.validate(userDto.getPassword()))
                .switchIfEmpty(Mono.error(new UnauthorizedException("Wrong password")))
                .flatMap(dbUser -> {
                    Mono<Void> checkEmail = Mono.empty();
                    if (!(null == userDto.getEmail()) && !dbUser.getEmail().matches(userDto.getEmail())) {
                        checkEmail = this.checkEmail(userDto.getEmail());
                    }
                    userDto.setPassword(null);
                    dbUser.getDeclaredFieldsFrom(userDto);
                    if (null != userDto.getNewPassword()) {
                        dbUser.setPassword(BCrypt.hashpw(userDto.getNewPassword(), BCrypt.gensalt()));
                    }
                    dbUser.validate();
                    return checkEmail.then(this.userWriteRepository.save(dbUser, false));
                })
                .flatMap(dbUser -> {
                    log.info(this.serializeObject(dbUser, "updated"));
                    return Mono.empty();
                });
    }

    private Mono<Void> checkEmail(String email) {
        return this.userWriteRepository
                .exists(email)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new BadRequestException(
                        String.format("email '%s' already exists", email))))
                .then();
    } 
    public Mono<AuthResponse> refresh(String refreshToken) {
        return this.refreshTokenRepository
                .getFromID(refreshToken)
                .switchIfEmpty(Mono.error(new NotFoundException("Refresh token not found")))
                .flatMap(id -> this.logInfoRepository.getFromID(id.toString()))
                .switchIfEmpty(Mono.error(new UnauthorizedException("User is logged out")))
                .flatMap(logInfo -> {
                    if (logInfo.userHasUsed(refreshToken)) {
                        return this.logout(logInfo.getId())
                                .then(Mono.error(new UnauthorizedException(
                                        "Token used multiple times: forced logout of user")));
                    }
                    logInfo.addRefreshToken(refreshToken);
                    return this.logInfoRepository.set(logInfo.getId().toString(), logInfo, 2);
                })
                .flatMap(logInfo -> this.generateResponse(logInfo));
    }

    public Mono<Void> resetTries(String userId) {
        return this.userWriteRepository
                .findById(ApplicationBase.getUUIDfrom(userId))
                .flatMap(dbUser -> {
                    dbUser.resetTries();
                    return this.userWriteRepository.save(dbUser, false);
                })
                .then();
    }

    public Mono<UserDTO> getProfile(String userId) {
        return this.findById(userId).map(dbUser -> this.modelMapper.map(dbUser, UserDTO.class));
    }

    private Mono<AuthResponse> generateResponse(UUID userId, Role role) {
        AuthResponse response = new AuthResponse();
        response.setAccessToken(tokenProvider.generateAccessToken(userId.toString()));
        response.setRefreshToken(tokenProvider.generateRefreshToken());
        return this.logInfoRepository
                .getFromID(userId.toString())
                .defaultIfEmpty(new UserLogInfo(userId,role))
                .flatMap(logInfo -> this.logInfoRepository.set(userId.toString(),logInfo, 1))
                .then(this.refreshTokenRepository.set(response.getRefreshToken(), userId, 2))
                .thenReturn(response);
    }

    private Mono<AuthResponse> generateResponse(User user) {
        return this.generateResponse(user.getId(), user.getRole());
    }

    private Mono<AuthResponse> generateResponse(UserLogInfo logInfo) {
        return this.generateResponse(logInfo.getId(), logInfo.getRole());
    }  
}

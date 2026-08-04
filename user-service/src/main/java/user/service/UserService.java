package user.service;

import java.util.Optional;

import user.dto.UserDto;
import user.entity.User;

public interface UserService {
    void create(UserDto user);
    Iterable<User> readAll();
    User readById(int id);
    void update(UserDto user);
    void delete(int id);
}

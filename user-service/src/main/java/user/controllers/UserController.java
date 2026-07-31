package user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import user.dto.UserDto;
import user.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDto GetUserById(@PathVariable int id) {
		return UserDto.toDto(userService.readById(id).get());
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<UserDto> GetAllUser() {
		return UserDto.toDto(userService.readAll());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void CreateUser(@RequestBody UserDto newUser) {
		userService.create(newUser);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void UpdateUser(@PathVariable int id, @RequestBody UserDto updateUser) {
		updateUser.setId(id);
		userService.update(updateUser);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void DeleteUser(@PathVariable int id) {
		userService.delete(id);
	}
}

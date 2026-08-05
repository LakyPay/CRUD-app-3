package user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import user.dto.UserDto;
import user.service.UserService;


@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "API для работы с пользователями")
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@Operation(
	        summary = "Получить пользователя",
	        description = "Находит пользователя по id"
	)
	@ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Успешно"),
	        @ApiResponse(responseCode = "404", description = "Не найден")
	})
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDto GetUserById(@PathVariable int id) {
		UserDto dto = UserDto.toDto(userService.readById(id));
		
		dto.add(
			    linkTo(methodOn(UserController.class)
			    		.GetUserById(id))
			            .withSelfRel()
			);
		dto.add(
			    linkTo(methodOn(UserController.class)
			    		.GetAllUser())
			            .withRel("allUsers")
			);
		dto.add(
			    linkTo(methodOn(UserController.class)
			    		.DeleteUser(id))
			            .withRel("delete")
			);
		dto.add(
			    linkTo(methodOn(UserController.class)
			    		.UpdateUser(id, new UserDto()))
			            .withRel("update")
			);
		dto.add(
			    linkTo(methodOn(UserController.class)
			    		.CreateUser(new UserDto()))
			            .withRel("create")
			);
		
		return dto;
	}
	
	@Operation(
	        summary = "Получить список пользователей",
	        description = "Собирает и выдаёт список всех пользователей"
	)
	@ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Успешно")
	})
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<UserDto> GetAllUser() {
		Iterable<UserDto> dtos = UserDto.toDto(userService.readAll());
		for (UserDto dto : dtos) {
			dto.add(
				    linkTo(methodOn(UserController.class)
				    		.GetUserById(dto.getId()))
				            .withSelfRel()
				);
			dto.add(
				    linkTo(methodOn(UserController.class)
				    		.GetAllUser())
				            .withRel("allUsers")
				);
			dto.add(
				    linkTo(methodOn(UserController.class)
				    		.DeleteUser(dto.getId()))
				            .withRel("delete")
				);
			dto.add(
				    linkTo(methodOn(UserController.class)
				    		.UpdateUser(dto.getId(), new UserDto()))
				            .withRel("update")
				);
			dto.add(
				    linkTo(methodOn(UserController.class)
				    		.CreateUser(new UserDto()))
				            .withRel("create")
				);
		}
		return dtos;
	}
	
	@Operation(
	        summary = "Создать пользователя",
	        description = "Создает нового пользователя"
	)
	@ApiResponses({
	        @ApiResponse(responseCode = "201", description = "Пользователь создан"),
	        @ApiResponse(responseCode = "400", description = "Некорректные данные")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> CreateUser(@Valid @RequestBody UserDto newUser) {
		userService.create(newUser);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(
	        summary = "Обновить пользователя",
	        description = "Заменяет пользователя новыми данными"
	)
	@ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Успешно"),
	        @ApiResponse(responseCode = "404", description = "Не найден")
	})
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Void> UpdateUser(@PathVariable int id, @Valid @RequestBody UserDto updateUser) {
		updateUser.setId(id);
		userService.update(updateUser);
		return ResponseEntity.ok().build();
	}
	
	@Operation(
	        summary = "Удалить пользователя",
	        description = "Удаляет пользователя по id"
	)
	@ApiResponses({
	        @ApiResponse(responseCode = "204", description = "Пользователь удалён"),
	        @ApiResponse(responseCode = "404", description = "Не найден")
	})
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> DeleteUser(@PathVariable int id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

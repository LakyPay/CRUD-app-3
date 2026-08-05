package user.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import user.entity.User;

@Schema(description = "Пользователь")
@Data
public class UserDto extends RepresentationModel<UserDto> {
	@Schema(description = "Идентификатор", example = "1")
	private int id;
	
	@Schema(description = "Имя", example = "Иван")
	@NotBlank(message = "Name is required")
    private String name;
	
	@Schema(description = "Email", example = "ivan@mail.com")
	@Email(message = "Email is invalid")
    @NotBlank
    private String email;
	
	@Schema(description = "Возраст", example = "20")
	@Min(value = 0)
    @Max(value = 150)
    private int age;
    
    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());

        return dto;
    }
    public static Iterable<UserDto> toDto(Iterable<User> users) {
        List<UserDto> dtoUsers = new ArrayList<UserDto>();
    	for (User user : users) {
    		UserDto dto = new UserDto();

            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setAge(user.getAge());
			dtoUsers.add(dto);
		}
        return dtoUsers;
    }
    
    public User toEntity() {

        User user = new User();

        user.setName(this.name);
        user.setEmail(this.email);
        user.setAge(this.age);
        user.setId(this.id);

        return user;
    }
    public User toEntityNoId() {

        User user = new User();

        user.setName(this.name);
        user.setEmail(this.email);
        user.setAge(this.age);

        return user;
    }
}

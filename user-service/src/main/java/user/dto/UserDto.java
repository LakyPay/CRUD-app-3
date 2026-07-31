package user.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import user.entity.User;

@Data
public class UserDto {
	private int id;
    private String name;
    private String email;
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

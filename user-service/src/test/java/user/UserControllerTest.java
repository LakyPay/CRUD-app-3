package user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import user.controllers.UserController;
import user.dto.UserDto;
import user.entity.User;
import user.service.UserServiceImpl;

public class UserControllerTest {
    
	private MockMvc mockMvc;
    private User user;
    private UserController userController;
    private UserDto userDto;
    private UserServiceImpl mockedUserServiceImpl;
    private ObjectMapper  mapper;
    private String jsonTest;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
    	mapper = new ObjectMapper();
    	
    	userDto = new UserDto();
    	userDto.setName("test name");
        userDto.setEmail("test email");
        userDto.setAge(15);
        userDto.setId(1);
        
        user = userDto.toEntity();
        
		jsonTest = mapper.writeValueAsString(userDto);
        
        mockedUserServiceImpl = mock(UserServiceImpl.class);
        userController = new UserController(mockedUserServiceImpl);
        
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
        		.defaultRequest(get("/").accept(MediaType.APPLICATION_JSON))
        		.build();
    }

    @Test
    public void createControllerTest() throws Exception{
    	mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTest))
			.andExpect(status().isCreated());
		verify(mockedUserServiceImpl, times(1)).create(userDto);
    }

    @Test
    public void updateControllerTest() throws Exception{
    	mockMvc.perform(put("/users/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTest))
			.andExpect(status().isOk());
    	verify(mockedUserServiceImpl, times(1)).update(userDto);
    }

    @Test
    public void deleteControllerTest() throws Exception{
    	mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
    	verify(mockedUserServiceImpl, times(1)).delete(1);
    }

    @Test
    public void readAllControllerTest() throws Exception{
    	Iterable<User> innerList = List.of(user);

        when(mockedUserServiceImpl.readAll())
            .thenReturn(innerList);
    	
        
        Iterable<User> newList = mapper.readValue(mockMvc.perform(get("/users")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), Iterable.class);
    	verify(mockedUserServiceImpl, times(1)).readAll();
    	assertEquals(newList, UserDto.toDto(innerList));
    }

    @Test
    public void readByIdControllerTest() throws Exception{
    	Optional<User> innerOptional = Optional.of(user);
    	when(mockedUserServiceImpl.readById(1))
    		.thenReturn(innerOptional);
    	UserDto newUserDto = mapper.readValue(mockMvc.perform(get("/users/1")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), UserDto.class);
    	verify(mockedUserServiceImpl, times(1)).readById(1);
    	assertEquals(newUserDto, userDto);
    }
}

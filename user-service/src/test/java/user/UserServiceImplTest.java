package user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import user.dto.MessageDto;
import user.dto.UserDto;
import user.entity.User;
import user.repository.UserRepository;
import user.service.UserServiceImpl;

public class UserServiceImplTest {
	private UserRepository mockedUserRepository;
	private KafkaTemplate<String, MessageDto> mockedKafkaTemplate;
	private UserServiceImpl serviceImpl; 
	private UserDto userDto; 
	private User user;

    @BeforeEach
    public void setUp() {
    	userDto = new UserDto();
    	userDto.setName("test name"); 
    	userDto.setEmail("test email"); 
    	userDto.setAge(15); 
    	userDto.setId(1); 
    	
    	user = userDto.toEntity(); 
    	
    	mockedUserRepository = mock(UserRepository.class); 
    	mockedKafkaTemplate = mock(KafkaTemplate.class);
    	serviceImpl = new UserServiceImpl(mockedUserRepository, mockedKafkaTemplate);
    }

    @Test
    public void createTest(){
    	serviceImpl.create(userDto); 
    	verify(mockedUserRepository, times(1)).save(user);
    	
    	ArgumentCaptor<MessageDto> captor = ArgumentCaptor.forClass(MessageDto.class);
    	
    	verify(mockedKafkaTemplate, times(1)).send("user-events", captor.capture());
    	
    	MessageDto sent = captor.getValue();
    	
    	assertEquals("Created", sent.getMessageType());
    	assertEquals(userDto.getEmail(), sent.getEmail());
    }

    @Test
    public void readAllTest(){
    	Iterable<User> innerList = List.of(user); 
    	when(mockedUserRepository.findAll()) 
    		.thenReturn(innerList); 
    	Iterable<User> resultList = serviceImpl.readAll(); 
    	assertEquals(innerList, resultList); 
    	verify(mockedUserRepository, times(1)).findAll();
    }

    @Test
    public void readByIdTest(){
    	Optional<User> timedOptional = Optional.of(user); 
    	when(mockedUserRepository.findById(1))
    		.thenReturn(timedOptional); 
    	User resultUser = serviceImpl.readById(1); 
    	assertEquals(timedOptional.get(), resultUser); 
    	verify(mockedUserRepository, times(1)).findById(1);
    }

    @Test
    public void updateTest(){
    	serviceImpl.update(userDto); 
    	verify(mockedUserRepository, times(1)).save(user);
    }

    @Test
    public void deleteTest(){
    	when(mockedUserRepository.findById(1))
        .thenReturn(Optional.of(user));
    	
    	serviceImpl.delete(1); 
    	
    	verify(mockedUserRepository, times(1)).deleteById(1);
    	verify(mockedUserRepository).findById(1);
    	
    	ArgumentCaptor<MessageDto> captor = ArgumentCaptor.forClass(MessageDto.class);
    	
    	verify(mockedKafkaTemplate, times(1)).send("user-events", captor.capture());
    	
    	MessageDto sent = captor.getValue();
    	
    	assertEquals("Deleted", sent.getMessageType());
    	assertEquals(userDto.getEmail(), sent.getEmail());
    }
}

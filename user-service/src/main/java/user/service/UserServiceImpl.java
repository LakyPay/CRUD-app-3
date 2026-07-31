package user.service;

import java.util.Optional;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import user.dto.MessageDto;
import user.dto.UserDto;
import user.entity.User;
import user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	private final KafkaTemplate<String, MessageDto> kafkaTemplate;

	public UserServiceImpl(UserRepository userRepository, KafkaTemplate<String, MessageDto> kafkaTemplate){ 
		this.userRepository = userRepository; 
		this.kafkaTemplate = kafkaTemplate;
	}

    @Override
    public void create(UserDto user){
    	userRepository.save(user.toEntityNoId());
    	MessageDto messageDto = new MessageDto();
    	messageDto.setEmail("request@gmail.com");
    	messageDto.setMessageType("Created");
    	kafkaTemplate.send("user-events", messageDto);
    }

    @Override
    public Iterable<User> readAll(){
    	return userRepository.findAll();
    }

    @Override
    public Optional<User> readById(int id){
    	return userRepository.findById(id);
    }

    @Override
    public void update(UserDto user){
    	userRepository.save(user.toEntity());
    }

    @Override
    public void delete(int id){
    	userRepository.deleteById(id);
    	MessageDto messageDto = new MessageDto();
    	messageDto.setEmail("request@gmail.com");
    	messageDto.setMessageType("Deleted");
    	kafkaTemplate.send("user-events", messageDto);
    }
}

package user.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import user.Exception.UserNotFoundException;
import user.dto.MessageDto;
import user.dto.UserDto;
import user.entity.User;
import user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	private final KafkaTemplate<String, MessageDto> kafkaTemplate;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	public UserServiceImpl(UserRepository userRepository, KafkaTemplate<String, MessageDto> kafkaTemplate){ 
		this.userRepository = userRepository; 
		this.kafkaTemplate = kafkaTemplate;
	}

    @Override
    public void create(UserDto user){
    	userRepository.save(user.toEntityNoId());
    	MessageDto messageDto = new MessageDto();
    	try {
    		messageDto.setEmail(user.getEmail());
        	messageDto.setMessageType("Created");
        	kafkaTemplate.send("user-events", messageDto);
		} 
    	catch (Exception e) {
			logger.error("ошибка отправки сообщения", e);
		}
    }

    @Override
    public Iterable<User> readAll(){
    	return userRepository.findAll();
    }

    @Override
    public User readById(int id){
    	return userRepository.findById(id)
    			.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void update(UserDto user){
    	userRepository.save(user.toEntity());
    }

    @Override
    public void delete(int id){
    	MessageDto messageDto = new MessageDto();
    	try {
    		messageDto.setEmail(userRepository.findById(id).get().getEmail());
        	messageDto.setMessageType("Deleted");
        	kafkaTemplate.send("user-events", messageDto);
		} 
    	catch (Exception e) {
			logger.error("ошибка отправки сообщения", e);
		}
    	userRepository.deleteById(id);
    }
}

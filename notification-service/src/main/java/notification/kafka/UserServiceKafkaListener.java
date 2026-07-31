package notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import notification.dto.MailMessageDto;
import notification.service.MailService;
import user.dto.MessageDto;

@Service
public class UserServiceKafkaListener {
	private final MailService mailService;
	
	public UserServiceKafkaListener(MailService mailService) {
		this.mailService = mailService;
	}
	
	@KafkaListener(topics = "user-events")
	public void SendEmailWhenUserEvent(MessageDto messageDto) {
		MailMessageDto mailMessageDto = MailMessageDto.toMailMessageDto(messageDto);
		mailService.sendMessage(mailMessageDto);
	}
}

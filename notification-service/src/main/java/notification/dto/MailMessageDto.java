package notification.dto;

import org.springframework.mail.SimpleMailMessage;

import lombok.Data;
import user.dto.MessageDto;

@Data
public class MailMessageDto {
	private String to;
	
	private String subject;
	
	private String text;
	
	public SimpleMailMessage toSimpleMailMessage() {
		SimpleMailMessage sMessage = new SimpleMailMessage();
		sMessage.setTo(this.to);
		sMessage.setSubject(this.subject);
		sMessage.setText(this.text);
		return sMessage;
	}
	public static MailMessageDto toMailMessageDto(MessageDto messageDto) {
		MailMessageDto mailMessageDto = new MailMessageDto();
		mailMessageDto.setTo(messageDto.getEmail());
		if(messageDto.getMessageType() == "Created") {
			mailMessageDto.setSubject("Ваш аккаунт создан.");
			mailMessageDto.setText("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
		}
		else {
			mailMessageDto.setSubject("Ваш аккаунт удалён.");
			mailMessageDto.setText("Здравствуйте! Ваш аккаунт был удалён.");
		}
		return mailMessageDto;
	}
}

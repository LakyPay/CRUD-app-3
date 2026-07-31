package notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import notification.dto.MailMessageDto;

@Service
public class MailServiceImpl implements MailService{
	private JavaMailSender mailSender;
	
	@Autowired
	public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Override
	public void sendMessage(MailMessageDto message) {
		mailSender.send(message.toSimpleMailMessage());
	}
}

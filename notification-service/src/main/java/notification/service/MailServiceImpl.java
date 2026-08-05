package notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import notification.dto.MailMessageDto;

@Service
public class MailServiceImpl implements MailService{
	private JavaMailSender mailSender;
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Override
	public void sendMessage(MailMessageDto message) {
		try {
			mailSender.send(message.toSimpleMailMessage());
		} catch (Exception e) {
			logger.error("ошибка отправки сообщения", e);
		}
	}
}

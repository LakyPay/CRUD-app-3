package notification.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import notification.dto.MailMessageDto;
import notification.service.MailServiceImpl;

@RestController
@RequestMapping("/mail")
public class MailSendController {
	private final MailServiceImpl mailServiceImpl;
	
	@Autowired
	public MailSendController(MailServiceImpl mailServiceImpl) {
		this.mailServiceImpl = mailServiceImpl;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void CreateUser(@RequestBody MailMessageDto mailMessageDto) {
		try {
			mailServiceImpl.sendMessage(mailMessageDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

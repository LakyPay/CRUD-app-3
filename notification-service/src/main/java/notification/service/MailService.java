package notification.service;

import notification.dto.MailMessageDto;

public interface MailService {
	public void sendMessage(MailMessageDto message);
}

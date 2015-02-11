package cc.protea.crmtext.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class CRMTextSendMessageResponse extends CRMTextResponse {

	@XmlElement(name = "msgID") String messageId;
	@XmlElement(name = "message") String message;
	@XmlElement(name = "timestamp") Date timestamp;
	@XmlElement(name = "txnid") String transactionId;
	@XmlElement(name = "msg_direction") String messageDirection; // TODO replace with enum - MT and ???
	@XmlElement(name = "user_id") String userId;
	@XmlElement(name = "customer") CRMTextCustomer customer;

}

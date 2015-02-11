package cc.protea.crmtext.servlet;

import java.util.Date;

public class CRMTextInboundMessage {

	public String customerId;
	public String message;
	public String store;
	public String phoneNumber;
	public String optInStatus; // TODO enum
	public Date timestamp;
	public String subacct;
	public String customerName;
	public String msgID;
	public String subacct_name;
}

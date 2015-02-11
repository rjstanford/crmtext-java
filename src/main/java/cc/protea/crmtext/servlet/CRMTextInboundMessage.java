package cc.protea.crmtext.servlet;

import java.util.Date;

import cc.protea.crmtext.model.CRMTextCustomer;
import cc.protea.crmtext.model.CRMTextStore;

public class CRMTextInboundMessage {

	public CRMTextStore store = new CRMTextStore();
	public CRMTextCustomer customer = new CRMTextCustomer();

	public Date timestamp;
	public String messageId;

	public String message;
}

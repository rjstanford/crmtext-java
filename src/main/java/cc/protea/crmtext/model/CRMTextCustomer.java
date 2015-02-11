package cc.protea.crmtext.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class CRMTextCustomer {

	@XmlElement(name = "custId") public String id;
	@XmlElement(name = "subacct") public String subAccount;
	@XmlElement(name = "timestamp") public Date timestamp;
	@XmlElement(name = "optinStatus") public String optInStatus; // TODO replace with enum 3, ??
	@XmlElement(name = "custName") public String name;
	@XmlElement(name = "custMobile") public String phoneNumber;
}

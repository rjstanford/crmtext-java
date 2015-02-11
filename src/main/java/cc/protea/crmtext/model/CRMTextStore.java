package cc.protea.crmtext.model;

import javax.xml.bind.annotation.XmlElement;

public class CRMTextStore {

	@XmlElement(name = "store-id") public String id;
	@XmlElement(name = "store-name") public String name;
	@XmlElement(name = "store-phone") public String phoneNumber;
}

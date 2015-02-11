package cc.protea.crmtext.model;

import javax.xml.bind.annotation.XmlAttribute;

//@XmlRootElement(name = "response")
public class CRMTextResponse {

	public boolean success;

	@XmlAttribute(name = "message") public String message;
	@XmlAttribute(name = "status") public String status;
}

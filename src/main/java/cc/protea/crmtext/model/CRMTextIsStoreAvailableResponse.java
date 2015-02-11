package cc.protea.crmtext.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class CRMTextIsStoreAvailableResponse extends CRMTextResponse {

	@XmlElement(name = "keyword") public String store;

	public boolean available;

	@XmlElement(name = "availability")
	public void setAvailability(final int available) {
		this.available = (available != 0);
	}
}

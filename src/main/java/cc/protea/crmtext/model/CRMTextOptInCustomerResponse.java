package cc.protea.crmtext.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class CRMTextOptInCustomerResponse extends CRMTextResponse {

	@XmlElement(name = "customer") CRMTextCustomer customer;

}

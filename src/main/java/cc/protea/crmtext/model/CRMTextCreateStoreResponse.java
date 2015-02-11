package cc.protea.crmtext.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class CRMTextCreateStoreResponse extends CRMTextResponse {

	public CRMTextStore store;
}

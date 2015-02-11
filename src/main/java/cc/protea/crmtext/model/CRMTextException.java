package cc.protea.crmtext.model;

import cc.protea.util.http.Response;

public class CRMTextException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public Response response = null;
	public String errorMessage;
	public String errorCode;

	public CRMTextException(final Exception e) {
		super(e);
	}

	public CRMTextException(final Exception e, final Response response) {
		super(e);
		this.response = response;
	}

	public CRMTextException(final Exception e, final String errorCode, final String errorMessage) {
		super(e);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}

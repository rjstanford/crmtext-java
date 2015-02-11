package cc.protea.crmtext.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CRMTextServlet extends HttpServlet {

	private static CRMTextEndpoint endpoint = null;

	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		if (CRMTextServlet.endpoint == null) {
			return;
		}
		CRMTextInboundMessage message = buildMessage(req.getParameterMap());
		try {
			CRMTextServlet.endpoint.inboundMessage(message);
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	public static CRMTextEndpoint getEndpoint() {
		return CRMTextServlet.endpoint;
	}

	public static void setEndpoint(final CRMTextEndpoint endpoint) {
		CRMTextServlet.endpoint = endpoint;
	}

    public void init() throws ServletException {
    	if (getServletConfig() == null) {
    		return;
    	}
    	String endpointClassName = getServletConfig().getInitParameter("endpoint");
    	if (endpointClassName == null || endpointClassName.trim().length() == 0) {
    		return;
    	}
		try {
			@SuppressWarnings("unchecked")
			Class<? extends CRMTextEndpoint> endpointClass = (Class<? extends CRMTextEndpoint>) Class.forName(endpointClassName);
	    	CRMTextServlet.endpoint = endpointClass.newInstance();
		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (IllegalAccessException e) {
			return;
		}
    }

    CRMTextInboundMessage buildMessage(final Map<String, String[]> map) {
    	CRMTextInboundMessage message = new CRMTextInboundMessage();
    	message.store.id = getParameter(map, "subacct");
    	message.store.name = getParameter(map, "subacct_name");
    	message.store.phoneNumber = null;
    	message.store.store = getParameter(map, "keyword");
    	message.customer.phoneNumber = getParameter(map, "mobileNum");
    	message.customer.id = getParameter(map, "custID");
    	message.customer.name = getParameter(map, "custName");
    	message.customer.optInStatus = getParameter(map, "optInStatus");
    	message.timestamp = getDate(getParameter(map, "timestamp"));
    	message.messageId = getParameter(map, "msgID");
    	message.message = getParameter(map, "message");
    	return message;
    }

    Date getDate(final String in) {
    	if (in == null) {
    		return null;
    	}
    	try {
    		// TODO validate actual date format
    		// TODO set timezone to UTC
			return new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").parse(in);
		} catch (ParseException e) {
			return null;
		}
    }

    String getParameter(final Map<String, String[]> map, final String label) {
    	if (map == null || label == null || ! map.containsKey(label)) {
    		return null;
    	}
    	return map.get(label)[0];
    }

	private static final long serialVersionUID = 1L;
}


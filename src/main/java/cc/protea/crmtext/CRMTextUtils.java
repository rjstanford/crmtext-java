package cc.protea.crmtext;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import cc.protea.crmtext.model.CRMTextException;
import cc.protea.crmtext.model.CRMTextResponse;
import cc.protea.util.http.Request;
import cc.protea.util.http.Response;

class CRMTextUtils {

	// TODO all inbound dates are in Pacific Time (because that's just stupid). Convert to UTC before returning.

	static <T extends CRMTextResponse> T post(final Request request, final Class<T> type) {
		Response response;
		try {
			response = request.postResource();
			T ret = CRMTextUtils.convert(response.getBody(), type, true);
			if (ret == null) {
				throw new CRMTextException(null);
			}
			ret.success = response.getResponseCode() == 200;
			return ret;
		} catch (IOException e) {
			throw new CRMTextException(e);
		}
	}


	@SuppressWarnings("unchecked")
	static <T> T convert(final String xml, final Class<T> type, final boolean handleErrors) {
		if (xml == null) {
			return null;
		}
        if (String.class.equals(type)) {
        	return (T) xml;
        }
		try {
			JAXBContext context = JAXBContext.newInstance(type);
	        Unmarshaller un = context.createUnmarshaller();
	        ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
	        return (T) un.unmarshal(is);
		} catch (JAXBException e) {
			if (! handleErrors) {
				throw new CRMTextException(e);
			}
			try {
				CRMTextErrorResponse error = CRMTextUtils.convert(xml, CRMTextErrorResponse.class, false);
				throw new CRMTextException(e, error.status, error.message);
			} catch (Exception ex) {
				throw new CRMTextException(e);
			}
		}
	}

	private static class CRMTextErrorResponse {
		@XmlAttribute(name = "op") public String method;
		@XmlAttribute(name = "status") public String status;
		@XmlAttribute(name = "message") public String message;
		@XmlAttribute(name = "version") public String version;
	}

	static String trim(final String in) {
		return in == null ? "" : in.trim();
	}

	static String formatPhoneNumber(final String in) {
		if (in == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder(10);
		for (char c : in.toCharArray()) {
			if (Character.isDigit(c)) {
				if (sb.length() == 0 && c == '1') {
					continue;
				}
				sb.append(c);
			}
		}
		return sb.toString();
	}

}

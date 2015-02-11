package cc.protea.crmtext;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import cc.protea.crmtext.model.CRMTextBasicResponse;
import cc.protea.crmtext.model.CRMTextCreateStoreResponse;
import cc.protea.crmtext.model.CRMTextCustomerChangeResponse;
import cc.protea.crmtext.model.CRMTextIsStoreAvailableResponse;
import cc.protea.crmtext.model.CRMTextResponse;
import cc.protea.crmtext.model.CRMTextSendMessageResponse;
import cc.protea.util.http.Request;

public class CRMText {

	private static String apiUrl = "https://restapi.crmtext.com/smapi/rest";

	public String username;
	public String password;
	public String store;

	public CRMText(final String username, final String password, final String store) {
		this.username = username;
		this.password = password;
		this.store = store;
	}

	////////////////////////////////////////////////////////////
	// API - Store Creation and Management

	/**
	 * Validate that a desired store keyword is available
	 * @param store
	 */
	public CRMTextIsStoreAvailableResponse isStoreAvailable(final String store) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("keyword", store);
		return post("iskeywordavailable", parameters, CRMTextIsStoreAvailableResponse.class);
	}

	/**
	 * Create a new store that users can opt-in to
	 * @param store the short keyword to use
	 * @param storeName the descriptive label used by generated messages
	 */
	public CRMTextCreateStoreResponse createStore(final String store, final String storeName) {
		return createStoreAndUser(store, storeName, null, null, null, null, null);
	}

	/**
	 * Create a new store that users can opt-in to and a new user to administrate the store
	 * @param store the short keyword to use
	 * @param storeName the descriptive label used by generated messages
	 * @param firstName the first name of the administrative user
	 * @param lastName the last name of the administrative user
	 * @param phoneNumber the phone number of the administrative user
	 * @param emailAddress the email address of the administrative user
	 * @param password the password for the administrative user
	 */
	public CRMTextCreateStoreResponse createStoreAndUser(final String store, final String storeName, final String firstName, final String lastName, final String phoneNumber, final String emailAddress, final String password) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("storename", storeName);
		parameters.put("storeKeyword", store);
		parameters.put("firstname", firstName);
		parameters.put("lastname", lastName);
		parameters.put("phonenumber", CRMTextUtils.formatPhoneNumber(phoneNumber));
		parameters.put("emailid", emailAddress);
		parameters.put("password", password);
		CRMTextCreateStoreResponse response = post("createstoreanduser", parameters, CRMTextCreateStoreResponse.class);
		response.store.store = store;
		return response;
	}

	/**
	 * Set the URL for inbound message notifications
	 * @param url the publicly reachable URL to be used as a webhook when messages arrive
	 */
	public CRMTextBasicResponse setCallbackUrl(final String url) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("callback", url);
		return post("setcallback", parameters, CRMTextBasicResponse.class);
	}

	////////////////////////////////////////////////////////////
	// API - Customers

	/**
	 * Add a new subscriber to your store
	 * @param phoneNumber the number to send to
	 */
	public CRMTextCustomerChangeResponse optInCustomer(final String phoneNumber) {
		return optInCustomer(phoneNumber, null, null);
	}

	/**
	 * Add a new subscriber to your store
	 * @param phoneNumber the number to send to
	 * @param firstName the subscriber's first name
	 * @param lastName the subscriber's last name
	 */
	public CRMTextCustomerChangeResponse optInCustomer(final String phoneNumber, final String firstName, final String lastName) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("phone_number", CRMTextUtils.formatPhoneNumber(phoneNumber));
		parameters.put("firstname", firstName);
		parameters.put("lastname", lastName);
		return post("optincustomer", parameters, CRMTextCustomerChangeResponse.class);
	}

	/**
	 * Remove a subscriber from your store
	 * @param phoneNumber the number used to send to
	 */
	public CRMTextCustomerChangeResponse optOutCustomer(final String phoneNumber) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("phone_number", CRMTextUtils.formatPhoneNumber(phoneNumber));
		return post("optoutcustomer", parameters, CRMTextCustomerChangeResponse.class);
	}

	////////////////////////////////////////////////////////////
	// API - Messaging

	/**
	 * Send a message to a single opted-in phone number in your store
	 * @param phoneNumber the number to send to
	 * @param message should be no longer than 1,600 bytes (under 160 for best deliverability)
	 */
	public CRMTextSendMessageResponse sendMessage(final String phoneNumber, final String message) {
		return sendMessage(phoneNumber, message, null);
	}

	/**
	 * Send a message to an opted-in phone number in your store
	 * @param phoneNumber the number to send to
	 * @param message should be no longer than 1,600 bytes (under 160 for best deliverability)
	 * @param imageUrl must refer to an JPG, GIF or PNG image less than 450KB in size
	 */
	public CRMTextSendMessageResponse sendMessage(final String phoneNumber, final String message, final String imageUrl) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("phone_number", CRMTextUtils.formatPhoneNumber(phoneNumber));
		parameters.put("message", message);
		parameters.put("mmsurl", imageUrl);
		return post("sendsmsmsg", parameters, CRMTextSendMessageResponse.class);
	}

	/**
	 * Send a message to every single phone number in your store
	 * @param campaignName should be unique
	 * @param message should be no longer than 1,600 bytes (under 160 for best deliverability)
	 */
	public CRMTextResponse sendCampaign(final String campaignName, final String message) {
		return sendCampaign(campaignName, message, null);
	}

	/**
	 * Send a message to every single phone number in your store
	 * @param campaignName should be unique
	 * @param message should be no longer than 1,600 bytes (under 160 for best deliverability)
	 * @param imageUrl must refer to an JPG, GIF or PNG image less than 450KB in size
	 */
	public CRMTextResponse sendCampaign(final String campaignName, final String message, final String imageUrl) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("message", message);
		parameters.put("mmsurl", imageUrl);
		return post("sendcampaign", parameters, CRMTextResponse.class);
	}

	// Helper methods that use class variables

	private <T extends CRMTextResponse> T post(final String method, final Map<String, String> parameters, final Class<T> type) {
		for (String key : parameters.keySet()) {
			parameters.put(key, CRMTextUtils.trim(parameters.get(key)));
		}
		parameters.put("method", method);
		Request request = new Request(CRMText.apiUrl)
			.addHeader("Authorization", getAuthorizationHeader(username, password, store))
			.addHeader("Accept", "application/xml")
			.addHeader("Content-Type", "application/xml")
			.setBodyUrlEncoded(parameters);
		return CRMTextUtils.post(request, type);
	}

	private String getAuthorizationHeader(final String username, final String password, final String store) {
		final String pair = username + ":" + password + ":" + store;
		final String base64 = DatatypeConverter.printBase64Binary(pair.getBytes());
		return "Basic " + base64;
	}

}
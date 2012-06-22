package com.sayar.requests.async;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.http.auth.AuthenticationException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.sayar.requests.RequestArguments;
import com.sayar.requests.RequestsConstants;
import com.sayar.requests.Response;
import com.sayar.requests.auth.HttpAuthentication;
import com.sayar.requests.impl.RequestHandler;
import com.sayar.requests.impl.RequestHandlerFactory;

public class RequestsIntentService extends IntentService {

	public static final String REQUESTS_ARGUMENTS = "com.sayar.requests.RequestArguments";
	public static final String REQUESTS_ENTITY_BYTE_ARRAY = "com.sayar.requests.RequestEntityByteArray";
	public static final String REQUESTS_ENTITY_MAP = "com.sayar.requests.RequestEntityMap";
	public static final String REQUESTS_ENTITY_STRING = "com.sayar.requests.RequestEntityString";
	public static final String REQUESTS_ENTITY_STRING_CHARSET = "com.sayar.requests.RequestEntityStringCharset";
	public static final String REQUESTS_HTTP_AUTHENTICATION = "com.sayar.requests.HttpAuthentication";
	public static final String REQUESTS_RESPONSE = "com.sayar.requests.Response";
	public static final String REQUESTS_RESULT_RECEIVER = "com.sayar.requests.REQUESTS_RESULTRECEIVER";

	public RequestsIntentService() {
		super(RequestsConstants.USER_AGENT);
	}

	public RequestsIntentService(final String name) {
		super(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onHandleIntent(final Intent intent) {
		if (!intent.hasExtra(RequestsIntentService.REQUESTS_ARGUMENTS) || !intent.hasExtra(RequestsIntentService.REQUESTS_RESULT_RECEIVER)) {
			return;
		}

		// Get the result receiver.
		final ResultReceiver receiver = intent.getParcelableExtra(RequestsIntentService.REQUESTS_RESULT_RECEIVER);

		// Get request arguments (RequestArguments implements the Parcelable
		// protocol).
		final RequestArguments args = intent.getParcelableExtra(RequestsIntentService.REQUESTS_ARGUMENTS);

		// Get the http authentication object
		if (intent.hasExtra(RequestsIntentService.REQUESTS_HTTP_AUTHENTICATION)) {
			final HttpAuthentication auth = (HttpAuthentication) intent.getParcelableExtra(RequestsIntentService.REQUESTS_HTTP_AUTHENTICATION);
			args.setHttpAuth(auth);
		}

		if (intent.hasExtra(RequestsIntentService.REQUESTS_ENTITY_STRING)) {
			final String content = intent.getStringExtra(RequestsIntentService.REQUESTS_ENTITY_STRING);
			String charset = null;
			if (intent.hasExtra(RequestsIntentService.REQUESTS_ENTITY_STRING_CHARSET)) {
				charset = intent.getStringExtra(RequestsIntentService.REQUESTS_ENTITY_STRING_CHARSET);
			}
			if (charset == null) {
				try {
					args.setRequestEntity(content);
				} catch (final UnsupportedEncodingException e) {
					Log.e(RequestsConstants.LOG_TAG, "Unable to set Request Entity - Unsupported Encoding.", e);
				}
			} else {
				try {
					args.setRequestEntity(content, charset);
				} catch (final UnsupportedEncodingException e) {
					Log.e(RequestsConstants.LOG_TAG, "Unable to set Request Entity - Unsupported Encoding.", e);
				}
			}
		} else if (intent.hasExtra(RequestsIntentService.REQUESTS_ENTITY_BYTE_ARRAY)) {
			final byte[] bytes = intent.getByteArrayExtra(RequestsIntentService.REQUESTS_ENTITY_BYTE_ARRAY);
			args.setRequestEntity(bytes);
		} else if (intent.hasExtra(RequestsIntentService.REQUESTS_ENTITY_MAP)) {
			try {
				args.setRequestEntity((Map<String, String>) intent.getParcelableExtra(RequestsIntentService.REQUESTS_ENTITY_MAP));
			} catch (final UnsupportedEncodingException e) {
				Log.e(RequestsConstants.LOG_TAG, "Unable to set Request Entity - Unsupported Encoding.", e);
			}
		}
		// NOTE: Can not set input streams as they are not parcelable.
		// TODO: FIGURE OUT WHAT THE SECOND SETREQUESTENTITY PARAM IS FOR
		// (CONTENT-TYPE???)
		/*
		 * else if (intent.hasExtra("RequestEntityFile")) { String file =
		 * intent.getStringExtra("RequestEntityFile");
		 * //args.setRequestEntity(file, args.getParseAs()); }
		 */

		// Get the current request handler
		// NOTE: This call will create a new Request Handler, so no threading
		// issues.
		final RequestHandler handler = RequestHandlerFactory.getRequestHandler();

		try {
			final Response response = handler.execute(args);

			// Return result receiver
			final Bundle resultData = new Bundle();
			resultData.putParcelable(RequestsIntentService.REQUESTS_RESPONSE, response);
			receiver.send(response.getCode(), resultData);
		} catch (final AuthenticationException e) {
			Log.e(RequestsConstants.LOG_TAG, "Authentication Exception", e);

		} catch (final InvalidParameterException e) {
			Log.e(RequestsConstants.LOG_TAG, "Invalid Parameter Exception", e);
		}
	}
}

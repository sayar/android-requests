/*
   Copyright 2012 Rami Sayar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.sayar.requests.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

import com.sayar.requests.RequestArguments;
import com.sayar.requests.RequestMethod;
import com.sayar.requests.RequestsConstants;
import com.sayar.requests.Response;
import com.sayar.requests.auth.HttpAuthentication;
import com.sayar.requests.parsers.RequestParser;
import com.sayar.requests.parsers.RequestParserFactory;

public class HttpClientRequestHandler implements RequestHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sayar.requests.impl.RequestHandler#execute(com.sayar.requests.
	 * RequestArguments)
	 */
	public Response execute(final RequestArguments args) throws AuthenticationException, InvalidParameterException {
		// Parse params
		final String url = args.getUrl();
		final Map<String, String> params = args.getParams();
		final HttpAuthentication httpAuth = args.getHttpAuth();

		String formattedUrl;
		try {
			formattedUrl = RequestHandlerUtils.formatUrl(url, params);
		} catch (final UnsupportedEncodingException e) {
			Log.e(RequestsConstants.LOG_TAG, "Unsupported Encoding Exception in Url Parameters.", e);
			throw new InvalidParameterException("Url Parameter Encoding is invalid.");

		}

		final RequestMethod method = args.getMethod();
		HttpUriRequest request = null;
		if (method == RequestMethod.GET) {
			request = new HttpGet(formattedUrl);
		} else if (method == RequestMethod.DELETE) {
			request = new HttpDelete(formattedUrl);
		} else if (method == RequestMethod.OPTIONS) {
			request = new HttpOptions(formattedUrl);
		} else if (method == RequestMethod.HEAD) {
			request = new HttpHead(formattedUrl);
		} else if (method == RequestMethod.TRACE) {
			request = new HttpTrace(formattedUrl);
		} else if (method == RequestMethod.POST || method == RequestMethod.PUT) {
			request = method == RequestMethod.POST ? new HttpPost(formattedUrl) : new HttpPut(formattedUrl);
			if (args.getRequestEntity() != null) {
				((HttpEntityEnclosingRequestBase) request).setEntity(args.getRequestEntity());
			}
		} else {
			throw new InvalidParameterException("Request Method is not set.");
		}

		if (httpAuth != null) {
			try {
				HttpClientRequestHandler.addAuthentication(request, httpAuth);
			} catch (final AuthenticationException e) {
				Log.e(RequestsConstants.LOG_TAG, "Adding Authentication Failed.", e);
				throw e;
			}
		}
		if (args.getHeaders() != null) {
			HttpClientRequestHandler.addHeaderParams(request, args.getHeaders());
		}

		final Response response = new Response();

		final HttpClient client = this.getHttpClient(args);

		try {
			final HttpResponse httpResponse = client.execute(request);

			response.setResponseVersion(httpResponse.getProtocolVersion());
			response.setStatusAndCode(httpResponse.getStatusLine());

			final HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				// Convert the stream into a string and store into the RAW
				// field.
				// InputStream instream = entity.getContent();
				// response.setRaw(RequestHandlerUtils.convertStreamToString(instream));
				// instream.close(); // Closing the input stream will trigger
				// connection release

				// TODO: Perhaps we should be dealing with the charset
				response.setRaw(EntityUtils.toString(entity));

				try {
					// Get the Content Type
					final String contenttype = entity.getContentType().getValue();

					RequestParser parser = null;

					// Check if a request-specific parser was set.
					if (args.getRequestParser() == null) {
						// Parse the request according to the user's wishes.
						final String extractionFormat = args.getParseAs();

						// Determine extraction format if none are set.
						if (extractionFormat == null) {
							// If we can not find an appropriate parser, the
							// default one will be returned.
							parser = RequestParserFactory.findRequestParser(contenttype);
						} else {
							// Try to get the requested parser. This will throw
							// an exception if we can't get it.
							parser = RequestParserFactory.getRequestParser(extractionFormat);
						}
					} else {
						// Set a request specific parser.
						parser = args.getRequestParser();
					}

					// Parse the content.. and if it throws an exception log it.
					final Object result = parser.parse(response.getRaw());
					// Check the result of the parser.
					if (result != null) {
						response.setContent(result);
						response.setParsedAs(parser.getName());
					} else {
						// If the parser returned nothing (and most likely it
						// wasn't the default parser).

						// We already have the raw response, user the default
						// parser fallback.
						response.setContent(RequestParserFactory.DEFAULT_PARSER.parse(response.getRaw()));
						response.setParsedAs(RequestParserFactory.DEFAULT_PARSER.getName());
					}
				} catch (final InvalidParameterException e) {
					Log.e(RequestsConstants.LOG_TAG, "Parser Requested Could Not Be Found.", e);
					throw e;
				} catch (final Exception e) {
					// Catch any parsing exception.
					Log.e(RequestsConstants.LOG_TAG, "Parser Failed Exception.", e);
					// Informed it was parsed as nothing... aka look at the raw
					// string.
					response.setParsedAs(null);
				}

			} else {
				if (args.getMethod() != RequestMethod.HEAD) {
					Log.w(RequestsConstants.LOG_TAG, "Entity is empty?");
				}
			}
		} catch (final ClientProtocolException e) {
			Log.e(RequestsConstants.LOG_TAG, "Client Protocol Exception", e);
		} catch (final IOException e) {
			Log.e(RequestsConstants.LOG_TAG, "IO Exception.", e);
		} finally {
		}

		return response;
	}

	private static HttpUriRequest addHeaderParams(final HttpUriRequest request, final Map<String, String> headerParams) {
		// Check if the params are empty.
		if (!headerParams.isEmpty()) {
			// Iterate using the enhanced for loop synthax as the JIT will
			// optimize it away
			// See
			// http://developer.android.com/guide/practices/design/performance.html#foreach
			for (final Map.Entry<String, String> entry : headerParams.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		return request;
	}

	private static HttpUriRequest addAuthentication(final HttpUriRequest request, final HttpAuthentication auth)
	throws AuthenticationException {
		// Add authentication headers.
		request.addHeader(auth.getHttpHeader(request));
		return request;
	}

	// Instantiate only one instance of the HttpClient
	// Reference:
	// http://na.isobar.com/2011/best-way-to-use-httpclient-in-android/
	protected static AbstractHttpClient HttpClient = null;

	// Wait this many milliseconds max for the TCP connection to be established
	private static final int CONNECTION_TIMEOUT = 60 * 1000;

	// Wait this many milliseconds max for the server to send us data once the
	// connection has been established
	private static final int SO_TIMEOUT = 5 * 60 * 1000;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sayar.requests.impl.HttpClientRequestHandler#getHttpClient(com.sayar
	 * .requests.RequestArguments)
	 */
	protected HttpClient getHttpClient(final RequestArguments args) {
		// The HttpClient is heavy and should be instantiated once per app.
		// It holds a thread pool for requests and is threadsafe.

		if (HttpClientRequestHandler.HttpClient == null) {
			// Reference:
			// http://na.isobar.com/2011/best-way-to-use-httpclient-in-android/
			final SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			// TODO: WE SHOULD BE THROWING AN EXCEMPTION IF CONTEXT IS NULL
			registry.register(new Scheme("https", this.getHttpsSocketFactory(args.getContext()), 443));

			final HttpParams basicparms = new BasicHttpParams();
			HttpProtocolParams.setContentCharset(basicparms, HTTP.DEFAULT_CONTENT_CHARSET);
			HttpProtocolParams.setHttpElementCharset(basicparms, HTTP.DEFAULT_CONTENT_CHARSET);
			HttpProtocolParams.setVersion(basicparms, args.getHttpVersion());
			HttpProtocolParams.setUseExpectContinue(basicparms, true);
			HttpProtocolParams.setUserAgent(basicparms, args.getUserAgent());

			// TODO: Need sensible defaults.
			HttpConnectionParams.setLinger(basicparms, -1);
			// HttpConnectionParams.setSocketBufferSize(basicparms, ???);
			// HttpConnectionParams.setStaleCheckingEnabled(basicparms, false);
			HttpConnectionParams.setConnectionTimeout(basicparms, HttpClientRequestHandler.CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(basicparms, HttpClientRequestHandler.SO_TIMEOUT);
			// HttpConnectionParams.setTcpNoDelay(basicparms, true);

			final ClientConnectionManager cm = new ThreadSafeClientConnManager(basicparms, registry);

			HttpClientRequestHandler.HttpClient = new DefaultHttpClient(cm, basicparms);
		}
		return HttpClientRequestHandler.HttpClient;
	}

	private SocketFactory getHttpsSocketFactory(final Context context) {
		// Gets an HTTPS socket factory with SSL Session Caching if such support
		// is available, otherwise falls back to a non-caching factory
		try {
			final Class<?> sslSessionCacheClass = Class.forName("android.net.SSLSessionCache");

			Object sslSessionCache = null;
			if (context != null) {
				sslSessionCache = sslSessionCacheClass.getConstructor(Context.class).newInstance(context);
			} else {
				// TODO: REQUIRES TESTING... EASY TO TEST... JUST RUN IT IN AN
				// EMULATOR
				sslSessionCache = sslSessionCacheClass.getConstructor(Context.class).newInstance("SSLSessionCache");
			}
			final Method getHttpSocketFactory = Class.forName("android.net.SSLCertificateSocketFactory").getMethod(
					"getHttpSocketFactory", new Class<?>[] { int.class, sslSessionCacheClass });
			return (SocketFactory) getHttpSocketFactory.invoke(null, HttpClientRequestHandler.CONNECTION_TIMEOUT, sslSessionCache);
		} catch (final Exception e) {
			Log.e("HttpClientProvider",
					"Unable to use android.net.SSLCertificateSocketFactory to get a SSL session caching socket factory, falling back to a non-caching socket factory",
					e);
			return SSLSocketFactory.getSocketFactory();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sayar.requests.impl.RequestHandler#shutdown()
	 */
	public void shutdown() {
		if (HttpClientRequestHandler.HttpClient != null) {
			HttpClientRequestHandler.HttpClient.getConnectionManager().shutdown();
			HttpClientRequestHandler.HttpClient = null;
		}

	}
}

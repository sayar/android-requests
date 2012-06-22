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

package com.sayar.requests;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.sayar.requests.auth.HttpAuthentication;
import com.sayar.requests.parsers.RequestParser;

/**
 * Request Arguments Class.
 * 
 * This class holds all the data required to perform a REST Request. It contains
 * information such as the Rest Method, the Url, the query parameters, the
 * authentication scheme and more...
 * 
 * This class implements the Parcelable interface, you can thus send this object
 * through IPC.
 * 
 * @author ramisayar
 * 
 */
public class RequestArguments implements Parcelable {
	/**
	 * The method to use for the REST request.
	 */
	private RequestMethod method;
	/**
	 * The Url of the resource.
	 */
	private String url;
	/**
	 * The url query parameters. We won't use NameValuePairs as they are
	 * HttpClient Library Specific. Library users shouldn't care about a
	 * specific object for holding string-string pairs.
	 */
	private Map<String, String> params;

	/**
	 * The entity of the request e.g. the POST/PUT parameters.
	 */
	private HttpEntity requestEntity;
	/**
	 * The HttpAuthentication Scheme.
	 */
	private HttpAuthentication httpAuth;
	/**
	 * The Request Headers
	 */
	private Map<String, String> headers;
	/**
	 * The User Agent string for the REST request.
	 */
	private String userAgent = RequestsConstants.USER_AGENT;

	/**
	 * How to parse the response data. e.g. Json, String, Xml. Leave this null
	 * for automatic detection of the format.
	 */
	private String parseAs = null;

	/**
	 * Application Context
	 */
	private Context context = null;

	/**
	 * Http Version - Defaults to Http 1.1
	 */
	private HttpVersion httpVersion = HttpVersion.HTTP_1_1;

	/**
	 * Request Parser - Allows the user to specify a request parser per request.
	 */
	private RequestParser requestParser = null;

	/**
	 * Constructor for Request Arguments Class
	 * 
	 * This is the constructor the RequestArguments class.
	 */
	public RequestArguments() {
	}

	/**
	 * Constructor for Request Arguments Class
	 * 
	 * This is the constructor the RequestArguments class. Parameters passed in
	 * are set at construction time.
	 * 
	 * @param url
	 *            The Url of the REST Resource
	 */
	public RequestArguments(final String url) {
		this.setUrl(url);
	}

	/**
	 * Constructor for Request Arguments Class
	 * 
	 * This is the constructor the RequestArguments class. Parameters passed in
	 * are set at construction time.
	 * 
	 * @param method
	 *            The Request Method E.g. GET, PUT, POST, DELETE...
	 * @param url
	 *            The Url of the REST Resource
	 */
	public RequestArguments(final RequestMethod method, final String url) {
		this.method = method;
		this.setUrl(url);
	}

	/**
	 * Constructor for Request Arguments Class
	 * 
	 * This is the constructor the RequestArguments class. Parameters passed in
	 * are set at construction time.
	 * 
	 * @param method
	 *            The Request Method E.g. GET, PUT, POST, DELETE...
	 * @param url
	 *            The Url of the REST Resource
	 */
	public RequestArguments(final RequestMethod method, final String url, final Map<String, String> params) {
		this.method = method;
		this.setUrl(url);
		this.params = params;
	}

	/**
	 * Constructor for Request Arguments Class
	 * 
	 * This is the constructor the RequestArguments class. Parameters passed in
	 * are set at construction time.
	 * 
	 * @param method
	 *            The Request Method E.g. GET, PUT, POST, DELETE...
	 * @param url
	 *            The Url of the REST Resource
	 */
	public RequestArguments(final RequestMethod method, final String url, final Map<String, String> params,
			final HttpAuthentication httpAuth) {
		this.method = method;
		this.setUrl(url);
		this.params = params;
		this.httpAuth = httpAuth;
	}

	/**
	 * Constructor for Request Arguments Class
	 * 
	 * This is the constructor the RequestArguments class. Parameters passed in
	 * are set at construction time.
	 * 
	 * @param url
	 *            The Url of the REST Resource
	 * @param params
	 *            The params for the REST Request
	 */
	public RequestArguments(final String url, final Map<String, String> params) {
		this.setUrl(url);
		this.params = params;
	}

	/**
	 * Constructor for Request Arguments Class
	 * 
	 * This is the constructor the RequestArguments class. Parameters passed in
	 * are set at construction time.
	 * 
	 * @param url
	 *            The Url of the REST Resource
	 * @param params
	 *            The params for the REST Request
	 * @param httpAuth
	 *            The http authentication method
	 */
	public RequestArguments(final String url, final Map<String, String> params, final HttpAuthentication httpAuth) {
		this.setUrl(url);
		this.params = params;
		this.httpAuth = httpAuth;
	}

	/**
	 * Constructor for Request Arguments Class
	 * 
	 * This is the constructor the RequestArguments class. Parameters passed in
	 * are set at construction time.
	 * 
	 * @param url
	 *            The Url of the REST Resource
	 * @param params
	 *            The params for the REST Request
	 * @param httpAuth
	 *            The http authentication method
	 * @param headers
	 *            Headers for the REST Request
	 */
	public RequestArguments(final String url, final Map<String, String> params, final HttpAuthentication httpAuth,
			final Map<String, String> headers) {
		this.setUrl(url);
		this.params = params;
		this.httpAuth = httpAuth;
		this.headers = headers;
	}

	/**
	 * Constructor for Request Arguments Class
	 * 
	 * This is the constructor the RequestArguments class. Parameters passed in
	 * are set at construction time.
	 * 
	 * @param url
	 *            The Url of the REST Resource
	 * @param params
	 *            The params for the REST Request
	 * @param httpAuth
	 *            The http authentication method
	 * @param headers
	 *            Headers for the REST Request
	 * @param parseAs
	 *            How to parse the Response
	 */
	public RequestArguments(final String url, final Map<String, String> params, final HttpAuthentication httpAuth,
			final Map<String, String> headers, final String parseAs) {
		this.setUrl(url);
		this.params = params;
		this.httpAuth = httpAuth;
		this.headers = headers;
		this.parseAs = parseAs;
	}

	@SuppressWarnings("unchecked")
	private RequestArguments(final Parcel bundle) {
		this.method = RequestMethod.fromValue(bundle.readString());

		this.url = bundle.readString();

		this.userAgent = bundle.readString();

		this.parseAs = bundle.readString();

		final Bundle params = bundle.readBundle();
		this.params = (Map<String, String>) params.getSerializable("map");

		final Bundle headers = bundle.readBundle();
		this.headers = (Map<String, String>) headers.getSerializable("map");
	}

	/**
	 * Getter for the Resource Url.
	 * 
	 * @return url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Setter for the Resource Url.
	 * 
	 * @param url
	 */
	public void setUrl(final String url) {
		if (!this.validUrl(url)) {
			throw new InvalidParameterException("URL is invalid.");
		}
		this.url = url;
	}

	/**
	 * @param url
	 * @return
	 */
	private boolean validUrl(final String url) {
		return !TextUtils.isEmpty(url) && URLUtil.isValidUrl(url);
	}

	/**
	 * Getter for the Parameters
	 * 
	 * @return params
	 */
	public Map<String, String> getParams() {
		return this.params;
	}

	/**
	 * Setter for the Parameters
	 * 
	 * @param params
	 */
	public void setParams(final Map<String, String> params) {
		this.params = params;
	}

	/**
	 * Getter for the Http Authentication Method
	 * 
	 * @return
	 */
	public HttpAuthentication getHttpAuth() {
		return this.httpAuth;
	}

	/**
	 * Setter for the Http Authentication Method
	 * 
	 * @param httpAuth
	 */
	public void setHttpAuth(final HttpAuthentication httpAuth) {
		this.httpAuth = httpAuth;
	}

	/**
	 * Getter for the Http headers.
	 * 
	 * @return
	 */
	public Map<String, String> getHeaders() {
		return this.headers;
	}

	/**
	 * Setter for the Http headers.
	 * 
	 * @param headers
	 */
	public void setHeaders(final Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * Getter for the Http User Agent.
	 * 
	 * @return
	 */
	public String getUserAgent() {
		return this.userAgent;
	}

	/**
	 * Setter for the Http User Agent.
	 * 
	 * @param userAgent
	 */
	public void setUserAgent(final String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * Getter for the request method. E.g. GET, PUT, POST, DELETE...
	 * 
	 * @return method
	 */
	public RequestMethod getMethod() {
		return this.method;
	}

	/**
	 * Setter for the request method.
	 * 
	 * @param method
	 */
	public void setMethod(final RequestMethod method) {
		this.method = method;
	}

	/**
	 * Getter for the application context to use.
	 * 
	 * @return
	 */
	public Context getContext() {
		return this.context;
	}

	/**
	 * Setter for the application context.
	 * 
	 * @param context
	 */
	public void setContext(final Context context) {
		this.context = context;
	}

	/**
	 * Getter for the request entity e.g. the request data.
	 * 
	 * @return requestEntity
	 */
	public HttpEntity getRequestEntity() {
		return this.requestEntity;
	}

	public void setRequestEntity(final HttpEntity requestEntity) {
		this.requestEntity = requestEntity;
	}

	/**
	 * Setter for the request entity. The param, String-String Map, will be
	 * encoded in URL format.
	 * 
	 * @param requestEntity
	 * @throws UnsupportedEncodingException
	 */
	public void setRequestEntity(final Map<String, String> requestEntity) throws UnsupportedEncodingException {
		if (!requestEntity.isEmpty()) {
			final List<NameValuePair> nvpairs = new ArrayList<NameValuePair>(requestEntity.size());
			// Iterate using the enhanced for loop synthax as the JIT will
			// optimize it away
			// See
			// http://developer.android.com/guide/practices/design/performance.html#foreach
			for (final Map.Entry<String, String> entry : requestEntity.entrySet()) {
				nvpairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			this.requestEntity = new UrlEncodedFormEntity(nvpairs);
		} else {
			throw new InvalidParameterException();
		}
	}

	/**
	 * Setter for the request entity. The param string will be placed as is.
	 * 
	 * @param entity
	 * @throws UnsupportedEncodingException
	 */
	public void setRequestEntity(final String entity) throws UnsupportedEncodingException {
		this.requestEntity = new StringEntity(entity);
	}

	/**
	 * Setter for the request entity. The param string will be placed as is with
	 * a specific charset specified.
	 * 
	 * @param entity
	 * @param charset
	 * @throws UnsupportedEncodingException
	 */
	public void setRequestEntity(final String entity, final String charset) throws UnsupportedEncodingException {
		this.requestEntity = new StringEntity(entity, charset);
	}

	/**
	 * Setter for the request entity. The serializable will be placed in the
	 * request.
	 * 
	 * @param ser
	 * @param bufferize
	 * @throws IOException
	 */
	public void setRequestEntity(final Serializable ser, final boolean bufferize) throws IOException {
		this.requestEntity = new SerializableEntity(ser, bufferize);
	}

	/**
	 * Setter for the request entity. The input stream will be consumed by the
	 * request.
	 * 
	 * @param instream
	 * @param length
	 */
	public void setRequestEntity(final InputStream instream, final long length) {
		this.requestEntity = new InputStreamEntity(instream, length);
	}

	/**
	 * Setter for the request entity. The file will be consumed by the request.
	 * 
	 * @param file
	 * @param contentType
	 */
	public void setRequestEntity(final File file, final String contentType) {
		this.requestEntity = new FileEntity(file, contentType);
	}

	/**
	 * Setter for the request entity. The byte array will be placed in the
	 * request.
	 * 
	 * @param b
	 */
	public void setRequestEntity(final byte[] b) {
		this.requestEntity = new ByteArrayEntity(b);
	}

	/**
	 * Getter for how to parse the request response.
	 * 
	 * @return parseAs
	 */
	public String getParseAs() {
		return this.parseAs;
	}

	/**
	 * Setter for how to parse the request response. E.g. json, xml, string.
	 * 
	 * @param parseAs
	 */
	public void setParseAs(final String parseAs) {
		this.parseAs = parseAs;
	}

	/**
	 * Getter for the Http Version. Defaults to Http 1.1
	 * 
	 * @return httpVersion
	 */
	public HttpVersion getHttpVersion() {
		return this.httpVersion;
	}

	/**
	 * Setter for the Http Version.
	 * 
	 * @param httpVersion
	 */
	public void setHttpVersion(final HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}

	/**
	 * @return the requestParser
	 */
	public RequestParser getRequestParser() {
		return this.requestParser;
	}

	/**
	 * Setter for the request parser. Allows you to specify a request parser for
	 * the request.
	 * 
	 * You can thus have customized XML SAX-based parsers for specific objects.
	 * 
	 * @param requestParser
	 *            the requestParser to set
	 */
	public void setRequestParser(final RequestParser requestParser) {
		this.requestParser = requestParser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RestRequestArguments [" + this.method.toString() + "url=" + this.url + ", params=" + this.params + "]";
	}

	/**
	 * You need at the very least a URL and a method to perform any request.
	 * 
	 * @return True if the request is valid and can be performed.
	 */
	public boolean isValidRequest() {
		return this.validUrl(this.url) && this.method != null;

	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(final Parcel dest, final int flags) {
		dest.writeString(this.method.toValue());
		dest.writeString(this.url);
		dest.writeString(this.userAgent);
		dest.writeString(this.parseAs);
		final Bundle params = new Bundle();
		params.putSerializable("map", (Serializable) this.params);
		dest.writeBundle(params);

		final Bundle headers = new Bundle();
		params.putSerializable("map", (Serializable) this.headers);
		dest.writeBundle(headers);
	}

	public static final Parcelable.Creator<RequestArguments> CREATOR = new Parcelable.Creator<RequestArguments>() {
		public RequestArguments createFromParcel(final Parcel in) {
			return new RequestArguments(in);
		}

		public RequestArguments[] newArray(final int size) {
			return new RequestArguments[size];
		}
	};
}

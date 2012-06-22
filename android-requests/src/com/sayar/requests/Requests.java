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

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.http.auth.AuthenticationException;

import com.sayar.requests.auth.HttpAuthentication;
import com.sayar.requests.impl.RequestHandlerFactory;

/**
 * @author ramisayar
 * 
 */
public class Requests {
	/**
	 * Perform a GET Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @return Response Instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response get(final String url) throws AuthenticationException, InvalidParameterException {
		return Requests.get(url, null, null, null);
	}

	/**
	 * Perform a GET Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response get(final String url, final Map<String, String> params) throws AuthenticationException,
	InvalidParameterException {
		return Requests.get(url, params, null, null);
	}

	/**
	 * Perform a GET Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response get(final String url, final Map<String, String> params, final HttpAuthentication httpAuth)
	throws AuthenticationException, InvalidParameterException {
		return Requests.get(url, params, httpAuth, null);
	}

	/**
	 * Perform a GET Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @param headers
	 *            The Http headers.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response get(final String url, final Map<String, String> params, final HttpAuthentication httpAuth,
			final Map<String, String> headers) throws AuthenticationException, InvalidParameterException {
		final RequestArguments args = new RequestArguments(url, params, httpAuth, headers);
		return Requests.get(args);
	}

	/**
	 * Perform a GET Request.
	 * 
	 * @param args
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response get(final RequestArguments args) throws AuthenticationException, InvalidParameterException {
		args.setMethod(RequestMethod.GET);
		return RequestHandlerFactory.getRequestHandler().execute(args);
	}

	/**
	 * Perform a POST Request.
	 * 
	 * @param url
	 * @param entity
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response post(final String url, final Map<String, String> entity)
	throws UnsupportedEncodingException, AuthenticationException, InvalidParameterException {
		return Requests.post(url, entity, null, null, null);
	}

	/**
	 * Perform a POST Request.
	 * 
	 * @param url
	 * @param entity
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response post(final String url, final String entity) throws UnsupportedEncodingException,
	AuthenticationException, InvalidParameterException {
		return Requests.post(url, entity, null, null, null);
	}

	/**
	 * Perform a POST Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response post(final String url, final Map<String, String> entity, final Map<String, String> params)
	throws UnsupportedEncodingException, AuthenticationException, InvalidParameterException {
		return Requests.post(url, entity, params, null, null);
	}

	/**
	 * Perform a POST Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response post(final String url, final String entity, final Map<String, String> params)
	throws UnsupportedEncodingException, AuthenticationException, InvalidParameterException {
		return Requests.post(url, entity, params, null, null);
	}

	/**
	 * Perform a POST Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @param httpAuth
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response post(final String url, final Map<String, String> entity, final Map<String, String> params,
			final HttpAuthentication httpAuth) throws UnsupportedEncodingException, AuthenticationException,
			InvalidParameterException {
		return Requests.post(url, entity, params, httpAuth, null);
	}

	/**
	 * Perform a POST Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @param httpAuth
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response post(final String url, final String entity, final Map<String, String> params,
			final HttpAuthentication httpAuth) throws UnsupportedEncodingException, AuthenticationException,
			InvalidParameterException {
		return Requests.post(url, entity, params, httpAuth, null);
	}

	/**
	 * Perform a POST Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @param httpAuth
	 * @param headers
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response post(final String url, final Map<String, String> entity, final Map<String, String> params,
			final HttpAuthentication httpAuth, final Map<String, String> headers) throws UnsupportedEncodingException,
			AuthenticationException, InvalidParameterException {
		final RequestArguments args = new RequestArguments(url, params, httpAuth, headers);
		args.setRequestEntity(entity);
		return Requests.post(args);
	}

	/**
	 * Perform a POST Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @param httpAuth
	 * @param headers
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response post(final String url, final String entity, final Map<String, String> params,
			final HttpAuthentication httpAuth, final Map<String, String> headers) throws UnsupportedEncodingException,
			AuthenticationException, InvalidParameterException {
		final RequestArguments args = new RequestArguments(url, params, httpAuth, headers);
		args.setRequestEntity(entity);
		return Requests.post(args);
	}

	/**
	 * Perform a POST Request.
	 * 
	 * @param args
	 * @return
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response post(final RequestArguments args) throws AuthenticationException, InvalidParameterException {
		args.setMethod(RequestMethod.POST);
		return RequestHandlerFactory.getRequestHandler().execute(args);
	}

	/**
	 * Perform a PUT Request.
	 * 
	 * @param url
	 * @param entity
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response put(final String url, final Map<String, String> entity) throws UnsupportedEncodingException,
	AuthenticationException, InvalidParameterException {
		return Requests.put(url, entity, null, null, null);
	}

	/**
	 * Perform a PUT Request.
	 * 
	 * @param url
	 * @param entity
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response put(final String url, final String entity) throws UnsupportedEncodingException,
	AuthenticationException, InvalidParameterException {
		return Requests.put(url, entity, null, null, null);
	}

	/**
	 * Perform a PUT Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response put(final String url, final Map<String, String> entity, final Map<String, String> params)
	throws UnsupportedEncodingException, AuthenticationException, InvalidParameterException {
		return Requests.put(url, entity, params, null, null);
	}

	/**
	 * Perform a PUT Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response put(final String url, final String entity, final Map<String, String> params)
	throws UnsupportedEncodingException, AuthenticationException, InvalidParameterException {
		return Requests.put(url, entity, params, null, null);
	}

	/**
	 * Perform a PUT Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @param httpAuth
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response put(final String url, final Map<String, String> entity, final Map<String, String> params,
			final HttpAuthentication httpAuth) throws UnsupportedEncodingException, AuthenticationException,
			InvalidParameterException {
		return Requests.put(url, entity, params, httpAuth, null);
	}

	/**
	 * Perform a PUT Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @param httpAuth
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response put(final String url, final String entity, final Map<String, String> params,
			final HttpAuthentication httpAuth) throws UnsupportedEncodingException, AuthenticationException,
			InvalidParameterException {
		return Requests.put(url, entity, params, httpAuth, null);
	}

	/**
	 * Perform a PUT Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @param httpAuth
	 * @param headers
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response put(final String url, final Map<String, String> entity, final Map<String, String> params,
			final HttpAuthentication httpAuth, final Map<String, String> headers) throws UnsupportedEncodingException,
			AuthenticationException, InvalidParameterException {
		final RequestArguments args = new RequestArguments(url, params, httpAuth, headers);
		args.setRequestEntity(entity);
		return Requests.put(args);
	}

	/**
	 * Perform a PUT Request.
	 * 
	 * @param url
	 * @param entity
	 * @param params
	 * @param httpAuth
	 * @param headers
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response put(final String url, final String entity, final Map<String, String> params,
			final HttpAuthentication httpAuth, final Map<String, String> headers) throws UnsupportedEncodingException,
			AuthenticationException, InvalidParameterException {
		final RequestArguments args = new RequestArguments(url, params, httpAuth, headers);
		args.setRequestEntity(entity);
		return Requests.put(args);
	}

	/**
	 * Perform a PUT Request.
	 * 
	 * @param args
	 * @return
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response put(final RequestArguments args) throws AuthenticationException, InvalidParameterException {
		args.setMethod(RequestMethod.PUT);
		return RequestHandlerFactory.getRequestHandler().execute(args);
	}

	/**
	 * Perform a DELETE Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @return Response Instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response delete(final String url) throws AuthenticationException, InvalidParameterException {
		return Requests.delete(url, null, null, null);
	}

	/**
	 * Perform a DELETE Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response delete(final String url, final Map<String, String> params) throws AuthenticationException,
	InvalidParameterException {
		return Requests.delete(url, params, null, null);
	}

	/**
	 * Perform a DELETE Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response delete(final String url, final Map<String, String> params, final HttpAuthentication httpAuth)
	throws AuthenticationException, InvalidParameterException {
		return Requests.delete(url, params, httpAuth, null);
	}

	/**
	 * Perform a DELETE Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @param headers
	 *            The Http headers.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response delete(final String url, final Map<String, String> params,
			final HttpAuthentication httpAuth, final Map<String, String> headers) throws AuthenticationException,
			InvalidParameterException {
		final RequestArguments args = new RequestArguments(url, params, httpAuth, headers);
		return Requests.delete(args);
	}

	/**
	 * Perform a DELETE Request.
	 * 
	 * @param args
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response delete(final RequestArguments args) throws AuthenticationException,
	InvalidParameterException {
		args.setMethod(RequestMethod.DELETE);
		return RequestHandlerFactory.getRequestHandler().execute(args);
	}

	/**
	 * Perform a HEAD Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @return Response Instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response head(final String url) throws AuthenticationException, InvalidParameterException {
		return Requests.head(url, null, null, null);
	}

	/**
	 * Perform a HEAD Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response head(final String url, final Map<String, String> params) throws AuthenticationException,
	InvalidParameterException {
		return Requests.head(url, params, null, null);
	}

	/**
	 * Perform a HEAD Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response head(final String url, final Map<String, String> params, final HttpAuthentication httpAuth)
	throws AuthenticationException, InvalidParameterException {
		return Requests.head(url, params, httpAuth, null);
	}

	/**
	 * Perform a HEAD Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @param headers
	 *            The Http headers.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response head(final String url, final Map<String, String> params, final HttpAuthentication httpAuth,
			final Map<String, String> headers) throws AuthenticationException, InvalidParameterException {
		final RequestArguments args = new RequestArguments(url, params, httpAuth, headers);
		return Requests.head(args);
	}

	/**
	 * Perform a HEAD Request.
	 * 
	 * @param args
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response head(final RequestArguments args) throws AuthenticationException, InvalidParameterException {
		args.setMethod(RequestMethod.HEAD);
		return RequestHandlerFactory.getRequestHandler().execute(args);
	}

	/**
	 * Perform a OPTIONS Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @return Response Instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response options(final String url) throws AuthenticationException, InvalidParameterException {
		return Requests.options(url, null, null, null);
	}

	/**
	 * Perform a OPTIONS Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response options(final String url, final Map<String, String> params) throws AuthenticationException,
	InvalidParameterException {
		return Requests.options(url, params, null, null);
	}

	/**
	 * Perform a OPTIONS Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response options(final String url, final Map<String, String> params, final HttpAuthentication httpAuth)
	throws AuthenticationException, InvalidParameterException {
		return Requests.options(url, params, httpAuth, null);
	}

	/**
	 * Perform a OPTIONS Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @param headers
	 *            The Http headers.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response options(final String url, final Map<String, String> params,
			final HttpAuthentication httpAuth, final Map<String, String> headers) throws AuthenticationException,
			InvalidParameterException {
		final RequestArguments args = new RequestArguments(url, params, httpAuth, headers);
		return Requests.options(args);
	}

	/**
	 * Perform a OPTIONS Request.
	 * 
	 * @param args
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response options(final RequestArguments args) throws AuthenticationException,
	InvalidParameterException {
		args.setMethod(RequestMethod.OPTIONS);
		return RequestHandlerFactory.getRequestHandler().execute(args);
	}

	/**
	 * Perform a TRACE Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @return Response Instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response trace(final String url) throws AuthenticationException, InvalidParameterException {
		return Requests.trace(url, null, null, null);
	}

	/**
	 * Perform a TRACE Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response trace(final String url, final Map<String, String> params) throws AuthenticationException,
	InvalidParameterException {
		return Requests.trace(url, params, null, null);
	}

	/**
	 * Perform a TRACE Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response trace(final String url, final Map<String, String> params, final HttpAuthentication httpAuth)
	throws AuthenticationException, InvalidParameterException {
		return Requests.trace(url, params, httpAuth, null);
	}

	/**
	 * Perform a TRACE Request.
	 * 
	 * @param url
	 *            The url of the Resource.
	 * @param params
	 *            The query params.
	 * @param httpAuth
	 *            The Http authentication method.
	 * @param headers
	 *            The Http headers.
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response trace(final String url, final Map<String, String> params, final HttpAuthentication httpAuth,
			final Map<String, String> headers) throws AuthenticationException, InvalidParameterException {
		final RequestArguments args = new RequestArguments(url, params, httpAuth, headers);
		return Requests.trace(args);
	}

	/**
	 * Perform a TRACE Request.
	 * 
	 * @param args
	 * @return Response instance
	 * @throws AuthenticationException
	 * @throws InvalidParameterException
	 */
	public static Response trace(final RequestArguments args) throws AuthenticationException, InvalidParameterException {
		args.setMethod(RequestMethod.TRACE);
		return RequestHandlerFactory.getRequestHandler().execute(args);
	}
}

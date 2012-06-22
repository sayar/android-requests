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

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.AbstractHttpClient;

import android.content.Context;
import android.util.Log;

import com.sayar.requests.RequestArguments;
import com.sayar.requests.RequestsConstants;

public class AndroidHttpClientRequestHandler extends HttpClientRequestHandler {

	@Override
	protected HttpClient getHttpClient(final RequestArguments args) {
		if (HttpClientRequestHandler.HttpClient == null) {
			try {
				// Use Reflection to instantiate AndroidHttpClient. This allows
				// us to maintain the same source code while targeting Api Level 3.
				final Class<?> androidHttpClientClass = Class.forName("android.net.http.AndroidHttpClient");
				if (args.getContext() != null) {
					HttpClientRequestHandler.HttpClient = (AbstractHttpClient) androidHttpClientClass.getConstructor(
							String.class, Context.class).newInstance(args.getUserAgent(), args.getContext());
				} else {
					HttpClientRequestHandler.HttpClient = (AbstractHttpClient) androidHttpClientClass.getConstructor(
							String.class).newInstance(args.getUserAgent());
				}
			} catch (final Exception e) {
				Log.e(RequestsConstants.LOG_TAG, "Instantiation of AndroidHttpClient failed.", e);
				// Use Older HttpClient instead.
				return super.getHttpClient(args);
			}
		}
		return HttpClientRequestHandler.HttpClient;
	}

}

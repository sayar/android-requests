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

import android.os.Build;

public class RequestHandlerFactory {
	public static RequestHandler getRequestHandler() {
		return RequestHandlerFactory.getRequestHandler(Integer
				.parseInt(Build.VERSION.SDK));
	}

	/**
	 * Gets the best Request Handler for the current Android version.
	 * 
	 * Dependency Injection for the platform: Useful for testing.
	 * 
	 * REFERENCE:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 * 
	 * @return
	 */
	public static RequestHandler getRequestHandler(final int platform) {
		// Before Froyo use HttpClient
		if (platform < 8) {
			return new HttpClientRequestHandler();
		} // Before ICS use AndroidHttpClient
		else if (platform < 14) {
			return new AndroidHttpClientRequestHandler();
		} // After ICS use HttpUrlConnection
		else {
			return new HttpUrlConnectionRequestHandler();
		}
	}
}

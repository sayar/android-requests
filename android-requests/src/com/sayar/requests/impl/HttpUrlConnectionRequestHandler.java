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

import com.sayar.requests.RequestArguments;
import com.sayar.requests.Response;

public class HttpUrlConnectionRequestHandler implements RequestHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sayar.requests.impl.RequestHandler#execute(com.sayar.requests.
	 * RequestArguments)
	 */
	public Response execute(final RequestArguments args) {
		// TODO
		// http://developer.android.com/reference/java/net/HttpURLConnection.html
		// TODO
		// http://android-developers.blogspot.com/2011/09/androids-http-clients.html
		return null;
	}

	public void shutdown() {
		// TODO Auto-generated method stub

	}

}

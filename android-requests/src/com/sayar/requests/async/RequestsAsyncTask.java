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

package com.sayar.requests.async;

import java.security.InvalidParameterException;

import org.apache.http.auth.AuthenticationException;

import android.os.AsyncTask;
import android.util.Log;

import com.sayar.requests.RequestArguments;
import com.sayar.requests.RequestsConstants;
import com.sayar.requests.Response;
import com.sayar.requests.impl.RequestHandler;
import com.sayar.requests.impl.RequestHandlerFactory;

public class RequestsAsyncTask extends AsyncTask<RequestArguments, Integer, Response[]> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Response[] doInBackground(final RequestArguments... params) {
		// Check if any request arguments were passed.
		if (params.length == 0) {
			Log.w(RequestsConstants.LOG_TAG, "No arguments were passed to RequestsAsyncTask.");
			return null;
		}

		// Failed Rest Requests will have nulls at their position.
		final Response[] responses = new Response[params.length];

		final RequestHandler handler = RequestHandlerFactory.getRequestHandler();
		for (int i = 0; i != params.length; i++) {
			if (!this.isCancelled()) {
				try {
					// Execute Rest Request
					final Response res = handler.execute(params[i]);
					responses[i] = res;
					// We will let the user figure out whether he wants to add 1
					// or not.
					this.publishProgress(i);
				} catch (final AuthenticationException e) {
					Log.e(RequestsConstants.LOG_TAG, "Adding Authentication Failed.", e);
					responses[i] = null;

				} catch (final InvalidParameterException e) {
					Log.e(RequestsConstants.LOG_TAG, "Invalid Parameter Exception.", e);
					responses[i] = null;
				}
			} else {
				break;
			}
		}
		return responses;
	}
}

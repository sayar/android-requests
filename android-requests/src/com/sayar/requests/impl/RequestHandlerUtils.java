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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import android.util.Log;

import com.sayar.requests.RequestsConstants;

public final class RequestHandlerUtils {

	/**
	 * Converts an InputStream into a String.
	 * 
	 * @param is
	 *            InputStream
	 * @return Returns the stream in a String.
	 */
	public static String convertStreamToString(final InputStream is) {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		final StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (final IOException e) {
			Log.e(RequestsConstants.LOG_TAG, "Stream Reading Failed.", e);
		} finally {
			try {
				is.close();
			} catch (final IOException e) {
				Log.e(RequestsConstants.LOG_TAG, "Stream Failed to Close.", e);
			}
		}
		return sb.toString();
	}

	/**
	 * High Performance URL formatter. Or at least as High Performance as I can
	 * make it.
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String formatUrl(final String url, final Map<String, String> params)
	throws UnsupportedEncodingException {
		// Use String Buffers and immediately fill it with the URL.
		final StringBuffer formattedUrl = new StringBuffer(url);
		// Check if the params are empty.
		if (params != null && !params.isEmpty()) {
			// Append ?
			formattedUrl.append('?');
			// Iterate using the enhanced for loop synthax as the JIT will
			// optimize it away
			// See
			// http://developer.android.com/guide/practices/design/performance.html#foreach
			for (final Map.Entry<String, String> entry : params.entrySet()) {
				formattedUrl.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "UTF-8"))
				.append('&'); // Trailing & does not cause any issues
				// and we can skip a comparison.
			}
		}
		return formattedUrl.toString();
	}
}

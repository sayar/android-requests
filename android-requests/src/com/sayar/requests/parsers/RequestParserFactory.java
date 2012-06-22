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

package com.sayar.requests.parsers;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class RequestParserFactory {
	public static RequestParser DEFAULT_PARSER = new StringRequestParser();

	public static Map<String, RequestParser> REGISTRY = new HashMap<String, RequestParser>(3) {
		private static final long serialVersionUID = 1L;

		{
			this.put("json", new JsonRequestParser());
			// NOTE: Xml is left to the User as there are three ways to parse
			// Xml on Android. (DOM, SAX, XmlPull)
			// Depending on the User, they may also want to use a per request
			// parser with SAX instead of a generic
			// parser like the json parser.
			// put("xml", new XmlRequestParser());

		}
	};

	public static RequestParser getRequestParser(final String type) {
		if (RequestParserFactory.REGISTRY.containsKey(type)) {
			return RequestParserFactory.REGISTRY.get(type);
		} else {
			throw new InvalidParameterException("Request Parser Type is not registered.");
		}
	}

	public static RequestParser findRequestParser(final String contenttype) {
		for (final RequestParser value : RequestParserFactory.REGISTRY.values()) {
			if (value.canParse(contenttype)) {
				return value;
			}
		}
		return RequestParserFactory.DEFAULT_PARSER;
	}

}

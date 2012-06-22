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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRequestParser implements RequestParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sayar.requests.parsers.RequestParser#canParse(java.lang.String)
	 */
	public boolean canParse(final String contenttype) {
		return contenttype.equals("application/json") || contenttype.equals("application/x-javascript")
		|| contenttype.equals("text/javascript") || contenttype.equals("text/x-javascript")
		|| contenttype.equals("text/x-json") || contenttype.contains("json");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sayar.requests.parsers.RequestParser#getName()
	 */
	public String getName() {
		return "json";
	}

	/*
	 * Json Parser.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.sayar.requests.parsers.RequestParser#parse(java.lang.String)
	 */
	public Object parse(final String content) throws Exception {
		Object ret = null;
		try {
			ret = new ObjectMapper().readValue(content, Map.class);
		} catch (final JsonParseException e) {
			throw e;
		} catch (final JsonMappingException e) {
			try {
				ret = new ObjectMapper().readValue(content, List.class);
			} catch (final JsonMappingException e1) {
				try {
					ret = new ObjectMapper().readValue(content, Object.class);
				} catch (final JsonMappingException e2) {
					throw e2;
				} catch (final IOException e2) {
					throw e2;
				}
			} catch (final IOException e1) {
				throw e1;
			}
		} catch (final IOException e) {
			throw e;
		}
		return ret;
	}

}

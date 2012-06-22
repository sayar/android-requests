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

public enum RequestMethod {
	GET("GET"), PUT("PUT"), POST("POST"), DELETE("DELETE"), OPTIONS("OPTIONS"), HEAD("HEAD"), TRACE("TRACE"), UNKNOWN(
	"UNKNOWN");

	private final String value;

	RequestMethod(final String value) {
		this.value = value;
	}

	public static RequestMethod fromValue(final String value) {
		if (value != null) {
			for (final RequestMethod method : RequestMethod.values()) {
				if (method.value.equals(value)) {
					return method;
				}
			}
		}

		// you may return a default value
		return RequestMethod.getDefault();
		// or throw an exception
		// throw new IllegalArgumentException("Invalid color: " + value);
	}

	public String toValue() {
		return this.value;
	}

	public static RequestMethod getDefault() {
		return UNKNOWN;
	}
}

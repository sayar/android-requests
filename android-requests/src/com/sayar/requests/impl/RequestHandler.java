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

import java.security.InvalidParameterException;

import org.apache.http.auth.AuthenticationException;

import com.sayar.requests.RequestArguments;
import com.sayar.requests.Response;

public interface RequestHandler {
	public Response execute(RequestArguments args) throws AuthenticationException, InvalidParameterException;

	public void shutdown();
}

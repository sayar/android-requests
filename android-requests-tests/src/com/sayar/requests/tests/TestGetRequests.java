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

package com.sayar.requests.tests;

import com.sayar.requests.RequestArguments;
import com.sayar.requests.RequestMethod;
import com.sayar.requests.Requests;
import com.sayar.requests.Response;
import com.sayar.requests.async.RequestsAsyncTask;

import android.test.AndroidTestCase;

public class TestGetRequests extends AndroidTestCase {
	
	public TestGetRequests(){
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetSimple() throws Exception {
		Response response = Requests.get(AllTests.TWITTER_PUBLIC_TIMELINE);
		System.out.println(response.getContent());
	}

	public void testGetSimpleAsync() throws Exception {
		RequestsAsyncTask async_get = new RequestsAsyncTask(){
		     protected void onPostExecute(Response[] results) {
		    	 for(Response response: results){
		    		 System.out.println(response.getContent());
		    	 }
		     }
		};
		async_get.execute(new RequestArguments(RequestMethod.GET,
				AllTests.TWITTER_PUBLIC_TIMELINE));
		Thread.sleep(10000);
	}
	
	public void testGetSimpleIntentService() throws Exception{		
		/*
		ResultReceiver receiver = new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultData != null && resultData.containsKey(RequestsIntentService.REQUESTS_RESPONSE)) {
                	//	todo: do something.
                }
                else {
                	// TODO : WARN?
                }
            }   
        }; */
		
		
		//Intent msgIntent = new Intent(activity, RequestsIntentService.class);
		//msgIntent.putExtra(RequestsIntentService.REQUESTS_ARGUMENTS, new RequestArguments(RequestMethod.GET,
		//		AllTests.TWITTER_PUBLIC_TIMELINE));
		//activity.startService(msgIntent);
	}
}

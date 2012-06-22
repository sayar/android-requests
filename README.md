# Android-Requests: HTTP for Robots with Humans Hearts

Android-Requests is a Apache v2.0 licensed, multi-target, high-performance, *dead-simple* REST Library for building Android REST applications. It is inspired by python-requests in its simplicity and vision. 

There is no single unified method for calling REST APIs on the Android platform, this library serves to remedy that problem. The library will select the most appropriate underlying HttpClient (Apache, Android Apache, HttpUrlConn) depending the Android version and it will provide a single, extensible interface. It also simplifies response parsing for Json, Xml, Yaml REST APIs (more can be easily added).

* NOTE: No code was translated from the python-requests library and the API was not copied or mimicked.

Things shouldn't be this easy. Not in Java. ;)

## Examples

### Simple Blocking Example

    import com.sayar.requests.Requests;
    import com.sayar.requests.Response;
    ...
    Response response = Requests.get("http://api.twitter.com/1/statuses/public_timeline.json");
    System.out.println(response);

### Simple Non-Blocking, Async Example

    import com.sayar.requests.Response;
    import com.sayar.requests.RequestMethod;
    import com.sayar.requests.async.RequestsAsyncTask;
    ...
	RequestsAsyncTask async_get = new RequestsAsyncTask(){
		 protected void onPostExecute(Response[] results) {
			 for(Response response: results){
				 System.out.println(response);
			 }
		 }
	};
	async_get.execute(new RequestArguments(
		RequestMethod.GET,
		"http://api.twitter.com/1/statuses/public_timeline.json"
	));

## Features

* Dead simple way to make Async requests from your UI thread
* Provides an uncommonly simple API
* Best HttpClient selection depending on Android Target
    So long as there is fragmentation on Android, android-requests will ensure your calls work 
    consistently and without error on all platforms. You will no longer have to deal with 
    the different bugs in the the different HttpClients of each platform.
* High Performance, Async Requests (Can be executed in Blocking and NonBlocking Modes)
* High Performance Json/Xml Parsing. (Yaml Coming Soon)
* Automatic Gzip Decompression
* Sensible HttpClient Defaults
* Connection-Pooling
* Graceful handling of SSL Caches.
* Basic/Digest Authentication (Digest Coming Soon)
* International Domains and Urls
* SSL Caching
* Sessions with Cookies (Coming Soon)
* And more coming soon. (See TODO for how to contribute)


## Contribute

1. Take a look at the TODOs.
2. Engage the author.
3. Write a test for your change first.
4. Fork, commit changes to a different branch than master, submit a pull-request. Add yourself to AUTHORS.


## Some More Examples

### Adding a Specialized Request Parser

If you want to add your own Yaml parser to the requests engine, here is a simple example of how to do it. 

**STEP 1:** Implement the RequestParser interface.

	public class YamlRequestParser implements RequestParser{
		public boolean canParse(String contenttype) {
			return contenttype.equals("application/yaml") || 
					contenttype.equals("text/yaml") ||
					contenttype.equals("text/x-yaml") ||
					contenttype.contains("yaml");
		}
	
		public String getName() {
			return "yaml";
		}
	
		public Object parse(String content) throws Exception {
			// TODO Auto-generated method stub
			
			// TODO IMPLEMENT YAML PARSER HERE.
			// NOTE This method and object should not contain
			// state that persists past each parse. Do not
			// heed the warnings at your own risk.
			
			return null;
		}
	}
	
**STEP 2:** Register the parser in the RequestParserFactory on application startup, this will register an instance that will be reused for all requests. 
	
	RequestParserFactory.REGISTRY.put("yaml", new YamlRequestParser());

**STEP 3:** There is no step 3.

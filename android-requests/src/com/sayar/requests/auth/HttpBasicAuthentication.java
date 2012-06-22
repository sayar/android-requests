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

package com.sayar.requests.auth;

import org.apache.http.Header;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents HttpBasic authentication.
 * 
 * @author ramisayar
 * 
 */
public class HttpBasicAuthentication implements HttpAuthentication, Parcelable {
	private String username;
	private String password;

	/**
	 * Http Basic Authentication Constructor
	 */
	public HttpBasicAuthentication() {
	}

	/**
	 * Http Basic Authentication Constructor
	 * 
	 * @param username
	 * @param password
	 */
	public HttpBasicAuthentication(final String username, final String password) {
		this.username = username;
		this.password = password;
	}

	private HttpBasicAuthentication(final Parcel in) {
		this.username = in.readString();
		this.password = in.readString();
	}

	/**
	 * Getter for the username.
	 * 
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Setter for the username.
	 * 
	 * @param username
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Getter for the password.
	 * 
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Setter for the password.
	 * 
	 * @param password
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sayar.requests.auth.HttpAuthentication#getHttpHeader(org.apache.http
	 * .client.methods.HttpUriRequest)
	 */
	public Header getHttpHeader(final HttpUriRequest request) throws AuthenticationException {
		final UsernamePasswordCredentials creds = new UsernamePasswordCredentials(this.username, this.password);
		return new BasicScheme().authenticate(creds, request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.password == null ? 0 : this.password.hashCode());
		result = prime * result + (this.username == null ? 0 : this.username.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final HttpBasicAuthentication other = (HttpBasicAuthentication) obj;
		if (this.password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!this.password.equals(other.password)) {
			return false;
		}
		if (this.username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!this.username.equals(other.username)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HttpBasicAuthentication [username=" + this.username + "]";
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(final Parcel dest, final int flags) {
		dest.writeString(this.username);
		dest.writeString(this.password);
	}

	public static final Parcelable.Creator<HttpBasicAuthentication> CREATOR = new Parcelable.Creator<HttpBasicAuthentication>() {
		public HttpBasicAuthentication createFromParcel(final Parcel in) {
			return new HttpBasicAuthentication(in);
		}

		public HttpBasicAuthentication[] newArray(final int size) {
			return new HttpBasicAuthentication[size];
		}
	};
}

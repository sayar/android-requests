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

import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.sayar.requests.parsers.RequestParser;
import com.sayar.requests.parsers.RequestParserFactory;

/**
 * This class contains response of a request.
 * 
 * This class implements the Parcelable interface.
 * 
 * @author ramisayar
 * 
 */
public class Response implements Parcelable {
	private Object content = null;
	private String raw = null;

	private String statusMessage;
	private int code;
	private String parsedAs;
	private ProtocolVersion responseVersion;

	/**
	 * Empty Constructor
	 */
	public Response() {
	}

	/**
	 * Constructor
	 * 
	 * @param statusLine
	 * @param parsedAs
	 * @param raw
	 * @param content
	 */
	public Response(final StatusLine statusLine, final String parsedAs, final String raw, final Object content) {
		this.statusMessage = statusLine.getReasonPhrase();
		this.code = statusLine.getStatusCode();
		this.parsedAs = parsedAs;
		this.content = content;
		this.raw = raw;
	}

	public Response(final String message, final int code, final String parsedAs, final String raw, final Object content) {
		this.statusMessage = message;
		this.code = code;
		this.parsedAs = parsedAs;
		this.content = content;
		this.raw = raw;
	}

	private Response(final Parcel bundle) {
		this.parsedAs = bundle.readString();
		this.raw = bundle.readString();
		this.statusMessage = bundle.readString();
		this.code = bundle.readInt();
	}

	public String getStatusMessage() {
		return this.statusMessage;
	}

	public int getCode() {
		return this.code;
	}

	public void setStatusAndCode(final StatusLine statusLine) {
		this.statusMessage = statusLine.getReasonPhrase();
		this.code = statusLine.getStatusCode();
	}

	public void setStatusMessage(final String message) {
		this.statusMessage = message;
	}

	public void setCode(final int code) {
		this.code = code;
	}

	public ProtocolVersion getResponseVersion() {
		return this.responseVersion;
	}

	public void setResponseVersion(final ProtocolVersion responseVersion) {
		this.responseVersion = responseVersion;
	}

	public String parsedAs() {
		return this.parsedAs;
	}

	public void setParsedAs(final String parsedAs) {
		this.parsedAs = parsedAs;
	}

	public Object getContent() {
		// Check if content is null and parseAs is not empty.
		// This state will happen if we just sent the response
		// object through the parcelable protocol.
		if (this.content == null && !TextUtils.isEmpty(this.parsedAs)) {
			return this.parse();
		}
		return this.content;
	}

	public void setContent(final Object content) {
		this.content = content;
	}

	public String getRaw() {
		return this.raw;
	}

	public void setRaw(final String raw) {
		this.raw = raw;
	}

	/**
	 * This a convenience method that will reparse the raw value. This method's
	 * main purpose is to reparse
	 * 
	 * @return
	 */
	public Object parse() {
		if (!TextUtils.isEmpty(this.parsedAs)) {
			return this.parseAs(this.parsedAs);
		} else {
			return null;
		}
	}

	public Object parseAs(final String parseAs) {
		final RequestParser parser = RequestParserFactory.getRequestParser(this.parsedAs);
		try {
			return parser.parse(this.getRaw());
		} catch (final Exception e) {
			Log.e(RequestsConstants.LOG_TAG, "Parsing Exception Occured.", e);
		}
		return null;
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
		result = prime * result + (this.content == null ? 0 : this.content.hashCode());
		result = prime * result + (this.parsedAs == null ? 0 : this.parsedAs.hashCode());
		result = prime * result + (this.raw == null ? 0 : this.raw.hashCode());
		result = prime * result + (this.responseVersion == null ? 0 : this.responseVersion.hashCode());
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
		final Response other = (Response) obj;
		if (this.content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!this.content.equals(other.content)) {
			return false;
		}
		if (this.parsedAs == null) {
			if (other.parsedAs != null) {
				return false;
			}
		} else if (!this.parsedAs.equals(other.parsedAs)) {
			return false;
		}
		if (this.raw == null) {
			if (other.raw != null) {
				return false;
			}
		} else if (!this.raw.equals(other.raw)) {
			return false;
		}
		if (this.responseVersion == null) {
			if (other.responseVersion != null) {
				return false;
			}
		} else if (!this.responseVersion.equals(other.responseVersion)) {
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
		return "Response [raw=" + this.raw + "]";
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(final Parcel dest, final int flags) {
		dest.writeString(this.parsedAs);
		dest.writeString(this.raw);
		dest.writeString(this.statusMessage);
		dest.writeInt(this.code);
		// TODO:
		// dest.writeString(responseVersion.getProtocol());
	}

	public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
		public Response createFromParcel(final Parcel in) {
			return new Response(in);
		}

		public Response[] newArray(final int size) {
			return new Response[size];
		}
	};

}

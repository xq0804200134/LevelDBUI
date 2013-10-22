package org.iq80.leveldn.ui.util;

import mx4j.tools.adaptor.http.HttpConstants;

public class HttpUtil {
	 /**
	    * Gets a message apropriate for a give HTTP code
	    *
	    * @param code Reference Code
	    * @return The result message
	    * @see HttpConstants
	    */
	   public static String getCodeMessage(int code)
	   {
	      switch (code)
	      {
	         case HttpConstants.STATUS_OKAY:
	            return "OK";
	         case HttpConstants.STATUS_NO_CONTENT:
	            return "No Content";
	         case HttpConstants.STATUS_MOVED_PERMANENTLY:
	            return "Moved Permanently";
	         case HttpConstants.STATUS_MOVED_TEMPORARILY:
	            return "Moved Temporarily";
	         case HttpConstants.STATUS_BAD_REQUEST:
	            return "Bad Request";
	         case HttpConstants.STATUS_FORBIDDEN:
	            return "Forbidden";
	         case HttpConstants.STATUS_NOT_FOUND:
	            return "Not Found";
	         case HttpConstants.STATUS_NOT_ALLOWED:
	            return "Method Not Allowed";
	         case HttpConstants.STATUS_INTERNAL_ERROR:
	            return "Internal Server Error";
	         case HttpConstants.STATUS_AUTHENTICATE:
	            return "Authentication requested";
	         case HttpConstants.STATUS_NOT_IMPLEMENTED:
	            return "Not Implemented";
	         default:
	            return "Unknown Code (" + code + ")";
	      }
	   }
}

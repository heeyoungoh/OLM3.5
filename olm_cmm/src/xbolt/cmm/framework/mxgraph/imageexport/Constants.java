/**
 * $Id: ExportServlet.java,v 1.4 2012-03-27 13:08:48 gaudenz Exp $
 * Copyright (c) 2011-2012, JGraph Ltd
 */
package xbolt.cmm.framework.mxgraph.imageexport;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Constants
{
	/**
	 * Contains an empty image.
	 */
	public static BufferedImage EMPTY_IMAGE;


	/**
	 * Initializes the empty image.
	 */
	static
	{
		try
		{
			EMPTY_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		}
		catch (Exception e)
		{
			// ignore
		}
	}


	/**
	 * Maximum size (in bytes) for request payloads. Default is 10485760 (10MB).
	 */
	public static final int MAX_REQUEST_SIZE = 30485760;


	/**
	 * Maximum width for exports. Default is 5000px.
	 */
	public static final int MAX_WIDTH = 30000;


	/**
	 * Maximum height for exports. Default is 5000px.
	 */
	public static final int MAX_HEIGHT = 50000;  


	/**
	 * Default image domain for relative images.
	 */
	public static final String IMAGE_DOMAIN = "http://www.draw.io/";


	/**
	 * Image domains that map to our clipart
	 */
	public static final ArrayList<String> IMAGE_DOMAIN_MATCHES = new ArrayList<String>();
}


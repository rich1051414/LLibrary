package net.ilexiconn.llibrary;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ArrayList to handle IContentHandlers
 * 
 * @author iLexiconn
 * @see net.ilexiconn.llibrary.IContentHandler
 */
public class ContentHandlerList extends ArrayList<IContentHandler>
{
    /**
	 * 
	 */
    private static final long serialVersionUID = -3794820778331030901L;

    /**
     * Create a new list with IContentHandlers
     * 
     * @param contentHandlers
     *            the list of IContentHandlers
     * @return a new instance of the list
     * @see net.ilexiconn.llibrary.IContentHandler
     */
    public static ContentHandlerList createList(IContentHandler... contentHandlers)
    {
        ContentHandlerList list = new ContentHandlerList();
        list.addAll(Arrays.asList(contentHandlers));
        return list;
    }

    /**
     * Initialize all the IContentHandlers in this list.
     * 
     * @see net.ilexiconn.llibrary.IContentHandler
     */
    public void init()
    {
        for (IContentHandler contentHandler : this)
        {
            try
            {
                contentHandler.init();
                contentHandler.gameRegistry();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

package com.marcelmika.lims.portal.processor;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 6:01 PM
 */
public class PortletProcessorUtil {

    // TODO: Inject via bean
    private static PortletProcessor portletProcessor = new PortletProcessorImpl();

    /**
     * Return Portlet Processor implementation
     *
     * @return PortletProcessor
     */
    public static PortletProcessor getPortletProcessor() {
        return portletProcessor;
    }

}

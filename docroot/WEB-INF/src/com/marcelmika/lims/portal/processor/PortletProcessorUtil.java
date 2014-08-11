package com.marcelmika.lims.portal.processor;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 6:01 PM
 */
public class PortletProcessorUtil {

    private static PortletProcessor portletProcessor;

    /**
     * Return Portlet Processor implementation
     *
     * @return PortletProcessor
     */
    public static PortletProcessor getPortletProcessor() {
        return portletProcessor;
    }

    /**
     * Injects proper PortletProcessor via Dependency Injection
     *
     * @param portletProcessor PortletProcessor
     */
    public void setPortletProcessor(PortletProcessor portletProcessor) {
        PortletProcessorUtil.portletProcessor = portletProcessor;
    }

}

/*
 * %Z%file      %M%
 * %Z%author    IBM Corp.
 * %Z%version   %I%
 * %Z%lastedit      %E%
 */
/*
 * Copyright IBM Corp. 1999-2000.  All rights reserved.
 * 
 * The program is provided "as is" without any warranty express or implied,
 * including the warranty of non-infringement and the implied warranties of
 * merchantibility and fitness for a particular purpose. IBM will not be
 * liable for any damages suffered by you or any third party claim against 
 * you regarding the Program.
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 * 
 * Copyright 2006 Sun Microsystems, Inc.  Tous droits reserves.
 * Ce logiciel est propriete de Sun Microsystems, Inc.
 * Distribue par des licences qui en restreignent l'utilisation. 
 *
 */


package javax.management.modelmbean;

import javax.management.*;

import javax.management.InstanceNotFoundException;

/**                    
 * This interface must be implemented by the ModelMBeans. An implementation of this interface 
 * must be shipped with every JMX Agent. 
 * <P>
 * Java resources wishing to be manageable instantiate the ModelMBean using the MBeanServer's 
 * createMBean method.  The resource then sets the ModelMBeanInfo (with Descriptors) for the ModelMBean
 * instance. The attributes and operations exposed via the ModelMBeanInfo for the ModelMBean are accessible
 * from MBeans, connectors/adaptors like other MBeans. Through the ModelMBeanInfo Descriptors, values and methods in
 * the managed application can be defined and mapped to attributes and operations of the ModelMBean.
 * This mapping can be defined during development in an XML formatted file or dynamically and
 * programmatically at runtime. 
 * <P>
 * Every ModelMBean which is instantiated in the MBeanServer becomes manageable: 
 * its attributes and operations
 * become remotely accessible through the connectors/adaptors connected to that MBeanServer. 
 * A Java object cannot be registered in the MBeanServer unless it is a JMX compliant MBean.
 * By instantiating a ModelMBean, resources are guaranteed that the MBean is valid.
 * <P>
 * MBeanException and RuntimeOperationsException must be thrown on every public method.  This allows 
 * for wrapping exceptions from distributed communications (RMI, EJB, etc.).  These exceptions do
 * not have to be thrown by the implementation except in the scenarios described in the specification 
 * and javadoc.
 *
 * @since 1.5
 */

public interface ModelMBeanNotificationBroadcaster extends NotificationBroadcaster
{

	/** 
	 * Sends a Notification which is passed in to the registered
	 * Notification listeners on the ModelMBean as a
	 * jmx.modelmbean.generic notification.
	 *
	 * @param ntfyObj The notification which is to be passed to
	 * the 'handleNotification' method of the listener object.
	 *
	 * @exception MBeanException Wraps a distributed communication Exception.
	 * @exception RuntimeOperationsException Wraps an IllegalArgumentException: 
	 *       The Notification object passed in parameter is null.
	 *
	 */

	public void sendNotification(Notification ntfyObj)
	throws MBeanException, RuntimeOperationsException;

	/** 
	 * Sends a Notification which contains the text string that is passed in
	 * to the registered Notification listeners on the ModelMBean.
	 *
	 * @param ntfyText The text which is to be passed in the Notification to the 'handleNotification'
	 * method of the listener object.  
	 * the constructed Notification will be:
	 *   type        "jmx.modelmbean.generic"
	 *   source      this ModelMBean instance
	 *   sequence    1
	 *
	 *
	 * @exception MBeanException Wraps a distributed communication Exception.
	 * @exception RuntimeOperationsException Wraps an IllegalArgumentException: 
	 *       The Notification text string passed in parameter is null.
	 *
	 */
	public void sendNotification(String ntfyText)
	throws MBeanException, RuntimeOperationsException;

	/** 
	 * Sends an attributeChangeNotification which is passed in to
	 * the registered attributeChangeNotification listeners on the
	 * ModelMBean.
	 *
	 * @param notification The notification which is to be passed
	 * to the 'handleNotification' method of the listener object.
	 *
	 * @exception MBeanException Wraps a distributed communication Exception.
	 * @exception RuntimeOperationsException Wraps an IllegalArgumentException: The AttributeChangeNotification object passed in parameter is null.
	 *
	 */
	public void sendAttributeChangeNotification(AttributeChangeNotification notification)
	throws MBeanException, RuntimeOperationsException;


	/** 
	 * Sends an attributeChangeNotification which contains the old value and new value for the
	 * attribute to the registered AttributeChangeNotification listeners on the ModelMBean.
	 * <P>
	 * @param oldValue The original value for the Attribute
	 * @param newValue The current value for the Attribute
	 *<P>
	 * <PRE>  
	 * The constructed attributeChangeNotification will be:
	 *   type        "jmx.attribute.change"
	 *   source      this ModelMBean instance
	 *   sequence    1
	 *   attributeName oldValue.getName()
	 *   attributeType oldValue's class
	 *   attributeOldValue oldValue.getValue()
	 *   attributeNewValue newValue.getValue()
	 * </PRE>
	 *
	 * @exception MBeanException Wraps a distributed communication Exception.
	 * @exception RuntimeOperationsException Wraps an IllegalArgumentException: An Attribute object passed in parameter is null
	 * or the names of the two Attribute objects in parameter are not the same.
	 */
	public void sendAttributeChangeNotification(Attribute oldValue, Attribute newValue)
	throws MBeanException, RuntimeOperationsException;


	/**
	 * Registers an object which implements the NotificationListener interface as a listener.  This
	 * object's 'handleNotification()' method will be invoked when any attributeChangeNotification is issued through
	 * or by the ModelMBean.  This does not include other Notifications.  They must be registered
	 * for independently. An AttributeChangeNotification will be generated for this attributeName.
	 *
	 * @param listener The listener object which will handles notifications emitted by the registered MBean.
	 * @param attributeName The name of the ModelMBean attribute for which to receive change notifications.
	 *      If null, then all attribute changes will cause an attributeChangeNotification to be issued.
	 * @param handback The context to be sent to the listener with the notification when a notification is emitted.
	 *
	 * @exception IllegalArgumentException The listener cannot be null.
	 * @exception MBeanException Wraps a distributed communication Exception.
	 * @exception RuntimeOperationsException Wraps an IllegalArgumentException The attribute name passed in parameter does not exist.
	 *
	 * @see #removeAttributeChangeNotificationListener
	 */
	public void addAttributeChangeNotificationListener(NotificationListener listener,
							   String attributeName, 
							   Object handback)
	throws MBeanException, RuntimeOperationsException, IllegalArgumentException;


	/**
	 * Removes a listener for attributeChangeNotifications from the RequiredModelMBean.
	 *
	 * @param listener The listener name which was handling notifications emitted by the registered MBean.
	 * This method will remove all information related to this listener.
	 * @param attributeName The attribute for which the listener no longer wants to receive attributeChangeNotifications.
	 * If null the listener will be removed for all attributeChangeNotifications. 
	 *
	 * @exception ListenerNotFoundException The listener is not registered in the MBean or is null.
	 * @exception MBeanException Wraps a distributed communication Exception.
	 * @exception RuntimeOperationsException Wraps an IllegalArgumentException If the inAttributeName parameter does not
	 * correspond to an attribute name.
	 *
	 * @see #addAttributeChangeNotificationListener
	 */

	public void removeAttributeChangeNotificationListener(NotificationListener listener,
							      String attributeName)
	throws MBeanException, RuntimeOperationsException, ListenerNotFoundException;

}

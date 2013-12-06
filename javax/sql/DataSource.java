/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Wrapper;

/** 
 * <p>A factory for connections to the physical data source that this
 * <code>DataSource</code> object represents.  An alternative to the
 * <code>DriverManager</code> facility, a <code>DataSource</code> object
 * is the preferred means of getting a connection. An object that implements
 * the <code>DataSource</code> interface will typically be
 * registered with a naming service based on the 
 * Java<sup><font size=-2>TM</font></sup> Naming and Directory (JNDI) API.
 * <P>
 * The <code>DataSource</code> interface is implemented by a driver vendor.
 * There are three types of implementations:
 * <OL>
 *   <LI>Basic implementation -- produces a standard <code>Connection</code> 
 *       object
 *   <LI>Connection pooling implementation -- produces a <code>Connection</code>
 *       object that will automatically participate in connection pooling.  This
 *       implementation works with a middle-tier connection pooling manager.
 *   <LI>Distributed transaction implementation -- produces a
 *       <code>Connection</code> object that may be used for distributed
 *       transactions and almost always participates in connection pooling. 
 *       This implementation works with a middle-tier 
 *       transaction manager and almost always with a connection 
 *       pooling manager.
 * </OL>
 * <P>
 * A <code>DataSource</code> object has properties that can be modified
 * when necessary.  For example, if the data source is moved to a different
 * server, the property for the server can be changed.  The benefit is that
 * because the data source's properties can be changed, any code accessing
 * that data source does not need to be changed.
 * <P>
 * A driver that is accessed via a <code>DataSource</code> object does not 
 * register itself with the <code>DriverManager</code>.  Rather, a
 * <code>DataSource</code> object is retrieved though a lookup operation
 * and then used to create a <code>Connection</code> object.  With a basic
 * implementation, the connection obtained through a <code>DataSource</code>
 * object is identical to a connection obtained through the
 * <code>DriverManager</code> facility.
 *
 * @since 1.4
 */

public interface DataSource  extends CommonDataSource,Wrapper {

  /**
   * <p>Attempts to establish a connection with the data source that
   * this <code>DataSource</code> object represents.
   *
   * @return  a connection to the data source
   * @exception SQLException if a database access error occurs
   */
  Connection getConnection() throws SQLException;
      
  /**
   * <p>Attempts to establish a connection with the data source that
   * this <code>DataSource</code> object represents.
   *
   * @param username the database user on whose behalf the connection is 
   *  being made
   * @param password the user's password
   * @return  a connection to the data source
   * @exception SQLException if a database access error occurs
   * @since 1.4
   */
  Connection getConnection(String username, String password) 
    throws SQLException;

}






/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.sql;

import java.sql.*;

/**
 * A factory for <code>XAConnection</code> objects that is used internally.  
 * An object that implements the <code>XADataSource</code> interface is
 * typically registered with a naming service that uses the
 * Java Naming and Directory Interface<sup><font size=-3>TM</font></sup>
 * (JNDI). 
 *
 * @since 1.4
 */

public interface XADataSource extends CommonDataSource {

  /**
   * Attempts to establish a physical database connection that can be
   * used in a distributed transaction.
   *
   * @return  an <code>XAConnection</code> object, which represents a
   *          physical connection to a data source, that can be used in
   *          a distributed transaction
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.4
   */
  XAConnection getXAConnection() throws SQLException;
      
  /**
   * Attempts to establish a physical database connection, using the given
   * user name and password. The connection that is returned is one that
   * can be used in a distributed transaction.
   *
   * @param user the database user on whose behalf the connection is being made
   * @param password the user's password
   * @return  an <code>XAConnection</code> object, which represents a
   *          physical connection to a data source, that can be used in
   *          a distributed transaction
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.4
   */
  XAConnection getXAConnection(String user, String password) 
    throws SQLException;        
 } 






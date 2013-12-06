/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.security.cert;

/**
 * A specification of the result of a certification path builder algorithm. 
 * All results returned by the {@link CertPathBuilder#build 
 * CertPathBuilder.build} method must implement this interface.
 * <p>
 * At a minimum, a <code>CertPathBuilderResult</code> contains the 
 * <code>CertPath</code> built by the <code>CertPathBuilder</code> instance. 
 * Implementations of this interface may add methods to return implementation 
 * or algorithm specific information, such as debugging information or 
 * certification path validation results.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Unless otherwise specified, the methods defined in this interface are not
 * thread-safe. Multiple threads that need to access a single
 * object concurrently should synchronize amongst themselves and
 * provide the necessary locking. Multiple threads each manipulating
 * separate objects need not synchronize.
 *
 * @see CertPathBuilder
 *
 * @version 	%I% %G%
 * @since	1.4
 * @author	Sean Mullan
 */
public interface CertPathBuilderResult extends Cloneable {

    /**
     * Returns the built certification path.
     *
     * @return the certification path (never <code>null</code>)
     */
    CertPath getCertPath();

    /**
     * Makes a copy of this <code>CertPathBuilderResult</code>. Changes to the
     * copy will not affect the original and vice versa.
     *
     * @return a copy of this <code>CertPathBuilderResult</code>
     */
    Object clone();
}

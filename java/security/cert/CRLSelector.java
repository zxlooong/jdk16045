/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.security.cert;

/**
 * A selector that defines a set of criteria for selecting <code>CRL</code>s.
 * Classes that implement this interface are often used to specify
 * which <code>CRL</code>s should be retrieved from a <code>CertStore</code>.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Unless otherwise specified, the methods defined in this interface are not
 * thread-safe. Multiple threads that need to access a single
 * object concurrently should synchronize amongst themselves and
 * provide the necessary locking. Multiple threads each manipulating
 * separate objects need not synchronize.
 *
 * @see CRL
 * @see CertStore
 * @see CertStore#getCRLs
 *
 * @version 	%I% %G%
 * @author	Steve Hanna
 * @since	1.4
 */
public interface CRLSelector extends Cloneable {

    /**
     * Decides whether a <code>CRL</code> should be selected.
     *
     * @param	crl     the <code>CRL</code> to be checked
     * @return	<code>true</code> if the <code>CRL</code> should be selected, 
     * <code>false</code> otherwise
     */
    boolean match(CRL crl);

    /**
     * Makes a copy of this <code>CRLSelector</code>. Changes to the 
     * copy will not affect the original and vice versa.
     *
     * @return a copy of this <code>CRLSelector</code>
     */
    Object clone();
}

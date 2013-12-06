/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.security.cert;

import java.security.InvalidAlgorithmParameterException;

/**
 * The <i>Service Provider Interface</i> (<b>SPI</b>) 
 * for the {@link CertPathBuilder CertPathBuilder} class. All 
 * <code>CertPathBuilder</code> implementations must include a class (the 
 * SPI class) that extends this class (<code>CertPathBuilderSpi</code>) and 
 * implements all of its methods. In general, instances of this class should 
 * only be accessed through the <code>CertPathBuilder</code> class. For 
 * details, see the Java Cryptography Architecture. 
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Instances of this class need not be protected against concurrent
 * access from multiple threads. Threads that need to access a single
 * <code>CertPathBuilderSpi</code> instance concurrently should synchronize
 * amongst themselves and provide the necessary locking before calling the
 * wrapping <code>CertPathBuilder</code> object.
 * <p>
 * However, implementations of <code>CertPathBuilderSpi</code> may still
 * encounter concurrency issues, since multiple threads each
 * manipulating a different <code>CertPathBuilderSpi</code> instance need not
 * synchronize.
 *
 * @version 	%I% %G%
 * @since	1.4
 * @author	Sean Mullan
 */
public abstract class CertPathBuilderSpi {

    /**
     * The default constructor.
     */
    public CertPathBuilderSpi() { }

    /**
     * Attempts to build a certification path using the specified 
     * algorithm parameter set.
     *
     * @param params the algorithm parameters
     * @return the result of the build algorithm
     * @throws CertPathBuilderException if the builder is unable to construct 
     * a certification path that satisfies the specified parameters
     * @throws InvalidAlgorithmParameterException if the specified parameters 
     * are inappropriate for this <code>CertPathBuilder</code>
     */
    public abstract CertPathBuilderResult engineBuild(CertPathParameters params)
	throws CertPathBuilderException, InvalidAlgorithmParameterException;
}

/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL.  Use is subject to license terms.
 */

package com.sun.mirror.type;


import com.sun.mirror.declaration.*;


/**
 * Represents a class type.
 * Interface types are represented separately by {@link InterfaceType}.
 * Note that an {@linkplain EnumType enum} is a kind of class.
 *
 * <p> While a {@link ClassDeclaration} represents the <i>declaration</i>
 * of a class, a <tt>ClassType</tt> represents a class <i>type</i>.
 * See {@link TypeDeclaration} for more on this distinction.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @version %I% %E%
 * @since 1.5
 */

public interface ClassType extends DeclaredType {

    /**
     * {@inheritDoc}
     */
    ClassDeclaration getDeclaration();

    /**
     * Returns the class type that is a direct supertype of this one.
     * This is the superclass of this type's declaring class, with any
     * type arguments substituted in.
     * The only class with no superclass is <tt>java.lang.Object</tt>,
     * for which this method returns <tt>null</tt>.
     *
     * <p> For example, the class type extended by
     * {@code java.util.TreeSet<String>} is
     * {@code java.util.AbstractSet<String>}.
     *
     * @return the class type that is a direct supertype of this one,
     * or <tt>null</tt> if there is none
     */
    ClassType getSuperclass();
}

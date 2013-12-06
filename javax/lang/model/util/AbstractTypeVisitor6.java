/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.lang.model.util;

import javax.lang.model.type.*;

/**
 * A skeletal visitor of types with default behavior appropriate for
 * the version 6 language level.
 * 
 * <p> <b>WARNING:</b> The {@code TypeVisitor} interface implemented
 * by this class may have methods added to it in the future to
 * accommodate new, currently unknown, language structures added to
 * future versions of the Java&trade; programming language.
 * Therefore, methods whose names begin with {@code "visit"} may be
 * added to this class in the future; to avoid incompatibilities,
 * classes which extend this class should not declare any instance
 * methods with names beginning with {@code "visit"}.
 * 
 * <p>When such a new visit method is added, the default
 * implementation in this class will be to call the {@link
 * #visitUnknown visitUnknown} method.  A new abstract type visitor
 * class will also be introduced to correspond to the new language
 * level; this visitor will have different default behavior for the
 * visit method in question.  When the new visitor is introduced, all
 * or portions of this visitor may be deprecated.
 *
 * @param <R> the return type of this visitor's methods.  Use {@link
 *            Void} for visitors that do not need to return results.
 * @param <P> the type of the additional parameter to this visitor's
 *            methods.  Use {@code Void} for visitors that do not need an
 *            additional parameter.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version %I% %E%
 * @since 1.6
 */ 
public abstract class AbstractTypeVisitor6<R, P> implements TypeVisitor<R, P> {
    /**
     * Constructor for concrete subclasses to call.
     */
    protected AbstractTypeVisitor6() {}

    /**
     * Visits any type mirror as if by passing itself to that type
     * mirror's {@link TypeMirror#accept accept} method.  The
     * invocation {@code v.visit(t, p)} is equivalent to {@code
     * t.accept(v, p)}.
     *
     * @param t  the type to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    public final R visit(TypeMirror t, P p) {
	return t.accept(this, p);
    }

    /**
     * Visits any type mirror as if by passing itself to that type
     * mirror's {@link TypeMirror#accept accept} method and passing
     * {@code null} for the additional parameter.  The invocation
     * {@code v.visit(t)} is equivalent to {@code t.accept(v, null)}.
     *
     * @param t  the type to visit
     * @return a visitor-specified result
     */
    public final R visit(TypeMirror t) {
	return t.accept(this, null);
    }

    /**
     * {@inheritDoc}
     *
     * <p> The default implementation of this method in {@code
     * AbstractTypeVisitor6} will always throw {@code
     * UnknownTypeException}.  This behavior is not required of a
     * subclass.
     *
     * @param t  the type to visit
     * @return a visitor-specified result
     * @throws UnknownTypeException
     *	a visitor implementation may optionally throw this exception
     */
    public R visitUnknown(TypeMirror t, P p) {
	throw new UnknownTypeException(t, p);
    }
}

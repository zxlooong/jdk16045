/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.lang.model.element;

import javax.lang.model.util.*;

/**
 * A visitor of program elements, in the style of the visitor design
 * pattern.  Classes implementing this interface are used to operate
 * on an element when the kind of element is unknown at compile time.
 * When a visitor is passed to an element's {@link Element#accept
 * accept} method, the <tt>visit<i>XYZ</i></tt> method most applicable
 * to that element is invoked.
 *
 * <p> Classes implementing this interface may or may not throw a
 * {@code NullPointerException} if the additional parameter {@code p}
 * is {@code null}; see documentation of the implementing class for
 * details.
 * 
 * <p> <b>WARNING:</b> It is possible that methods will be added to
 * this interface to accommodate new, currently unknown, language
 * structures added to future versions of the Java&trade; programming
 * language.  Therefore, visitor classes directly implementing this
 * interface may be source incompatible with future versions of the
 * platform.  To avoid this source incompatibility, visitor
 * implementations are encouraged to instead extend the appropriate
 * abstract visitor class that implements this interface.  However, an
 * API should generally use this visitor interface as the type for
 * parameters, return type, etc. rather than one of the abstract
 * classes.
 *
 * @param <R> the return type of this visitor's methods.  Use {@link
 * 	      Void} for visitors that do not need to return results.
 * @param <P> the type of the additional parameter to this visitor's
 *            methods.  Use {@code Void} for visitors that do not need an
 *            additional parameter.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @version %I% %E%
 * @see AbstractElementVisitor6
 * @since 1.6
 */
public interface ElementVisitor<R, P> {
    /**
     * Visits an element.
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visit(Element e, P p);

    /**
     * A convenience method equivalent to {@code v.visit(e, null)}.
     * @param e  the element to visit
     * @return a visitor-specified result
     */
    R visit(Element e);

    /**
     * Visits a package element.
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitPackage(PackageElement e, P p);

    /**
     * Visits a type element.
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitType(TypeElement e, P p);

    /**
     * Visits a variable element.
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitVariable(VariableElement e, P p);

    /**
     * Visits an executable element.
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitExecutable(ExecutableElement e, P p);

    /**
     * Visits a type parameter element.
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitTypeParameter(TypeParameterElement e, P p);

    /**
     * Visits an unknown kind of element.
     * This can occur if the language evolves and new kinds
     * of elements are added to the {@code Element} hierarchy.
     *
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     * @throws UnknownElementException
     *	a visitor implementation may optionally throw this exception
     */
    R visitUnknown(Element e, P p);
}

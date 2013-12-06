/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 */
/*
 * $Id: XPathFilter2ParameterSpec.java,v 1.7 2005/05/13 18:45:42 mullan Exp $
 */
package javax.xml.crypto.dsig.spec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.crypto.dsig.Transform;

/**
 * Parameters for the W3C Recommendation
 * <a href="http://www.w3.org/TR/xmldsig-filter2/">
 * XPath Filter 2.0 Transform Algorithm</a>.
 * The parameters include a list of one or more {@link XPathType} objects.
 *
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see Transform
 * @see XPathFilterParameterSpec
 */
public final class XPathFilter2ParameterSpec implements TransformParameterSpec {

    private final List xPathList;

    /**
     * Creates an <code>XPathFilter2ParameterSpec</code>.
     *
     * @param xPathList a list of one or more {@link XPathType} objects. The 
     *    list is defensively copied to protect against subsequent modification.
     * @throws ClassCastException if <code>xPathList</code> contains any
     *    entries that are not of type {@link XPathType}
     * @throws IllegalArgumentException if <code>xPathList</code> is empty
     * @throws NullPointerException if <code>xPathList</code> is 
     *    <code>null</code>
     */
    public XPathFilter2ParameterSpec(List xPathList) {
	if (xPathList == null) {
	    throw new NullPointerException("xPathList cannot be null");
	}
        List xPathListCopy = new ArrayList(xPathList);
	if (xPathListCopy.isEmpty()) {
	    throw new IllegalArgumentException("xPathList cannot be empty");
	}
	int size = xPathListCopy.size();
        for (int i = 0; i < size; i++) {
            if (!(xPathListCopy.get(i) instanceof XPathType)) {
                throw new ClassCastException
                    ("xPathList["+i+"] is not a valid type");
            }
        }
	this.xPathList = Collections.unmodifiableList(xPathListCopy);
    }

    /**
     * Returns a list of one or more {@link XPathType} objects. 
     * <p>
     * This implementation returns an {@link Collections#unmodifiableList
     * unmodifiable list}.
     *
     * @return a <code>List</code> of <code>XPathType</code> objects
     *    (never <code>null</code> or empty)
     */
    public List getXPathList() {
	return xPathList;
    }
}

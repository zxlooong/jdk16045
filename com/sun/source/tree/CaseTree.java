/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * Use and Distribution is subject to the Java Research License available
 * at <http://wwws.sun.com/software/communitysource/jrl.html>.
 */

package com.sun.source.tree;

import java.util.List;

/**
 * A tree node for a 'case' in a 'switch' statement.
 *
 * For example:
 * <pre>
 *   case <em>expression</em> : 
 *       <em>statements</em>
 *
 *   default : 
 *       <em>statements</em>
 * </pre>
 *
 * @see "The Java Language Specification, 3rd ed, section 14.11"
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface CaseTree extends Tree {
    /**
     * @return null if and only if this Case is {@code default:}
     */
    ExpressionTree getExpression();
    List<? extends StatementTree> getStatements();
}

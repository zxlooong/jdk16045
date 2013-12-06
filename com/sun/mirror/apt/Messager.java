/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL.  Use is subject to license terms.
 */

package com.sun.mirror.apt;


import com.sun.mirror.util.SourcePosition;


/**
 * A <tt>Messager</tt> provides the way for
 * an annotation processor to report error messages, warnings, and
 * other notices.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @version %I% %E%
 * @since 1.5
 */

public interface Messager {

    /**
     * Prints an error message.
     * Equivalent to <tt>printError(null, msg)</tt>.
     * @param msg  the message, or an empty string if none
     */
    void printError(String msg);

    /**
     * Prints an error message.
     * @param pos  the position where the error occured, or null if it is
     *			unknown or not applicable
     * @param msg  the message, or an empty string if none
     */
    void printError(SourcePosition pos, String msg);

    /**
     * Prints a warning message.
     * Equivalent to <tt>printWarning(null, msg)</tt>.
     * @param msg  the message, or an empty string if none
     */
    void printWarning(String msg);

    /**
     * Prints a warning message.
     * @param pos  the position where the warning occured, or null if it is
     *			unknown or not applicable
     * @param msg  the message, or an empty string if none
     */
    void printWarning(SourcePosition pos, String msg);

    /**
     * Prints a notice.
     * Equivalent to <tt>printNotice(null, msg)</tt>.
     * @param msg  the message, or an empty string if none
     */
    void printNotice(String msg);

    /**
     * Prints a notice.
     * @param pos  the position where the noticed occured, or null if it is
     *			unknown or not applicable
     * @param msg  the message, or an empty string if none
     */
    void printNotice(SourcePosition pos, String msg);
}

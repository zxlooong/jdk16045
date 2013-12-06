/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.nio.charset;


/**
 * Checked exception thrown when an input byte sequence is not legal for given
 * charset, or an input character sequence is not a legal sixteen-bit Unicode
 * sequence.
 *
 * @since 1.4
 */

public class MalformedInputException
    extends CharacterCodingException
{

    private int inputLength;

    public MalformedInputException(int inputLength) {
	this.inputLength = inputLength;
    }

    public int getInputLength() {
	return inputLength;
    }

    public String getMessage() {
	return "Input length = " + inputLength;
    }

}

/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
 
package javax.security.auth.login;

import java.util.Map;
import java.util.Collections;

/**
 * This class represents a single <code>LoginModule</code> entry
 * configured for the application specified in the
 * <code>getAppConfigurationEntry(String appName)</code>
 * method in the <code>Configuration</code> class.  Each respective
 * <code>AppConfigurationEntry</code> contains a <code>LoginModule</code> name,
 * a control flag (specifying whether this <code>LoginModule</code> is
 * REQUIRED, REQUISITE, SUFFICIENT, or OPTIONAL), and LoginModule-specific
 * options.  Please refer to the <code>Configuration</code> class for
 * more information on the different control flags and their semantics.
 *
 * @version %I%, %G%
 * @see javax.security.auth.login.Configuration
 */
public class AppConfigurationEntry {

    private String loginModuleName;
    private LoginModuleControlFlag controlFlag;
    private Map options;

    /**
     * Default constructor for this class.
     *
     * <p> This entry represents a single <code>LoginModule</code>
     * entry configured for the application specified in the
     * <code>getAppConfigurationEntry(String appName)</code>
     * method from the <code>Configuration</code> class.
     *
     * @param loginModuleName String representing the class name of the
     *			<code>LoginModule</code> configured for the
     *			specified application. <p>
     *
     * @param controlFlag either REQUIRED, REQUISITE, SUFFICIENT,
     *			or OPTIONAL. <p>
     *
     * @param options the options configured for this <code>LoginModule</code>.
     *
     * @exception IllegalArgumentException if <code>loginModuleName</code>
     *			is null, if <code>LoginModuleName</code>
     *			has a length of 0, if <code>controlFlag</code>
     *			is not either REQUIRED, REQUISITE, SUFFICIENT
     *			or OPTIONAL, or if <code>options</code> is null.
     */
    public AppConfigurationEntry(String loginModuleName,
				LoginModuleControlFlag controlFlag,
				Map<String,?> options)
    {
	if (loginModuleName == null || loginModuleName.length() == 0 ||
	    (controlFlag != LoginModuleControlFlag.REQUIRED &&
		controlFlag != LoginModuleControlFlag.REQUISITE &&
		controlFlag != LoginModuleControlFlag.SUFFICIENT &&
		controlFlag != LoginModuleControlFlag.OPTIONAL) ||
	    options == null)
	    throw new IllegalArgumentException();
	    
	this.loginModuleName = loginModuleName;
	this.controlFlag = controlFlag;
	this.options = Collections.unmodifiableMap(options);
    }

    /**
     * Get the class name of the configured <code>LoginModule</code>.
     *
     * @return the class name of the configured <code>LoginModule</code> as
     *		a String.
     */
    public String getLoginModuleName() {
	return loginModuleName;
    }

    /**
     * Return the controlFlag
     * (either REQUIRED, REQUISITE, SUFFICIENT, or OPTIONAL)
     * for this <code>LoginModule</code>.
     *
     * @return the controlFlag
     *		(either REQUIRED, REQUISITE, SUFFICIENT, or OPTIONAL)
     *		for this <code>LoginModule</code>.
     */
    public LoginModuleControlFlag getControlFlag() {
	return controlFlag;
    }

    /**
     * Get the options configured for this <code>LoginModule</code>.
     *
     * @return the options configured for this <code>LoginModule</code>
     *		as an unmodifiable <code>Map</code>.
     */
    public Map<String,?> getOptions() {
	return options;
    }

    /**
     * This class represents whether or not a <code>LoginModule</code>
     * is REQUIRED, REQUISITE, SUFFICIENT or OPTIONAL.
     */
    public static class LoginModuleControlFlag {

	private String controlFlag;

	/**
 	 * Required <code>LoginModule</code>.
 	 */
	public static final LoginModuleControlFlag REQUIRED =
				new LoginModuleControlFlag("required");

	/**
	 * Requisite <code>LoginModule</code>.
	 */
	public static final LoginModuleControlFlag REQUISITE =
				new LoginModuleControlFlag("requisite");

	/**
	 * Sufficient <code>LoginModule</code>.
	 */
	public static final LoginModuleControlFlag SUFFICIENT =
				new LoginModuleControlFlag("sufficient");

	/**
	 * Optional <code>LoginModule</code>.
	 */
	public static final LoginModuleControlFlag OPTIONAL =
				new LoginModuleControlFlag("optional");

	private LoginModuleControlFlag(String controlFlag) {
	    this.controlFlag = controlFlag;
	}

	/**
	 * Return a String representation of this controlFlag.
	 *
	 * <p> The String has the format, "LoginModuleControlFlag: <i>flag</i>",
	 * where <i>flag</i> is either <i>required</i>, <i>requisite</i>,
	 * <i>sufficient</i>, or <i>optional</i>.
	 *
	 * @return a String representation of this controlFlag.
	 */
	public String toString() {
	    return (sun.security.util.ResourcesMgr.getString
		("LoginModuleControlFlag: ") + controlFlag);
	}
    }
}

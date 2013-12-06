/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.lang;

import java.io.*;

/* This class is for the exclusive use of ProcessBuilder.start() to
 * create new processes.
 *
 * @author Martin Buchholz
 * @version %I%, %E%
 * @since   1.5
 */

final class ProcessImpl extends Process {

    // System-dependent portion of ProcessBuilder.start()
    static Process start(String cmdarray[],
			 java.util.Map<String,String> environment,
			 String dir,
			 boolean redirectErrorStream)
	throws IOException
    {
	String envblock = ProcessEnvironment.toEnvironmentBlock(environment);
	return new ProcessImpl(cmdarray, envblock, dir, redirectErrorStream);
    }

    // We guarantee the only command file execution for implicit [cmd.exe] run.
    //    http://technet.microsoft.com/en-us/library/bb490954.aspx
    private static final char CMD_BAT_ESCAPE[] = {' ', '\t', '<', '>', '&', '|', '^'};
    private static final char WIN32_EXECUTABLE_ESCAPE[] = {' ', '\t', '<', '>'};  
    
    private static boolean isQuoted(boolean noQuotesInside, String arg, 
            String errorMessage) {
        int lastPos = arg.length() - 1;
        if (lastPos >=1 && arg.charAt(0) == '"' && arg.charAt(lastPos) == '"') {
            // The argument has already been quoted.
            if (noQuotesInside) {
                if (arg.indexOf('"', 1) != lastPos) {
                    // There is ["] inside.
                    throw new IllegalArgumentException(errorMessage);
                }
            }
            return true;
        }
        if (noQuotesInside) {
            if (arg.indexOf('"') >= 0) {
                // There is ["] inside.
                throw new IllegalArgumentException(errorMessage);                            
            }
        }
        return false;
    }
    
    private static boolean needsEscaping(boolean isCmdFile, String arg) {
        // Switch off MS heuristic for internal ["].
        // Please, use the explicit [cmd.exe] call  
        // if you need the internal ["].
        //    Example: "cmd.exe", "/C", "Extended_MS_Syntax"
        
        // For [.exe] or [.com] file the unpaired/internal ["] 
        // in the argument is not a problem.
        boolean argIsQuoted = isQuoted(isCmdFile, arg, 
            "Argument has embedded quote, use the explicit CMD.EXE call.");
        
        if (!argIsQuoted) {
            char testEscape[] = isCmdFile 
                    ? CMD_BAT_ESCAPE
                    : WIN32_EXECUTABLE_ESCAPE;
            for (int i = 0; i < testEscape.length; ++i) {
                if (arg.indexOf(testEscape[i]) >= 0) {
                    return true;        
                }
            }
        }    
        return false;        
    }    
    
    private static String getExecutablePath(String path) 
        throws IOException 
    {
        boolean pathIsQuoted = isQuoted(true, path, 
                "Executable name has embedded quote, split the arguments");

        // Win32 CreateProcess requires path to be normalized        
        File fileToRun = new File(pathIsQuoted
            ? path.substring(1, path.length() - 1)   
            : path);
        
        // From the [CreateProcess] function documentation:
        //
        // "If the file name does not contain an extension, .exe is appended. 
        // Therefore, if the file name extension is .com, this parameter 
        // must include the .com extension. If the file name ends in 
        // a period (.) with no extension, or if the file name contains a path, 
        // .exe is not appended." 
        //
        // "If the file name !does not contain a directory path!, 
        // the system searches for the executable file in the following 
        // sequence:..."
        //
        // In practice ANY non-existent path is extended by [.exe] extension 
        // in the [CreateProcess] funcion with the only exception:
        // the path ends by (.)
        
        return fileToRun.getPath();
    }

    private long handle = 0;
    private FileDescriptor stdin_fd;
    private FileDescriptor stdout_fd;
    private FileDescriptor stderr_fd;
    private OutputStream stdin_stream;
    private InputStream stdout_stream;
    private InputStream stderr_stream;

    private ProcessImpl(String cmd[],
			String envblock,
			String path,
			boolean redirectErrorStream)
	throws IOException
    {
        // The [executablePath] is not quoted for any case.        
        String executablePath = getExecutablePath(cmd[0]);
        
        // We need to extend the argument verification procedure 
        // to guarantee the only command file execution for implicit [cmd.exe]
        // run.
        String upPath = executablePath.toUpperCase();
        boolean isCmdFile = (upPath.endsWith(".CMD") || upPath.endsWith(".BAT"));
        
        StringBuilder cmdbuf = new StringBuilder(80);
        
        // Quotation protects from interpretation of the [path] argument as 
        // start of longer path with spaces. Quotation has no influence to 
        // [.exe] extension heuristic.
        cmdbuf.append('"');
        cmdbuf.append(executablePath);
        cmdbuf.append('"');
        
        for (int i = 1; i < cmd.length; i++) {
            cmdbuf.append(' ');
            String s = cmd[i];
            if (needsEscaping(isCmdFile, s)) {
                cmdbuf.append('"');
                cmdbuf.append(s);

                // The code protects the [java.exe] and console command line 
                // parser, that interprets the [\"] combination as an escape 
                // sequence for the ["] char. 
                //     http://msdn.microsoft.com/en-us/library/17w5ykft.aspx
                //
                // If the argument is an FS path, doubling of the tail [\] 
                // char is not a problem for non-console applications.
                //
                // The [\"] sequence is not an escape sequence for the [cmd.exe]
                // command line parser. The case of the [""] tail escape 
                // sequence could not be realized due to the argument validation 
                // procedure.
                if (!isCmdFile && s.endsWith("\\")) {
                    cmdbuf.append('\\');
                }
                cmdbuf.append('"');
            } else {
                cmdbuf.append(s);
            }
        }
	String cmdstr = cmdbuf.toString();

	stdin_fd  = new FileDescriptor();
	stdout_fd = new FileDescriptor();
	stderr_fd = new FileDescriptor();

	handle = create(cmdstr, envblock, path, redirectErrorStream,
			stdin_fd, stdout_fd, stderr_fd);

	java.security.AccessController.doPrivileged(
	    new java.security.PrivilegedAction() {
	    public Object run() {
		stdin_stream =
		    new BufferedOutputStream(new FileOutputStream(stdin_fd));
		stdout_stream =
		    new BufferedInputStream(new FileInputStream(stdout_fd));
		stderr_stream =
		    new FileInputStream(stderr_fd);
		return null;
	    }
	});
    }

    public OutputStream getOutputStream() {
	return stdin_stream;
    }

    public InputStream getInputStream() {
	return stdout_stream;
    }

    public InputStream getErrorStream() {
	return stderr_stream;
    }

    public void finalize() {
	close();
    }

    public native int exitValue();
    public native int waitFor();
    public native void destroy();

    private native long create(String cmdstr,
			       String envblock,
			       String dir,
			       boolean redirectErrorStream,
			       FileDescriptor in_fd,
			       FileDescriptor out_fd,
			       FileDescriptor err_fd)
	throws IOException;

    private native void close();
}

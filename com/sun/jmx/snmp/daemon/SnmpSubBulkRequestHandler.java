/*
 * %Z%file      %M%
 * %Z%author    Sun Microsystems, Inc.
 * %Z%version   %I%
 * %Z%date      %D%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */


package com.sun.jmx.snmp.daemon;



// java import
//
import java.util.Enumeration;
import java.util.Vector;

// jmx imports
//
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpVarBind;
import com.sun.jmx.snmp.SnmpOid;
import com.sun.jmx.snmp.SnmpValue;
import com.sun.jmx.snmp.SnmpDefinitions;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpEngine;
// SNMP Runtime import
//
import com.sun.jmx.snmp.agent.SnmpMibAgent;
import com.sun.jmx.snmp.agent.SnmpMibRequest;
import com.sun.jmx.snmp.ThreadContext;
import com.sun.jmx.snmp.daemon.SnmpAdaptorServer;
import com.sun.jmx.snmp.internal.SnmpIncomingRequest;
import com.sun.jmx.snmp.ThreadContext;

class SnmpSubBulkRequestHandler extends SnmpSubRequestHandler {
    private SnmpAdaptorServer server = null;

    /**
     * The constuctor initialize the subrequest with the whole varbind list contained
     * in the original request.
     */
    protected SnmpSubBulkRequestHandler(SnmpEngine engine,
					SnmpAdaptorServer server,
					SnmpIncomingRequest incRequest,
					SnmpMibAgent agent, 
					SnmpPdu req, 
					int nonRepeat,
                                        int maxRepeat, 
					int R) {
	super(engine, incRequest, agent, req);
	init(server, req, nonRepeat, maxRepeat, R);
    }
    
    /**
     * The constuctor initialize the subrequest with the whole varbind list contained
     * in the original request.
     */
    protected SnmpSubBulkRequestHandler(SnmpAdaptorServer server,
					SnmpMibAgent agent, 
					SnmpPdu req, 
					int nonRepeat,
                                        int maxRepeat, 
					int R) {
	super(agent, req);
	init(server, req, nonRepeat, maxRepeat, R);
    }
    
    public void run() {
    
        size= varBind.size();
    
        try {
            // Invoke a getBulk operation
            //
	    /* NPCTE fix for bugId 4492741, esc 0, 16-August-2001 */
	    final ThreadContext oldContext =
                ThreadContext.push("SnmpUserData",data);
	    try {
		if (isTraceOn()) {
                	trace("run", "[" + Thread.currentThread() + 
			"]:getBulk operation on " + agent.getMibName());
		}
		agent.getBulk(createMibRequest(varBind,version,data), 
			      nonRepeat, maxRepeat);
	    } finally {
                ThreadContext.restore(oldContext);
            }  
	    /* end of NPCTE fix for bugId 4492741 */
	    
        } catch(SnmpStatusException x) {
            errorStatus = x.getStatus() ;
            errorIndex=  x.getErrorIndex();
            if (isDebugOn()) {
                debug("run", "[" + Thread.currentThread() + 
		      "]:an Snmp error occured during the operation");
                debug("run", x);
            }
        }
        catch(Exception x) {
            errorStatus = SnmpDefinitions.snmpRspGenErr ;
            if (isDebugOn()) {
                debug("run", "[" + Thread.currentThread() + 
		      "]:a generic error occured during the operation");
                debug("run", x);
            }
        }
        if (isTraceOn()) {
            trace("run", "[" + Thread.currentThread() + 
		  "]:operation completed");
        }
    }
    
    private void init(SnmpAdaptorServer server,
		      SnmpPdu req,
		      int nonRepeat,
		      int maxRepeat, 
		      int R) {
	this.server = server;
        this.nonRepeat= nonRepeat;
        this.maxRepeat= maxRepeat;  
        this.globalR= R;
	
	final int max= translation.length;
        final SnmpVarBind[] list= req.varBindList;
        final NonSyncVector nonSyncVarBind = ((NonSyncVector)varBind);
        for(int i=0; i < max; i++) {
            translation[i]= i;
            // we need to allocate a new SnmpVarBind. Otherwise the first
            // sub request will modify the list...
            //
	    final SnmpVarBind newVarBind = 
		new SnmpVarBind(list[i].oid, list[i].value);
            nonSyncVarBind.addNonSyncElement(newVarBind);
        }
    }
    
    /**
     * The method updates find out which element to use at update time. Handle oid overlapping as well
     */
    private SnmpVarBind findVarBind(SnmpVarBind element, 
				    SnmpVarBind result) {
	
	if (element == null) return null;

	if (result.oid == null) {
	     return element;
	}

	if (element.value == SnmpVarBind.endOfMibView) return result;

	if (result.value == SnmpVarBind.endOfMibView) return element;

	final SnmpValue val = result.value;

	int comp = element.oid.compareTo(result.oid);
	if(isDebugOn()) {
	    trace("findVarBind","Comparing OID element : " + element.oid +
		  " with result : " + result.oid);
	    trace("findVarBind","Values element : " + element.value +
		  " result : " + result.value);
	}
	if (comp < 0) {
	    // Take the smallest (lexicographically)
	    //
	    return element;
	}
	else {
	    if(comp == 0) {
		// Must compare agent used for reply
		// Take the deeper within the reply
		if(isDebugOn()) {
		    trace("findVarBind"," oid overlapping. Oid : " + 
			  element.oid + "value :" + element.value);
		    trace("findVarBind","Already present varBind : " + 
			  result);
		}
		SnmpOid oid = result.oid;
		SnmpMibAgent deeperAgent = server.getAgentMib(oid);

		if(isDebugOn())
		    trace("findVarBind","Deeper agent : " + deeperAgent);
		if(deeperAgent == agent) {
		    if(isDebugOn())
			trace("updateResult","The current agent is the deeper one. Update the value with the current one");
		    return element;
		} else {
		    if(isDebugOn())
			trace("updateResult","Current is not the deeper, return the previous one.");
		    return result;
		}
		    
		/*
		   Vector v = new Vector();
		   SnmpMibRequest getReq = createMibRequest(v,
		   version,
		   null);
		   SnmpVarBind realValue = new SnmpVarBind(oid);
		   getReq.addVarBind(realValue);
		   try {
		   deeperAgent.get(getReq);
		   } catch(SnmpStatusException e) {
		   e.printStackTrace();
		   }
		   
		   if(isDebugOn())
		   trace("findVarBind", "Biggest priority value is : " +  
		   realValue.value);
		   
		   return realValue;
		*/
		
	    }
	    else {
		if(isDebugOn())
		    trace("findVarBind",
			  "The right varBind is the already present one");
		return result;
	    }
	}
    }
    /**
     * The method updates a given var bind list with the result of a 
     * previsouly invoked operation.
     * Prior to calling the method, one must make sure that the operation was
     * successful. As such the method getErrorIndex or getErrorStatus should be
     * called.
     */
    protected void updateResult(SnmpVarBind[] result) {
	// we can assume that the run method is over ...
        // 

        final Enumeration e= varBind.elements();
        final int max= result.length;

        // First go through all the values once ...
        for(int i=0; i < size; i++) {
            // May be we should control the position ...
            //
            if (e.hasMoreElements() == false)
                return;

	    // bugId 4641694: must check position in order to avoid 
	    //       ArrayIndexOutOfBoundException
	    final int pos=translation[i];
	    if (pos >= max) {
		debug("updateResult","Position `"+pos+"' is out of bound...");
		continue;
	    }

	    final SnmpVarBind element= (SnmpVarBind) e.nextElement();
	    
	    if (element == null) continue;
	    if (isDebugOn())
		trace("updateResult", "Non repeaters Current element : " + 
		      element + " from agent : " + agent);
	    final SnmpVarBind res = findVarBind(element,result[pos]);
	    
	    if(res == null) continue;
	    
	    result[pos] = res;
	}
 
        // Now update the values which have been repeated
        // more than once.
        int localR= size - nonRepeat;
        for (int i = 2 ; i <= maxRepeat ; i++) {
            for (int r = 0 ; r < localR ; r++) {
                final int pos = (i-1)* globalR + translation[nonRepeat + r] ;
                if (pos >= max)
                    return;
                if (e.hasMoreElements() ==false)
                    return;
                final SnmpVarBind element= (SnmpVarBind) e.nextElement();
		
		if (element == null) continue;
		if (isDebugOn())
		    trace("updateResult", "Repeaters Current element : " + 
			  element + " from agent : " + agent);
		final SnmpVarBind res = findVarBind(element, result[pos]);
		
		if(res == null) continue;
	    
		result[pos] = res;
            }
        }
    }
  
    protected String makeDebugTag() {
        return "SnmpSubBulkRequestHandler";
    }
    
    // PROTECTED VARIABLES
    //------------------

    /**
     * Specific to the sub request
     */
    protected int nonRepeat=0;
  
    protected int maxRepeat=0;
  
    /**
     * R as defined in RCF 1902 for the global request the sub-request is associated to.
     */
    protected int globalR=0;

    protected int size=0;
}

/*
 * %W% %E%
 *
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.sql;

/**
 * <P>A thin wrapper around a millisecond value that allows
 * JDBC to identify this as an SQL <code>DATE</code> value.  A 
 * milliseconds value represents the number of milliseconds that 
 * have passed since January 1, 1970 00:00:00.000 GMT.
 * <p>
 * To conform with the definition of SQL <code>DATE</code>, the 
 * millisecond values wrapped by a <code>java.sql.Date</code> instance 
 * must be 'normalized' by setting the 
 * hours, minutes, seconds, and milliseconds to zero in the particular
 * time zone with which the instance is associated.
 */
public class Date extends java.util.Date {

    /**
     * Constructs a <code>Date</code> object initialized with the given
     * year, month, and day.
     * <P>
     * The result is undefined if a given argument is out of bounds.
     *
     * @param year the year minus 1900; must be 0 to 8099. (Note that
     *        8099 is 9999 minus 1900.)
     * @param month 0 to 11
     * @param day 1 to 31
     * @deprecated instead use the constructor <code>Date(long date)</code>
     */
    public Date(int year, int month, int day) {
	super(year, month, day);
    }

    /**
     * Constructs a <code>Date</code> object using the given milliseconds 
     * time value.  If the given milliseconds value contains time 
     * information, the driver will set the time components to the
     * time in the default time zone (the time zone of the Java virtual
     * machine running the application) that corresponds to zero GMT.
     *
     * @param date milliseconds since January 1, 1970, 00:00:00 GMT not
     *        to exceed the milliseconds representation for the year 8099.
     *        A negative number indicates the number of milliseconds
     *        before January 1, 1970, 00:00:00 GMT.
     */
    public Date(long date) {
	// If the millisecond date value contains time info, mask it out.
	super(date);
	
    }

    /**
     * Sets an existing <code>Date</code> object 
     * using the given milliseconds time value. 
     * If the given milliseconds value contains time information, 
     * the driver will set the time components to the
     * time in the default time zone (the time zone of the Java virtual
     * machine running the application) that corresponds to zero GMT.
     *
     * @param date milliseconds since January 1, 1970, 00:00:00 GMT not
     *        to exceed the milliseconds representation for the year 8099.
     *        A negative number indicates the number of milliseconds
     *        before January 1, 1970, 00:00:00 GMT.
     */
    public void setTime(long date) {
	// If the millisecond date value contains time info, mask it out.
	super.setTime(date);	 
    }

    /**
     * Converts a string in JDBC date escape format to
     * a <code>Date</code> value.
     *
     * @param s a <code>String</code> object representing a date in 
     *        in the format "yyyy-mm-dd"
     * @return a <code>java.sql.Date</code> object representing the
     *         given date
     * @throws IllegalArgumentException if the date given is not in the
     *         JDBC date escape format (yyyy-mm-dd)
     */
    public static Date valueOf(String s) {
        final int YEAR_LENGTH = 4;
        final int MONTH_LENGTH = 2;
        final int DAY_LENGTH = 2;
        final int MAX_MONTH = 12;
        final int MAX_DAY = 31;
        int firstDash;
        int secondDash;
        Date d = null;

        if (s == null) {
            throw new java.lang.IllegalArgumentException();
        }

        firstDash = s.indexOf('-');
        secondDash = s.indexOf('-', firstDash + 1);
        if ((firstDash > 0) && (secondDash > 0) && (secondDash < s.length()-1)) {
            String yyyy = s.substring(0, firstDash);
            String mm = s.substring(firstDash + 1, secondDash);
            String dd = s.substring(secondDash + 1);
            if (yyyy.length() == YEAR_LENGTH && mm.length() == MONTH_LENGTH &&
                dd.length() == DAY_LENGTH) {
                int year = Integer.parseInt(yyyy);
                int month = Integer.parseInt(mm);
                int day = Integer.parseInt(dd);
                if (month >= 1 && month <= MAX_MONTH) {
                    int maxDays = MAX_DAY;
                    switch (month) {
                        // February determine if a leap year or not
                        case 2:
                            if((year % 4 == 0 && !(year % 100 == 0)) || (year % 400 == 0)) {
                                maxDays = MAX_DAY-2; // leap year so 29 days in February
                            } else {
                                maxDays = MAX_DAY-3; //  not a leap year so 28 days in February 
                            }
                            break;
                        // April, June, Sept, Nov 30 day months
                        case 4:
                        case 6:
                        case 9:
                        case 11:
                            maxDays = MAX_DAY-1;
                            break;
                    }
                    if (day >= 1 && day <= maxDays) {
                        d = new Date(year - 1900, month - 1, day);
                    }
                }
            }
        }
        if (d == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return d;
    }

    /**
     * Formats a date in the date escape format yyyy-mm-dd.  
     * <P>
     * @return a String in yyyy-mm-dd format
     */
    public String toString () {
	int year = super.getYear() + 1900;
	int month = super.getMonth() + 1;
	int day = super.getDate();

        char buf[] = "2000-00-00".toCharArray();
        buf[0] = Character.forDigit(year/1000,10);
        buf[1] = Character.forDigit((year/100)%10,10);
        buf[2] = Character.forDigit((year/10)%10,10);
        buf[3] = Character.forDigit(year%10,10);
        buf[5] = Character.forDigit(month/10,10);
        buf[6] = Character.forDigit(month%10,10);
        buf[8] = Character.forDigit(day/10,10);
        buf[9] = Character.forDigit(day%10,10);
		
	return new String(buf);
    }

    // Override all the time operations inherited from java.util.Date;

   /**
    * This method is deprecated and should not be used because SQL Date 
    * values do not have a time component.
    *
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #setHours
    */
    public int getHours() {
	throw new java.lang.IllegalArgumentException();
    }

   /**
    * This method is deprecated and should not be used because SQL Date 
    * values do not have a time component.
    *
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #setMinutes
    */
    public int getMinutes() {
	throw new java.lang.IllegalArgumentException();
    }
    
   /**
    * This method is deprecated and should not be used because SQL Date 
    * values do not have a time component.
    *
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #setSeconds
    */
    public int getSeconds() {
	throw new java.lang.IllegalArgumentException();
    }

   /**
    * This method is deprecated and should not be used because SQL Date 
    * values do not have a time component.
    *
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #getHours
    */
    public void setHours(int i) {
	throw new java.lang.IllegalArgumentException();
    }

   /**
    * This method is deprecated and should not be used because SQL Date 
    * values do not have a time component.
    *
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #getMinutes
    */
    public void setMinutes(int i) {
	throw new java.lang.IllegalArgumentException();
    }

   /**
    * This method is deprecated and should not be used because SQL Date 
    * values do not have a time component.
    *
    * @deprecated
    * @exception java.lang.IllegalArgumentException if this method is invoked
    * @see #getSeconds
    */
    public void setSeconds(int i) {
	throw new java.lang.IllegalArgumentException();
    }

   /**
    * Private serial version unique ID to ensure serialization
    * compatibility.
    */ 
    static final long serialVersionUID = 1511598038487230103L;
}


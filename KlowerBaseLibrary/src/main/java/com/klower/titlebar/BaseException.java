/**
 * All rights reserved
 */

package com.klower.titlebar;

public class BaseException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BaseException() {
        super();
    }

    public BaseException(String zMessage) {
        super(zMessage);

    }

    public BaseException(Throwable oThrowable) {
        super(oThrowable);

    }

    public BaseException(String zMessage, Throwable oThrowable) {
        super(zMessage, oThrowable);

    }

    public BaseException(String zMessage, int nErrCode, int nReqType) {
        super(zMessage);

    }
}

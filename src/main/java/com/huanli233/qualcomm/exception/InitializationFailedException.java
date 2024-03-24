package com.huanli233.qualcomm.exception;

public class InitializationFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InitializationFailedException() {
        super("Initialization failed");
    }

    public InitializationFailedException(String message) {
        super(message);
    }

    public InitializationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}

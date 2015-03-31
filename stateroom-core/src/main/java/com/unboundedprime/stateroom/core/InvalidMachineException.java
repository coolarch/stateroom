package com.unboundedprime.stateroom.core;

public class InvalidMachineException extends Exception {

	public InvalidMachineException() {
		super();
	}

	public InvalidMachineException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMachineException(String message) {
		super(message);
	}

	public InvalidMachineException(Throwable cause) {
		super(cause);
	}
}

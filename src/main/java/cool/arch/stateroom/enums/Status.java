package cool.arch.stateroom.enums;

public enum Status {

		/**
		 * Indicates that the machine context is ready for evaluation.
		 */
		READY,
		/**
		 * Indicates that the machine context has finished and accepted.
		 */
		ACCEPTED,
		/**
		 * Indicates that machine has context crashed.
		 */
		CRASHED
}

package it.rdev.blog.api.exception;

public class NotTheAuthorException extends RuntimeException {

	private static final long serialVersionUID = 1119980923472681095L;

	public NotTheAuthorException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public NotTheAuthorException(String message) {
		super(message);
	}
}

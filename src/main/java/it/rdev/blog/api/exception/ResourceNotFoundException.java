package it.rdev.blog.api.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1119980923472681095L;

	public ResourceNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}

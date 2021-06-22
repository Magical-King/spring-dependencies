package org.springframework.safe.core.excep;


import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;

/**
 * 自定义安全异常
 * @author Sir.D
 */
public class SpringSafeException extends Exception {
	private static final long serialVersionUID = -438675789539285672L;

	private Message message;

	public SpringSafeException(int code ) {
		this.message = new Message( code );
	}

	public SpringSafeException(Status s ) {
		this.message = new Message( s );
	}

	public SpringSafeException(String message ) {
		this.message = new Message( Status.ACTION_INVALID, message );
	}

	public SpringSafeException(Status s, String message ) {
		this.message = new Message( s, message );
	}

	public SpringSafeException(Status s, Object data ) {
		this.message = new Message();
		this.message.setData( data );
		this.message.setCode( s.code() );
		this.message.setSuccess( false );
	}

	public Message get() {
		return this.message;
	}
	
}

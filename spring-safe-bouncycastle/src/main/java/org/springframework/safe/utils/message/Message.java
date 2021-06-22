package org.springframework.safe.utils.message;

import lombok.Data;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Sir.D
 */
@Data
public class Message {

	public Message() {
		
	}

	public Message( int code ) {
		this.code = code;
		this.success = ( Status.SUCCESS.code() == code );
	}
	
	public Message( Status s ) {
		this( s.code() );
	}
	
	public Message( Object data ) {
		this( Status.SUCCESS );
		this.data = data;
	}
	
	public Message( Status s, String message ) {
		this( s );
		this.message = message;
	}
	
	public <T> Message( long total, Collection<T> list ) {
		this( Status.SUCCESS );
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		tmp.put( "total", total );
		tmp.put( "rows", list );
		this.data = tmp;
	}

	/**
	 * 状态码
	 */
	private int code;
	/**
	 * 数据
	 */
	private Object data;
	/**
	 * 消息
	 */
	private String message;
	/**
	 * 是否执行成功
	 */
	private boolean success;

}

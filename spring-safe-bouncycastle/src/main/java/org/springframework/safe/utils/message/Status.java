package org.springframework.safe.utils.message;

/**
 * 消息状态
 * @author Sir.D
 *
 */
public enum Status {
	// 操作成功
	SUCCESS 		( 10000 ),
	// 请求已过期
	EXPIRED			( 10001 ),
	// 校验码错误
	TOKEN_ERROR		( 10002 ),
	// 数据签名无效
	SIGNATURE_ERROR	( 10003 ),
	// 系统发生数据错误或运行时异常
	FAILED			( 10004 ),
	// 操作无效
	ACTION_INVALID	( 10005 ),
	// 无效的参数
	PARAM_INVALID	( 10006 ),
	// 超出系统限制
	LIMITED 		( 10007 ),
	// 禁止操作
	FORBIDDEN		( 10008 ),
	// 数据已存在
	EXISTS			( 10009 ),
	// 数据不存在
	UNEXISTS		( 10010 ),
	// 无权限
	NO_AUTHENTICATION		( 10011 ),
	;

	/**
	 * code
	 */
	private final int code;
	public int code(){ return code; }
	Status( int code ) { this.code = code; }
	
	public static Status fromCode( int code ) {
		for ( Status source : values() ) {
			if ( source.code == code ) {
				return source;
			}
		}
		return null;
	}
	
}

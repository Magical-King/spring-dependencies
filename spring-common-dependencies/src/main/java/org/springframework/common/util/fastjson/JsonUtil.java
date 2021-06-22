package org.springframework.common.util.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Sir.D
 */
public class JsonUtil {
	
	/**
	 * 格式输出JSON字符串
	 */
	public static final SerializerFeature[] FEATURES = {
		// 禁用引用
		SerializerFeature.DisableCircularReferenceDetect,

		// 使用引号, 默认
//		SerializerFeature.QuoteFieldNames,

		// 非实体字段忽略, 默认
//		SerializerFeature.SkipTransientField,

		// 枚举类型字段转换成字符串
		SerializerFeature.WriteEnumUsingToString,

		// 输出空值字段
		SerializerFeature.WriteMapNullValue,

		// list字段如果为null，输出为[]，而不是null
		SerializerFeature.WriteNullListAsEmpty,

		// 数值字段如果为null，输出为0，而不是null
//		SerializerFeature.WriteNullNumberAsZero,

		// Boolean字段如果为null，输出为false，而不是null
		SerializerFeature.WriteNullBooleanAsFalse,

		// 字符类型字段如果为null，输出为""，而不是null
		SerializerFeature.WriteNullStringAsEmpty,

		SerializerFeature.WriteDateUseDateFormat
	};
	
	/**
	 * e.g.[{...}]
	 * @param obj			被转换成JSON字符串的对象
	 * @param clazz			转换过程中需要特殊处理的类, 若为空, 表示obj的Clazz
	 * @param exclude		处理类型 ( true: 表示从clazz中排除properties, false: 表示从clazz中保留properties ) 
	 * @param properties	被处理的属性
	 * @return
	 */
	public static String toJson( Object obj, Class<?> clazz, boolean exclude, String... properties ){
		
		return JSON.toJSONString( obj, 
			/**
			 * 过滤序列化的字段
			 * @param clazz		// 需要过滤的类
			 * @param exclude	// true表示properties是需要被过滤的字段，false表示properties是需要序列化的字段
			 * @param properties// 字段名称数组
			 * @return
			 */
			new PropertyFilter( clazz, exclude, properties ), FEATURES );
	}
	
	/**
	 * 普通json格式
	 * e.g.[{...}]
	 * @return
	 */
	public static String toJson( Object obj ){
		return JSON.toJSONString( obj, FEATURES );
	}

}

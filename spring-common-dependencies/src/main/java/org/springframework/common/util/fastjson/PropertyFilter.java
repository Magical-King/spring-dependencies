package org.springframework.common.util.fastjson;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sir.D
 */
public class PropertyFilter implements com.alibaba.fastjson.serializer.PropertyFilter {

    private final Class<?>    clazz;
    private final Set<String> includes = new HashSet<String>();
    private final Set<String> excludes = new HashSet<String>();

    public PropertyFilter(Class<?> clazz, String... properties){
        this(clazz, false, properties);
    }
    
    public PropertyFilter(Class<?> clazz, boolean exclude, String... properties){
        super();
        this.clazz = clazz;
        
        Set<String> tmp = null;
        if(null != properties){
        	tmp = new HashSet<String>();
            Collections.addAll(tmp, properties);
        }
        
        if(null != tmp){
	        if(exclude){
	        	excludes.addAll(tmp);
	        } else {
	        	includes.addAll(tmp);
	        }
        }
    }

	@Override
	public boolean apply(Object source, String name, Object value) {
		if (source == null) {
			return true;
		}
		
        if (clazz != null && !clazz.isInstance(source)) {
        	return true;
        }
        
        if (this.excludes.contains(name)) {
        	return false;
        }
        
        if(value instanceof HibernateProxy){
        	LazyInitializer lazy = ((HibernateProxy) value).getHibernateLazyInitializer();
        	if(lazy.isUninitialized()){
        		return false;
        	}
        }
        
        if (includes.size() == 0 || includes.contains(name)) {
        	return true;
        }
        
        return false;
	}
}

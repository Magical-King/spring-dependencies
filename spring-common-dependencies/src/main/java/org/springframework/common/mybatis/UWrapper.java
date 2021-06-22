package org.springframework.common.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * @author Sir.D
 */
public class UWrapper<T> extends UpdateWrapper<T> {

    @Override
    protected String columnToString(String column) {
        return StringUtils.camelToUnderline(column);
    }

    @Override
    public UpdateWrapper<T> eq(boolean condition, String column, Object val) {
        if (null == val) {
            condition = false;
        }
        return super.eq(condition, column, val);
    }

    @Override
    public UpdateWrapper<T> like(boolean condition, String column, Object val) {
        if (null == val) {
            condition = false;
        }
        return super.like(condition, column, val);
    }

    @Override
    public UpdateWrapper<T> set(boolean condition, String column, Object val) {
        return super.set(condition, StringUtils.camelToUnderline(column), val);
    }
}

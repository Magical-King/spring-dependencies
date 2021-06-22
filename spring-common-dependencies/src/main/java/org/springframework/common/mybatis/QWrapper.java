package org.springframework.common.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Sir.D
 */
public class QWrapper<T> extends QueryWrapper<T> {

    @Override
    protected String columnToString(String column) {
        return StringUtils.camelToUnderline(column);
    }

    @Override
    public QueryWrapper<T> eq(boolean condition, String column, Object val) {
        if (null == val) {
            condition = false;
        }

        return super.eq(condition, columnToString(column), val);
    }

    @Override
    public QueryWrapper<T> like(boolean condition, String column, Object val) {
        if (null == val ) {
            condition = false;
        }
        return super.like(condition, columnToString(column), val);
    }

    @Override
    public QueryWrapper<T> select(String... columns) {
        String[] c = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            c[i] = columnToString(columns[i]);
        }

        return super.select(c);
    }
}

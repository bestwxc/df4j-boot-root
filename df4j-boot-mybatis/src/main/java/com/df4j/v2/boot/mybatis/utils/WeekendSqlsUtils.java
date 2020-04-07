package com.df4j.v2.boot.mybatis.utils;


import com.df4j.v2.base.form.BoundType;
import com.df4j.v2.base.form.Field;
import com.df4j.v2.base.form.RangeType;
import com.df4j.v2.base.util.ValidatorUtils;
import tk.mybatis.mapper.weekend.Fn;
import tk.mybatis.mapper.weekend.WeekendSqls;
import static com.df4j.v2.base.form.BoundType.*;
import static com.df4j.v2.base.form.BoundType.INCLUDE;
import static com.df4j.v2.base.form.RangeType.*;
import static com.df4j.v2.base.form.RangeType.BETWEEN;
import static com.df4j.v2.base.form.RangeType.EQUAL;
import static com.df4j.v2.base.form.RangeType.EQUAL_NULL;
import static com.df4j.v2.base.form.RangeType.GREATER;
import static com.df4j.v2.base.form.RangeType.LESS;
import static com.df4j.v2.base.form.RangeType.NOT_BETWEEN;

/**
 * 拼接WeekendSqls的工具类
 */
public class WeekendSqlsUtils<T> {
    public WeekendSqls<T> appendSql(WeekendSqls<T> sqls, Fn<T, Object> fn, Object value) {
        // 如果值为空，不拼接
        if (ValidatorUtils.isNull(value)) {
            return sqls;
        }
        // 不是range子类
        if (!(value instanceof Field)) {
            return sqls.andEqualTo(fn, value);
        }

        Field field = (Field) value;
        RangeType rangeType = field.getRangeType();
        BoundType fromBoundType = field.getFromBoundType();
        // 等值范围
        if (rangeType == EQUAL) {
            return sqls.andEqualTo(fn, field.getFromValue());
        }
        // 大于范围
        if (rangeType == GREATER) {
            if (fromBoundType == INCLUDE) {
                sqls.andGreaterThanOrEqualTo(fn, field.getFromValue());
            } else {
                sqls.andGreaterThan(fn, field.getFromValue());
            }
        }
        // 小于范围
        if (rangeType == LESS) {
            if (fromBoundType == INCLUDE) {
                sqls.andLessThanOrEqualTo(fn, field.getFromValue());
            } else {
                sqls.andLessThan(fn, field.getFromValue());
            }
        }

        BoundType toBoundType = field.getToBoundType();
        // between
        if (rangeType == BETWEEN) {
            if (fromBoundType == INCLUDE) {
                sqls.andGreaterThanOrEqualTo(fn, field.getFromValue());
            } else {
                sqls.andGreaterThan(fn, field.getFromValue());
            }
            if (toBoundType == INCLUDE) {
                sqls.andLessThanOrEqualTo(fn, field.getToValue());
            } else {
                sqls.andLessThan(fn, field.getToValue());
            }
            return sqls;
        }
        // not between
        if (rangeType == NOT_BETWEEN) {
            if (fromBoundType == INCLUDE) {
                sqls.andLessThanOrEqualTo(fn, field.getFromValue());
            } else {
                sqls.andLessThan(fn, field.getFromValue());
            }
            if (toBoundType == INCLUDE) {
                sqls.andGreaterThanOrEqualTo(fn, field.getToValue());
            } else {
                sqls.andGreaterThan(fn, field.getToValue());
            }
            return sqls;
        }
        // 等于null
        if (rangeType == EQUAL_NULL) {
            return sqls.andEqualTo(fn, null);
        }
        return sqls;
    }
}

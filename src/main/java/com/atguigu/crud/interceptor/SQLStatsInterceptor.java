package com.atguigu.crud.interceptor;

import java.sql.Connection;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;

/**
 * @Auther: Albert
 * @Date: 2019/7/9 21:41
 * @Description: 把Mybatis所有执行的sql都记录下来。
 */
@Slf4j
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class}) })
public class SQLStatsInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        log.info("mybatis intercept sql:{}", sql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     *
     * 功能描述: 项目启动的时候才会执行此方法,
     *	<plugin interceptor="com.atguigu.crud.interceptor.SQLStatsInterceptor">
            <property name="dialect" value="mysql22test" />
         </plugin>
     * @param:
     * @return:
     * @auther: Albert
     * @date: 2019/7/9 21:56
     */
    @Override
    public void setProperties(Properties properties) {
        // <property name="dialect" value="mysql22test" /> 获取里面的值
        String dialect = properties.getProperty("dialect");
        log.info("mybatis intercept dialect:{}", dialect);
    }
}

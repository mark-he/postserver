package com.eagletsoft.post.core.convert.impl;

import com.eagletsoft.post.core.convert.IConvertor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlConvertor implements IConvertor {

    private String name = "MYSQL_CONVERTOR";
    private DataSource dataSource;
    private String queryUrl;

    @Override
    public void init(Map<String, Object> props) {
        this.name = (String)props.get("name");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName((String)props.get("driver"));
        dataSourceBuilder.url((String)props.get("url"));
        dataSourceBuilder.username((String)props.get("username"));
        dataSourceBuilder.password((String)props.get("password"));
        HikariDataSource hikariDataSource  = (HikariDataSource) dataSourceBuilder.type(HikariDataSource.class).build();
        hikariDataSource.setMaximumPoolSize(props.get("maxPoolSize")==null?5:(Integer) props.get("maxPoolSize"));
        this.dataSource = hikariDataSource;
        this.queryUrl = (String)props.get("sql");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<String> convert(String userId) {
        List<String> deviceIds = new ArrayList<>();
        if (null != this.dataSource) {
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
            Map params = new HashMap<>();
            String[] userIds = userId.split(",");
            params.put("userIds", Arrays.asList(userIds));
            List<Map<String, Object>> list = jdbcTemplate.queryForList(this.queryUrl, params);
            for (Map<String, Object> map : list) {
                if (null != map.get("device_id")) {
                    if (null != map.get("device_id") && !StringUtils.isEmpty(String.valueOf(map.get("device_id")))) {
                        deviceIds.add(String.valueOf(map.get("device_id")));
                    }
                }
            }
        }
        return deviceIds;
    }
}

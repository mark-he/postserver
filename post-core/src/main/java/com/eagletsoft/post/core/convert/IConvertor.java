package com.eagletsoft.post.core.convert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public interface IConvertor {

    void init(Map<String, Object> props);
    String getName();
    List<String> convert(String userId);
}

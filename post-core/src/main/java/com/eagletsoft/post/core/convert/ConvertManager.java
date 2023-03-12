package com.eagletsoft.post.core.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertManager {

    private static Logger LOG = LoggerFactory.getLogger(ConvertManager.class);
    private Map<String, IConvertor> convertorMap = new HashMap<>();

    public IConvertor findConvertor(String convertorName) {
        IConvertor convertor = convertorMap.get(convertorName);
        return convertor;
    }

    public void init(List<Map<String, Object>> convertorConfigs) throws Exception {
        LOG.warn("Started to load convertors");
        loadConvertors(convertorConfigs);
    }

    public void loadConvertors(List<Map<String, Object>> convertorConfigs ) {

        for (Map<String, Object> source : convertorConfigs) {
            IConvertor convertor = null;
            try {
                convertor = (IConvertor) Class.forName((String)source.get("className")).newInstance();

                convertor.init(source);
                convertorMap.put(convertor.getName(), convertor);

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}

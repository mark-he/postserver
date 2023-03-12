package com.eagletsoft.post.core.convert;

import com.eagletsoft.boot.framework.common.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class ConvertConfig {

    private static Logger LOG = LoggerFactory.getLogger(ConvertConfig.class);


    public static List<Map<String, Object>> loadConfig(ClassPathResource resource) throws Exception {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            String str = new String(stringBuilder.toString().getBytes("UTF-8"));
            List<Map<String, Object>> config = JsonUtils.createMapper().readValue(str, new TypeReference<List<Map<String, Object>>>(){});


            System.out.println("ConvertConfig ===" + JsonUtils.writeValue(config));
            return config;
        } catch (Exception e) {
            LOG.error("Error in reading convert configuration!");
            throw e;
        }
    }
}

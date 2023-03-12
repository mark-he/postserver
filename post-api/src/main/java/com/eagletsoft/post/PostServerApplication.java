package com.eagletsoft.post;

import com.eagletsoft.boot.framework.cache.CacheAdvise;
import com.eagletsoft.boot.framework.cache.ICache;
import com.eagletsoft.boot.framework.cache.local.LocalCache;
import com.eagletsoft.boot.framework.common.session.UserSession;
import com.eagletsoft.boot.framework.common.utils.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.*;


@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@ImportResource("classpath:spring/applicationContext*.xml")
@ComponentScan({"com.eagletsoft"})
@PropertySource(value="classpath:config/application.properties",encoding = "UTF-8", ignoreResourceNotFound = true)
public class PostServerApplication extends SpringBootServletInitializer {

    private static Logger LOG = LoggerFactory.getLogger(PostServerApplication.class);
    public static void main(String[] args) {
        ApplicationUtils.CONTEXT = SpringApplication.run(PostServerApplication.class, args);
    }

    @Bean
    @Primary
    public ICache localCache() {
        LocalCache cache = new LocalCache("/ehcache/ehcache.xml") {
            @Override
            public String makeCacheId(String sector, String key, Object query) {
                if (null != UserSession.getAuthorize()) {
                    sector = UserSession.getAuthorize().getToken() + "/" + sector;
                }
                return super.makeCacheId(sector, key, query);
            }
        };
        return cache;
    }

    @Bean
    public CacheAdvise cacheAdvise(ICache cache) {
        CacheAdvise advise = new CacheAdvise();
        advise.setCache(cache);
        return advise;
    }

}

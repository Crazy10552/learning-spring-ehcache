package com.tianyang.learning.spring.ehcache;

import com.alibaba.fastjson.JSON;
import com.tianyang.learning.spring.ehcache.constants.Constants;
import com.tianyang.learning.spring.ehcache.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yttiany
 * @author yttiany
 * @Description:
 * @ProjectName: self-working-space
 * @Package: com.tianyang.learning.spring.ehcache
 * @Date Create on 2019/7/25.12:43
 * -----------------------------------------------------------.
 * @Modify - - - - - - - - - - - - - - - - - -
 * @ModTime 2019/7/25.12:43
 * @ModDesc:
 * @Modify - - - - - - - - - - - - - - - - - -
 * -----------------------------------------------------------
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringEhcacheApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SpringBootJunitTest implements Constants {

    private static Long hosId=10086l;
    private static String URL_APPEND="/";
    private static String CACHE_NAME_APPEND="_";
    private static final String URL_HEAD="http://127.0.0.1:8222/learning-spring-ehcache";

    @Autowired
    EhCacheCacheManager ehCacheCacheManager;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void getCacheTest(){
        String url = URL_APPEND+hosId+URL_APPEND+URL_SUFFIX_CREATE_TEST_CACHE_SUCCESS_BUT_NO_CACHE;
        String cacheKey = hosId+CACHE_NAME_APPEND+URL_SUFFIX_CREATE_TEST_CACHE_SUCCESS_BUT_NO_CACHE;
        ResultVo resultVo = restTemplate.getForObject(url,ResultVo.class);
        log.debug("url=["+url+"]\t result="+JSON.toJSONString(resultVo));

        url = URL_APPEND+URL_SUFFIX_GET_RESULT;
        resultVo = restTemplate.getForObject(url,ResultVo.class,CACHE_NEMA_TESTCACHE,cacheKey);
        log.debug("cacheName=["+CACHE_NAME_APPEND+"]  cacheKey=["+cacheKey+"] \t result="+ JSON.toJSONString(resultVo));

        url = URL_APPEND+hosId+URL_APPEND+URL_SUFFIX_CREATE_TEST_CACHE_SUCCESS;
        cacheKey = hosId+CACHE_NAME_APPEND+URL_SUFFIX_CREATE_TEST_CACHE_SUCCESS;
        resultVo = restTemplate.getForObject(url,ResultVo.class);
        log.debug("url=["+url+"]\t result="+JSON.toJSONString(resultVo));

        url = URL_APPEND+URL_SUFFIX_GET_RESULT;
        resultVo = restTemplate.getForObject(url,ResultVo.class,CACHE_NEMA_TESTCACHE,cacheKey);
        log.debug("cacheName=["+CACHE_NAME_APPEND+"]  cacheKey=["+cacheKey+"] \t result="+ JSON.toJSONString(resultVo));

        url = URL_APPEND+hosId+URL_APPEND+URL_SUFFIX_CREATE_TEST_CACHE_SUCCESS;
        cacheKey = hosId+CACHE_NAME_APPEND+URL_SUFFIX_CREATE_TEST_CACHE_SUCCESS;
        resultVo = restTemplate.getForObject(url,ResultVo.class);
        log.debug("url=["+url+"]\t result="+JSON.toJSONString(resultVo));

        url = URL_APPEND+URL_SUFFIX_GET_RESULT;
        resultVo = restTemplate.getForObject(url,ResultVo.class,CACHE_NEMA_TESTCACHE,cacheKey);
        log.debug("cacheName=["+CACHE_NAME_APPEND+"]  cacheKey=["+cacheKey+"] \t result="+ JSON.toJSONString(resultVo));

        url = URL_APPEND+hosId+URL_APPEND+URL_SUFFIX_UPDATE_TEST_CACHE_SUCCESS;
        cacheKey = hosId+CACHE_NAME_APPEND+URL_SUFFIX_UPDATE_TEST_CACHE_SUCCESS;
        resultVo = restTemplate.getForObject(url,ResultVo.class);
        log.debug("url=["+url+"]\t result="+JSON.toJSONString(resultVo));

        url = URL_APPEND+URL_SUFFIX_GET_RESULT;
        resultVo = restTemplate.getForObject(url,ResultVo.class,CACHE_NEMA_TESTCACHE,cacheKey);
        log.debug("cacheName=["+CACHE_NAME_APPEND+"]  cacheKey=["+cacheKey+"] \t result="+ JSON.toJSONString(resultVo));

        url = URL_APPEND+hosId+URL_APPEND+URL_SUFFIX_UPDATE_TEST_CACHE_SUCCESS;
        cacheKey = hosId+CACHE_NAME_APPEND+URL_SUFFIX_UPDATE_TEST_CACHE_SUCCESS;
        resultVo = restTemplate.getForObject(url,ResultVo.class);
        log.debug("url=["+url+"]\t result="+JSON.toJSONString(resultVo));

        url = URL_APPEND+URL_SUFFIX_GET_RESULT;
        resultVo = restTemplate.getForObject(url,ResultVo.class,CACHE_NEMA_TESTCACHE,cacheKey);
        log.debug("cacheName=["+CACHE_NAME_APPEND+"]  cacheKey=["+cacheKey+"] \t result="+ JSON.toJSONString(resultVo));

        url = URL_APPEND+hosId+URL_APPEND+URL_SUFFIX_DELETE_CREATE_TEST_CACHE_SUCCESS;
        cacheKey = hosId+CACHE_NAME_APPEND+URL_SUFFIX_DELETE_CREATE_TEST_CACHE_SUCCESS;
        resultVo = restTemplate.getForObject(url,ResultVo.class);
        log.debug("url=["+url+"]\t result="+JSON.toJSONString(resultVo));

        url = URL_APPEND+URL_SUFFIX_GET_RESULT;
        resultVo = restTemplate.getForObject(url,ResultVo.class,CACHE_NEMA_TESTCACHE,cacheKey);
        log.debug("cacheName=["+CACHE_NAME_APPEND+"]  cacheKey=["+cacheKey+"] \t result="+ JSON.toJSONString(resultVo));

        url = URL_APPEND+hosId+URL_APPEND+URL_SUFFIX_DELETE_UPDATE_TEST_CACHE_SUCCESS;
        cacheKey = hosId+CACHE_NAME_APPEND+URL_SUFFIX_DELETE_UPDATE_TEST_CACHE_SUCCESS;
        resultVo = restTemplate.getForObject(url,ResultVo.class);
        log.debug("url=["+url+"]\t result="+JSON.toJSONString(resultVo));

        url = URL_APPEND+URL_SUFFIX_GET_RESULT;
        resultVo = restTemplate.getForObject(url,ResultVo.class,CACHE_NEMA_TESTCACHE,cacheKey);
        log.debug("cacheName=["+CACHE_NAME_APPEND+"]  cacheKey=["+cacheKey+"] \t result="+ JSON.toJSONString(resultVo));

    }


    @Test
    public void singleCacheStatisticTest(){
        for (int i = 0; i < 20; i++) {
            hosId++;
            String url = URL_APPEND+hosId+URL_APPEND+URL_SUFFIX_CREATE_TEST_CACHE_SUCCESS;
            restTemplate.getForObject(url,ResultVo.class);
        }
        String url = URL_APPEND+hosId+URL_APPEND+URL_SUFFIX_CREATE_TEST_CACHE_2_SUCCESS;
        restTemplate.getForObject(url,ResultVo.class);


        url = URL_APPEND+URL_SUFFIX_GET_CACHE_STATISTIC;
        restTemplate.getForObject(url,ResultVo.class);
    }

    @Test
    public void changeCacheManagerPeerProviderFactory(){
        String url = URL_APPEND+URL_SUFFIX_CHANGE_CACHE_MANAGER_PEER_PROVIDER_FACTORY;
        String rmiUrls="//127.0.0.1:40003/testCache|//127.0.0.1:40003/testCache2|//127.0.0.1:40002/testCache|//127.0.0.1:40002/testCache2 ";
        url=url+"?rmiUrls="+rmiUrls;
        restTemplate.getForObject(url,ResultVo.class);
    }



}

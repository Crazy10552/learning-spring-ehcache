package com.tianyang.learning.spring.ehcache.controller;

import com.tianyang.learning.spring.ehcache.constants.Constants;
import com.tianyang.learning.spring.ehcache.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.ManualRMICacheManagerPeerProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yttiany
 * @author yttiany
 * @Description: 用于试验spring ehcache 的相关代码
 * @ProjectName: self-working-space
 * @Package: com.tianyang.learning.spring.ehcache.controller
 * @Date Create on 2019/7/25.10:13
 * -----------------------------------------------------------.
 * @Modify - - - - - - - - - - - - - - - - - -
 * @ModTime 2019/7/25.10:13
 * @ModDesc:
 * @Modify - - - - - - - - - - - - - - - - - -
 * -----------------------------------------------------------
 */
@RestController
@Slf4j
public class TestCacheController implements Constants {


    @Resource
    EhCacheCacheManager ehCacheCacheManager;

    /**
     * value：缓存位置名称，不能为空，如果使用EHCache，就是ehcache.xml中声明的cache的name
     * key：缓存的key(保证唯一的参数)，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL
     *       缓存key还可以用如下规则组成,当我们要使用root作为key时，可以不用写root直接@Cache(key="caches[1].name")。因为他默认是使用#root的
     *      1.methodName   当前方法名        #root.methodName
     *      2.method      当前方法        #root.method.name
     *      3.target      当前被动用对象        #root.target
     *      4.targetClass   当前被调用对象        Class#root.targetClass
     *      5.args       当前方法参数组成的数组  #root.args[0]
     *      6.caches      当前被调用方法所使用的Cache   #root.caches[0],name
     *      7.方法参数     假设包含String型参数str      #str
     *                                #p0代表方法的第一个参数
     *               假设包含HttpServletRequest型参数request  #request.getAttribute('usId32')  调用入参对象的相关包含参数的方法
     *               假设包含User型参数user             #user.usId   调用入参对象的无参方法可以直接用此形式
     *      8.字符串                                                    '字符串内容'
     *
     * condition：触发条件,只有满足条件的情况才会加入缓存,默认为空,既表示全部都加入缓存,支持SpEL
     * unless：   触发条件,只有不满足条件的情况才会加入缓存,默认为空,既表示全部都加入缓存,支持SpEL
     *            #result  可以获得返回结果对象
     */

    /**
     * 请求成功,但是不能成功生成缓存的测试方法
     * @param hosId
     * @param request
     * @return
     */
    @Cacheable(value="testCache",key="#hosId+'_'+'createTestCacheSuccessButNoCache'", condition="#hosId!=null",unless="#result.result!=true or #result.data==null")
    @RequestMapping(value = "/{hosId}/createTestCacheSuccessButNoCache", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultVo createTestCacheSuccessButNoCache(@PathVariable Long hosId, HttpServletRequest request) {
        log.debug("进入实际生成缓存方法体,本次请求未使用缓存");
        ResultVo resultVo = new ResultVo();
        resultVo.setKind(SUCCESS_CODE);
        resultVo.setResult(true);
        return resultVo;
    }

    /**
     * 生成缓存,同时下一次在调用此方法优先从缓存中获取信息
     * 读取/生成缓存@Cacheable
     * 能够根据方法的请求参数对其结果进行缓存。即当重复使用相同参数调用方法的时候，方法本身不会被调用执行，即方法本身被略过了，取而代之的是方法的结果直接从缓存中找到并返回了。
     * @param hosId
     * @param request
     * @return
     */
    @Cacheable(value="testCache",key="#hosId+'_'+'createTestCacheSuccess'", condition="#hosId!=null",unless="#result.result!=true or #result.data==null")
    @RequestMapping(value = "/{hosId}/createTestCacheSuccess", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultVo createTestCacheSuccess(@PathVariable Long hosId, HttpServletRequest request) {

        ResultVo resultVo = new ResultVo();
        resultVo.setKind(SUCCESS_CODE);
        resultVo.setResult(true);
        resultVo.setData((Object)("createTestCacheSuccess成功生成缓存"+System.currentTimeMillis()));
        log.debug("进入实际生成缓存方法体,本次请求未使用缓存,本方法可以生成有效缓存,缓存未失效之前调用该方法将不会进入到方法体");
        return resultVo;
    }

    /**
     * 生成缓存,同时下一次在调用此方法还是会执行该方法并且同时更新缓存内容
     *
     * 更新缓存@CachePut
     * 它虽然也可以声明一个方法支持缓存，但它执行方法前是不会去检查缓存中是否存在之前执行过的结果，而是每次都执行该方法，并将执行结果放入指定缓存中。
     * @param hosId
     * @param request
     * @return
     */
    @CachePut(value="testCache",key="#hosId+'_'+'createTestCacheSuccess'", condition="#hosId!=null",unless="#result.result!=true or #result.data==null")
    @RequestMapping(value = "/{hosId}/updateTestCacheSuccess", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultVo updateTestCacheSuccess(@PathVariable Long hosId, HttpServletRequest request) {
        log.debug("进入实际生成缓存方法体,本方法可以生成有效缓存,下一次调用该方法依然会进入到方法体");
        ResultVo resultVo = new ResultVo();
        resultVo.setKind(SUCCESS_CODE);
        resultVo.setResult(true);
        resultVo.setData((Object)("updateTestCacheSuccess成功生成缓存"+System.currentTimeMillis()));
        return resultVo;
    }


    /**
     * 删除缓存
     * 删除缓存@CacheEvict
     * 根据value 和key值来唯一找到缓存记录，并且清理缓存信息
     * @param hosId
     * @param request
     * @return
     */
    @CacheEvict(value="testCache",key="#hosId+'_'+'createTestCacheSuccess'", condition="#hosId!=null")
    @RequestMapping(value = "/{hosId}/deleteCreateTestCacheSuccess", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultVo deleteCreateTestCacheSuccess(@PathVariable Long hosId, HttpServletRequest request) {
        log.debug("删除createTestCacheSuccess生成的缓存");
        ResultVo resultVo = new ResultVo();
        resultVo.setKind(SUCCESS_CODE);
        resultVo.setResult(true);
        resultVo.setData((Object)("删除缓存成功"+System.currentTimeMillis()));
        return resultVo;
    }



    /**
     * 能成功生成缓存的测试方法
     * @param hosId
     * @param request
     * @return
     */
    @Cacheable(value="testCache2",key="#hosId+'_'+'createTestCache2Success'", condition="#hosId!=null",unless="#result.result!=true or #result.data==null")
    @RequestMapping(value = "/{hosId}/createTestCache2Success", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultVo createTestCache2Success(@PathVariable Long hosId, HttpServletRequest request) {
        log.debug("进入实际生成缓存方法体,本次请求未使用缓存,本方法可以生成有效缓存");
        ResultVo resultVo = new ResultVo();
        resultVo.setKind(SUCCESS_CODE);
        resultVo.setResult(true);
        resultVo.setData((Object)("成功生成缓存"+System.currentTimeMillis()));
        return resultVo;
    }


    /**
     * 从cache 中获取实际缓存信息
     * @param cacheName
     * @param cacheKey
     * @return
     */
    @RequestMapping(value = "getResult", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultVo getResult(@RequestParam(value="cacheName") String cacheName,@RequestParam(value="cacheKey") String cacheKey){
        ResultVo resultVo = null;
        CacheManager cacheManager=ehCacheCacheManager.getCacheManager();
        if (cacheManager!=null){
            Ehcache ehcache = cacheManager.getEhcache(cacheName);
            if(ehcache!=null){
                Element element = ehcache.get(cacheKey);
                if(element!=null && element.getObjectValue()!=null
                        && element.getObjectValue() instanceof ResultVo){
                    resultVo = (ResultVo)element.getObjectValue();

                }
            }
        }
        return resultVo;
    }



    public static final String URL_DELIMITER = "|";
    /**
     * 修改spring-ehcache.xml 中的相关属性类(该文件不是spring常规的加载方式,在spring启动时通过${xxx}获取到值)
     * @return
     */
    @RequestMapping(value = "changeCacheManagerPeerProviderFactory", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultVo changeCacheManagerPeerProviderFactory(@RequestParam(value = "rmiUrls") String rmiUrls){
        ResultVo resultVo = null;
        //CacheManagerPeerProvider
        CacheManager cacheManager=ehCacheCacheManager.getCacheManager();
        if (cacheManager!=null){
            //此处查看源码可知返回的是一个ummoifyMap,字面意思不可更改的map(ConcureentHashMap)
            Map<String, CacheManagerPeerProvider> map = cacheManager.getCacheManagerPeerProviders();
            //默认生成的CacheManagerPeerProvider 对应的key是RMI
            CacheManagerPeerProvider cacheManagerPeerProvider = map.get("RMI");
            if( null != cacheManagerPeerProvider && cacheManagerPeerProvider instanceof ManualRMICacheManagerPeerProvider){
                ManualRMICacheManagerPeerProvider manualRMICacheManagerPeerProvider=(ManualRMICacheManagerPeerProvider)cacheManagerPeerProvider;
                StringTokenizer stringTokenizer = new StringTokenizer(rmiUrls, URL_DELIMITER);
                while (stringTokenizer.hasMoreTokens()) {
                    String rmiUrl = stringTokenizer.nextToken();
                    rmiUrl = rmiUrl.trim();
                    manualRMICacheManagerPeerProvider.registerPeer(rmiUrls);
                    log.debug("Registering peer {}", rmiUrl);
                }
                Map<String, CacheManagerPeerProvider> modifiableMap=null;
                Class clazz =null;
                try {
                    clazz = cacheManager.getClass();
                    Field fields[] = clazz.getDeclaredFields();
                    for (Field field:fields) {
                        if( "cacheManagerPeerProviders".equals(field.getName())){
                            field.setAccessible(true);
                            //获取属性
                            String name = field.getName();
                            //获取属性值
                            Object value = field.get(cacheManager);
                            modifiableMap=(ConcurrentHashMap)value;
                            modifiableMap.put("RMI",manualRMICacheManagerPeerProvider);
                            log.debug("");
                            break;
                        }
                    }
                    log.debug("");
                } catch (Exception e) {
                    log.error("",e);
                }
                ehCacheCacheManager.setCacheManager(cacheManager);
            }
        }
        return resultVo;
    }

    /**
     * 从cache 中获取缓存简单的监控信息(数据量少的时候适合这么干,数据量大的时候需要注意性能问题,一下子遍历所有缓存元素这将是一个灾难)
     * @return
     */
    @RequestMapping(value = "getCacheStatistic", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResultVo getCacheStatistic(){
        ResultVo resultVo = new ResultVo();
        resultVo.setResult(false);
        CacheManager cacheManager=ehCacheCacheManager.getCacheManager();
        if (cacheManager!=null){
            String []cacheNames=cacheManager.getCacheNames();
            if( null != cacheNames && cacheNames.length>0 ){
                StringBuffer ehcacheBuffer = new StringBuffer();
                ehcacheBuffer.append(StringUtils.rightPad("CacheName", 15));
                ehcacheBuffer.append(" | ");
                ehcacheBuffer.append(StringUtils.rightPad("Key", 40));
                ehcacheBuffer.append(" | ");
                ehcacheBuffer.append(StringUtils.rightPad("HintCount", 10));
                ehcacheBuffer.append(" | ");
                ehcacheBuffer.append(StringUtils.rightPad("CreationTime", 25));
                ehcacheBuffer.append(" | ");
                ehcacheBuffer.append(StringUtils.rightPad("LastAccessTime", 25));
                ehcacheBuffer.append(" | ");
                ehcacheBuffer.append(StringUtils.rightPad("TimeToLive(ms)", 15));
                ehcacheBuffer.append(" | ");
                ehcacheBuffer.append(StringUtils.rightPad("TimeToIdle(ms)", 15));
                //这里不打印数据值,因为打印值的话数据量比较大
                ehcacheBuffer.append(" | ");
                ehcacheBuffer.append("\n");
                for (int i = 0; i < cacheNames.length; i++) {
                    Ehcache ehcache = cacheManager.getCache(cacheNames[i]);
                    if(ehcache!=null){
                        List<String> ehcacheKeys = ehcache.getKeys();
                        if( null!=ehcacheKeys && 0< ehcacheKeys.size() ){
                            for (String ehcacheKey:ehcacheKeys) {
                                Element element = ehcache.get(ehcacheKey);
                                if(element!=null ){
                                    ehcacheBuffer.append(StringUtils.rightPad(ehcache.getName(), 15));//cachenName
                                    ehcacheBuffer.append(" | ");
                                    ehcacheBuffer.append(StringUtils.rightPad(ehcacheKey, 40));//key name
                                    ehcacheBuffer.append(" | ");
                                    ehcacheBuffer.append(StringUtils.rightPad(""+element.getHitCount(), 10));//命中次数
                                    ehcacheBuffer.append(" | ");
                                    ehcacheBuffer.append(StringUtils.rightPad(formatDate(element.getCreationTime()), 25));//创建时间
                                    ehcacheBuffer.append(" | ");
                                    ehcacheBuffer.append(StringUtils.rightPad(formatDate(element.getLastAccessTime()), 25));//最后访问时间
                                    ehcacheBuffer.append(" | ");
                                    ehcacheBuffer.append(StringUtils.rightPad(""+element.getTimeToLive(), 15));   //存活时间
                                    ehcacheBuffer.append(" | ");
                                    ehcacheBuffer.append(StringUtils.rightPad(""+element.getTimeToIdle(), 15));   //空闲时间
                                    ehcacheBuffer.append(" | ");
                                    ehcacheBuffer.append("\n");
                                }
                            }
                        }


                    }
                }
                log.debug("\n"+ehcacheBuffer.toString());
                resultVo.setData(ehcacheBuffer);
                resultVo.setResult(true);
            }
        }
        return resultVo;
    }

    /**
     * 简单的时间格式化
     * @param millis
     * @return
     */
    private static String formatDate(long millis){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            return formatter.format(calendar.getTime());
        } catch (Exception e) {
            log.error("",e);
        }
        return null;
    }


}

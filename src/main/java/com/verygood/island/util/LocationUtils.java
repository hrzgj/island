package com.verygood.island.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于调用地图api
 *
 * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
 * @date 2020-04-05 11:35
 */
@Slf4j
@Component
public class LocationUtils {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * api密钥
     */
    public static final String API_KEY = "95879aeccdec94c4cec7783aa5f2bc28";
    /**
     * 地理编码 api url
     */
    public static final String GEOCODE_URL = "https://restapi.amap.com/v3/geocode/geo";

    /**
     * 地理路径 api url
     */
    public static final String DISTANCE_URL = "https://restapi.amap.com/v3/distance";

    /**
     * 将一个地址信息转成经纬度
     *
     * @param location 地址信息，如：广东省广州市
     * @return 返回经纬度，格式为 经度,纬度 如：113.120312,23.232432
     * @name locationToCoordinate
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2020-05-07
     */
    public String locationToCoordinate(String location) {

        //构造请求参数
        Map<String, String> params = new HashMap<>();
        params.put("address", location);
        params.put("output", "json");
        params.put("key", API_KEY);
        //请求api
        Map<String, List<Map<String, String>>> responseObj = getResult(GEOCODE_URL, params);
        if (responseObj.get("geocodes") == null || responseObj.get("geocodes").size() == 0) {
            throw new RuntimeException("地址解析失败");
        }
        return responseObj.get("geocodes").get(0).get("location");
    }


    /**
     * 判断一个地址信息是否可以解析
     *
     * @param location 地址信息，如：广东省广州市
     * @return 如果该地址可以解析，返回true,否则返回false
     * @name isValidLocation
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2020-05-07
     */
    public boolean isValidLocation(String location) {
        try {
            locationToCoordinate(location);
            return true;
        } catch (Exception e) {
            log.warn("地址解析失败：{}", e.getMessage());
            return false;
        }
    }

    /**
     * @param origin      起点 如：广东省广州市
     * @param destination 终点 如：广东省深圳市
     * @return 返回两个点之间的距离 如 12930 单位：米
     * @name getDistance
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2020-05-07
     */
    public long getDistance(String origin, String destination) {
        //构造请求参数
        Map<String, String> params = new HashMap<>();
        params.put("origins", locationToCoordinate(origin));
        params.put("destination", locationToCoordinate(destination));
        params.put("output", "json");
        params.put("key", API_KEY);
        //请求api
        Map<String, List<Map<String, String>>> responseObj = getResult(DISTANCE_URL, params);
        if (responseObj.get("results") == null || responseObj.get("results").size() == 0) {
            throw new RuntimeException("地址解析失败");
        }
        return Long.parseLong(responseObj.get("results").get(0).get("distance"));
    }

    /**
     * 访问 api 并返回结果，其中至多自动重试10次
     *
     * @param url    api地址
     * @param params 请求参数
     * @return 返回api的结果
     * @name getResult
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2020-05-07
     */
    private Map<String, List<Map<String, String>>> getResult(String url, Map<String, String> params) {
        log.info("正在请求外部api(connecting {})", url);
        log.info("请求参数:{}", params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        //请求api
        String response = null;
        int count = 0;
        while (true) {
            count++;
            try {
                response = restTemplate.exchange(url, HttpMethod.GET,
                        new HttpEntity<String>(headers), String.class, params).getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (response != null || count > 10) {
                break;
            }
            log.info("第" + count + "次重试");
        }

        //解析结果
        log.info("收到外部api响应：response = {}", response);
        Map<String, List<Map<String, String>>> responseObj = null;
        try {
            responseObj = objectMapper.readValue(response, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (null == responseObj) {
            throw new RuntimeException("地址解析失败");
        }
        return responseObj;
    }
}

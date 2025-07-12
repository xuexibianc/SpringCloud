package com.example.service;

import com.example.api.MovieServiceFeign;
import com.example.dao.UserDao;
import com.example.po.Movie;
import com.example.po.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieServiceFeign movieServiceFeign;   //调用Feign接口；其实就是调用远程服务

    /**
     * 根据ID得到用户对象
     *
     * @param id
     * @return
     */
    public User getUserById(Integer id) {
        User user = userDao.getUser(id);
        return user;
    }

    /**
     * 购买最新的电影票
     * @param id 用户ID
     * @return
     */
    @CircuitBreaker( name = "backendA", fallbackMethod = "buyMovieFallbackMethod")
    public Map<String, Object> buyMovie(Integer id) {
        Map<String, Object> result = new HashMap<>();
        //1.查询用户信息
        User user = this.getUserById(id);
        result.put("user", user);
//2.查到最新电影票
        //2.查到最新电影票
        //Movie movie = restTemplate.getForObject("http://PROVIDERMOVIE/movie", Movie.class);
        Movie movie = movieServiceFeign.getNewMovie();
        //TODO 暂时为null
        result.put("movie", movie);
        return result;
    }



    public Map<String,Object> buyMovieFallbackMethod(Integer id ,Throwable e){
        e.printStackTrace();
        User user = new User();
        user.setId(-1);
        user.setUserName("未知用户");

        Movie movie = new Movie();
        movie.setId(-100);
        movie.setMovieName("无此电影");

        Map<String,Object> result = new HashMap<>();
        result.put("user",user);
        result.put("movice",movie);

        return result;
    }
}

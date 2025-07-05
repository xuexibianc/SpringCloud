package com.example.service;

import com.example.api.MovieServiceFeign;
import com.example.dao.UserDao;
import com.example.po.Movie;
import com.example.po.User;
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
}

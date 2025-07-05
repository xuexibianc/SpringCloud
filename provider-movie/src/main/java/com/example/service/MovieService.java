package com.example.service;

import com.example.dao.MovieDao;
import com.example.po.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    private MovieDao movieDao;

    public Movie getNewMovie() {
        return movieDao.getNewMovie();
    }
}

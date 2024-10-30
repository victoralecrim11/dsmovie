package com.devsuperior.dsmovie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.Movie;
import com.devsuperior.dsmovie.entities.Score;
import com.devsuperior.dsmovie.entities.User;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.repositories.UserRepository;

// Registra a classe como uma classe customizada no sistema
@Service
public class ScoreService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Transactional
    public void saveScore(ScoreDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail());

        if (user == null) {
            user = new User();
            user.setEmail(dto.getEmail());
            user = userRepository.saveAndFlush(user);
        }

        Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> 
            new IllegalArgumentException("Movie not found"));

        Score score = new Score();
        score.setMovie(movie);
        score.setUser(user);
        score.setScoreValue(dto.getScore());

        // Salva a entidade Score usando o scoreRepository, não o userRepository
        score = scoreRepository.saveAndFlush(score);
        
        double sum = 0;
        for (Score s : movie.getScores()) {
            sum += s.getScoreValue();
        }
        
        double avg = sum / movie.getScores().size();
        
        movie.setScore(avg);
        movie.setCount(movie.getScores().size());

        // Salva a entidade Movie no movieRepository
        movieRepository.save(movie);
    }
}

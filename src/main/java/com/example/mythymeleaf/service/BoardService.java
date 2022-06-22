package com.example.mythymeleaf.service;

import com.example.mythymeleaf.model.Board;
import com.example.mythymeleaf.model.User;
import com.example.mythymeleaf.repository.BoardRepository;
import com.example.mythymeleaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void save(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    public Page<Board> findByTitleContainingOrContentContaining(String title, String inContent, Pageable pageable) {
        return boardRepository.findByTitleContainingOrContentContainingOrderByIdDesc(title, inContent, pageable);
    }
}

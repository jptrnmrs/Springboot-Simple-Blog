package com.example.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public long create(BoardDTO board) {
        Board entity = board.toBoard();
        return boardRepository.save(entity).getId();
    }

    public Page<Board> readAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public BoardDTO read(Long id) {
        Board board = boardRepository.findById(id).get();
        return board.convertToDTO();
    }

    public boolean delete(Long id) {
        try {
            boardRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}

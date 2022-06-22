package com.example.mythymeleaf.controller;

import com.example.mythymeleaf.model.Board;
import com.example.mythymeleaf.repository.BoardRepository;
import com.example.mythymeleaf.service.BoardService;
import com.example.mythymeleaf.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    private final BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size=2) Pageable pageable,
                       @RequestParam(required = false, defaultValue="") String searchText){
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardService.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        //boards.getTotalElements();
        int startPage = Math.max(1, boards.getPageable().getPageNumber() -4);
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber()+4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            model.addAttribute("board", new Board());
        }else{
            Board board = boardService.findById(id).orElseGet(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String boardSubmit(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors())
            return "board/form";
        String username = authentication.getName();
//        board.setUser(username);
        boardService.save(username, board);
        return "redirect:/board/list";
    }
}

package com.example.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MainController {

    private final BoardService boardService;

    @GetMapping(value = {"/",""})
    public String index(Model model, @PageableDefault(sort = "id", size = 5, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> list = boardService.readAll(pageable);
        model.addAttribute("boards", list);

        int blockLimit = 5;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), list.getTotalPages());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "index";
    }

    @GetMapping("/write")
    public String write(){
        return "write";
    }

    @ResponseBody
    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody BoardDTO dto){
        log.info(dto);
        Long result = boardService.create(dto);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        BoardDTO board = boardService.read(id);
        model.addAttribute("board", board);
        return "edit";
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        boolean result = boardService.delete(id);
        return ResponseEntity.ok().body(result);
    }

}

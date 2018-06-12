package com.wangrj.note.controller;

import com.wangrj.note.entity.Note;
import com.wangrj.note.repository.NoteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * by wangrongjun on 2018/5/31.
 */
@Controller
@ResponseBody
@RequestMapping("/note")
public class NoteController {

    @Resource
    private NoteRepository noteRepository;

    @GetMapping
    public List<Note> getNoteList() {
        return noteRepository.findAll(new Sort(Sort.Direction.DESC, "createdOn"));
    }

    @PostMapping
    public Note addNote(String content) {
        Note note = new Note(content, new Date());
        noteRepository.save(note);
        return note;
    }

}

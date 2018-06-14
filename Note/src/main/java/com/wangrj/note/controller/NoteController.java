package com.wangrj.note.controller;

import com.wangrj.note.entity.Note;
import com.wangrj.note.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * by wangrongjun on 2018/5/31.
 */
@Controller
@ResponseBody
@RequestMapping("/note")
public class NoteController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Resource
    private NoteRepository noteRepository;

    @GetMapping
    public List<Note> getNoteList() {
        return noteRepository.findAll(new Sort(Sort.Direction.DESC, "createdOn"));
    }

    @PostMapping
    public Note addNote(@NotBlank String content) {
        logger.info(content);
        Note note = new Note(content, new Date());
        noteRepository.save(note);
        return note;
    }

    @PutMapping("/{noteId}")
    public void updateNote(@PathVariable Integer noteId, String content) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) {
            throw new IllegalArgumentException("noteId " + noteId + " 不存在");
        }
        note.setContent(content);
        noteRepository.save(note);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Integer noteId) {
        noteRepository.deleteById(noteId);
    }

}

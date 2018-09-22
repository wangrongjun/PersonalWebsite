package com.wangrj.note.controller;

import com.wangrj.java_lib.java_util.excel.EntityConverter;
import com.wangrj.java_lib.java_util.excel.ExcelWriter;
import com.wangrj.note.entity.Note;
import com.wangrj.note.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * by wangrongjun on 2018/5/31.
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Resource
    private NoteRepository noteRepository;

    @GetMapping
    public ResponseEntity<List<Note>> getNoteList() {
        List<Note> noteList = noteRepository.findAll(new Sort(Sort.Direction.DESC, "createdOn"));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(noteList);
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@NotBlank String content) {
        logger.info(content);
        Note note = new Note(content, new Date());
        noteRepository.save(note);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(note);
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

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Note> noteList = noteRepository.findAll(new Sort(Sort.Direction.DESC, "createdOn"));

        ExcelWriter excelWriter = new ExcelWriter();
        EntityConverter entityConverter = new EntityConverter();
        List<List<Object>> valueLists = entityConverter.entityListToValueLists(noteList);
        byte[] bytes = excelWriter.writeExcel(valueLists);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String excelFileName = "Note." + time + ".xls";
        response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);
        ServletOutputStream os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }

    /**
     * 访问时，必须把Content-Type设置为application/json，否则会报错（因为没有对应的MessageConverter来处理）
     */
    @PostMapping(value = "/importFromJson")
    public ResponseEntity<String> importFromJson(@RequestBody List<Note> noteList) {
        List<String> skipNote = new ArrayList<>();
        for (Note note : noteList) {
            note.setNoteId(null);
            List<Note> simpleNoteList = noteRepository.findByContentAndAndCreatedOn(note.getContent(), note.getCreatedOn());
            if (simpleNoteList.size() > 0) {
                skipNote.add(note.getContent());
                continue;
            }
            noteRepository.save(note);
        }

        int totalCount = noteList.size();
        int skipCount = skipNote.size();

        String result = String.format("读取数据 %d 条，导入 %d 条，跳过 %d 条。\n跳过的数据：\n%s",
                totalCount,
                totalCount - skipCount,
                skipCount,
                skipNote.stream().collect(Collectors.joining("\n======================\n"))
        );

        return ResponseEntity.ok(result);
    }

}

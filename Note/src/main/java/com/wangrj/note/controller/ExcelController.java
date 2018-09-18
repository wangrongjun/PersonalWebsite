package com.wangrj.note.controller;

import com.wangrj.java_lib.java_util.excel.EntityConverter;
import com.wangrj.java_lib.java_util.excel.ExcelWriter;
import com.wangrj.note.entity.Note;
import com.wangrj.note.repository.NoteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ExcelController {

    @Resource
    private NoteRepository noteRepository;

    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
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

}

package com.wangrj.note;

import com.wangrj.java_lib.java_util.DateUtil;
import com.wangrj.note.entity.Note;
import com.wangrj.note.repository.NoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * by wangrongjun on 2018/6/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteInsertTest {

    @Resource
    private NoteRepository noteRepository;

    @Test
    public void testInsert() {
        noteRepository.deleteAll();
        for (int i = 1; i <= 10; i++) {
            Note note = new Note("note_" + i, DateUtil.toDate("2016-01-" + i));
            noteRepository.save(note);
        }
    }

}

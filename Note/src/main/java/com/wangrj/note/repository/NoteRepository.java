package com.wangrj.note.repository;

import com.wangrj.note.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByContentAndAndCreatedOn(String content, Date createdOn);

}

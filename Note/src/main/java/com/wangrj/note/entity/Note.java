package com.wangrj.note.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Note {

    @Id
    @GeneratedValue
    private Integer noteId;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdOn;

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", content='" + content + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }

    public Note() {
    }

    public Note(Integer noteId) {
        this.noteId = noteId;
    }

    public Note(String content, Date createdOn) {
        this.content = content;
        this.createdOn = createdOn;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}

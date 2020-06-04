package com.accolite.assessmentmanagement.models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Quiz {

//    TODO: Refer quiz to user

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name = "quiz_questions",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> Questions;

    private String title;
    private String description;

    public Quiz() {
    }

    public Quiz(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Question> getQuestions() {
        return Questions;
    }

    public void setQuestions(Set<Question> questions) {
        Questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return id.equals(quiz.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

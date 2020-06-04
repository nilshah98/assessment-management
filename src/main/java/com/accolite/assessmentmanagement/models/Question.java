package com.accolite.assessmentmanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Question {

//    TODO: Refer question to user

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "question", cascade = { CascadeType.ALL })
    private List<Option> options;

    @ManyToMany(mappedBy = "Questions")
    @JsonIgnore
    private Set<Quiz> quizes;

    private String description;
    private Integer correctOption;

    public Question() { }

    public Question(String description, List<Option> options, Integer correctOption){
        this.description = description;
        this.options = options;
        this.correctOption = correctOption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(Integer correctOption) {
        this.correctOption = correctOption;
    }

    public void addOptions(List<Option> options){
        for (Option o: options) { o.setQuestion(this);}
    }

    public Set<Quiz> getQuizes() {
        return quizes;
    }

    public void setQuizes(Set<Quiz> quizes) {
        this.quizes = quizes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id.equals(question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", options=" + options +
                ", description='" + description + '\'' +
                ", correctOption=" + correctOption +
                '}';
    }
}

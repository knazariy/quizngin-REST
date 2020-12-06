package com.nklymok.quizngin.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Quiz")
@Table(name = "Quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @NotBlank(message = "title is required")
    @Column(name = "TITLE")
    private String title;

    @NotBlank(message = "text is required")
    @Column(name = "TEXT")
    private String text;

    @NotEmpty(message = "options are required") @Size(min = 2)
    @ElementCollection
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    private Set<Integer> answer;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "AUTHOR")
    private String authorUsername;

    protected Quiz() { }

    @JsonCreator
    public Quiz(@JsonProperty("title") String title,
                @JsonProperty("text") String text,
                @JsonProperty("options") List<String> options,
                @JsonProperty("answer") @Nullable List<Integer> answer
                ) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer == null ? null : new HashSet<>(answer);
    }

    public boolean check(List<Integer> answers) {
        if (answers == null) {
            return this.answer == null || this.answer.isEmpty();
        }
        if (this.answer == null) {
            return answers.isEmpty();
        }

        return this.answer.equals(new HashSet<>(answers));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setAnswer(Set<Integer> answer) {
        this.answer = answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public List<String> getOptions() {
        return this.options;
    }

    public Set<Integer> getAnswer() {
        return this.answer;
    }

    public Integer getId() {
        return this.id;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String name) {
        this.authorUsername = name;
    }
}

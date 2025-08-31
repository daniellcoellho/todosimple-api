package com.danielcoelho.todosimple.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = Task.TABLE_NAME)    //Nome da tabela
public class Task {
  
  public static final String TABLE_NAME = "task"; //Definindo nome da tabela
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //Sequence padrão no MySql
  @Column(name = "id", unique = true)
  private Long id;

  @ManyToOne    // Define que várias tarefas, serão de um usuário  
  @JoinColumn(name="user_id", nullable = false, updatable = false)
  private User user;

  @Column(name = "description", length = 255, nullable = false)
  @NotBlank
  @Size(min = 1, max = 255)
  private String description;


  public Task() {
  }

  public Task(Long id, User user, String description) {
    this.id = id;
    this.user = user;
    this.description = description;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Task id(Long id) {
    setId(id);
    return this;
  }

  public Task user(User user) {
    setUser(user);
    return this;
  }

  public Task description(String description) {
    setDescription(description);
    return this;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;

    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

    return result;

  }

  
  
}

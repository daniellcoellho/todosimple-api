package com.danielcoelho.todosimple.models;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = User.TABLE_NAME)    //Nome da tabela
public class User {
  
  public interface CreateUser {};
  public interface UpdateUser {};

  public static final String TABLE_NAME = "user"; //Definindo nome da tabela
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //Sequence padrão no MySql
  @Column(name = "id", unique = true)
  private Long id;

  @Column(name = "username", length = 100, nullable = false)
  @NotBlank(groups = CreateUser.class)
  @Size(groups = CreateUser.class, min = 2, max = 100)
  private String username;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Column(name = "password", length = 35, nullable = false)
  @NotBlank(groups = {CreateUser.class, UpdateUser.class })
  @Size(groups = CreateUser.class, min = 5, max = 35)
  private String password;

  
  @OneToMany(mappedBy = "user")      // Um usuário pode ter várias tasks
  private List<Task> tasks = new ArrayList<Task>();


  public List<Task> getTasks() { 
    return this.tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }


  public User() {
  }

  public User(Long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }


  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;

    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

    return result;
  }
  

}

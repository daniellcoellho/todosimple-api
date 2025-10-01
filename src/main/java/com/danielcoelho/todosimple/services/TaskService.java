package com.danielcoelho.todosimple.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielcoelho.todosimple.repositories.TaskRepository;

import jakarta.transaction.Transactional;

import com.danielcoelho.todosimple.models.Task;
import com.danielcoelho.todosimple.models.User;

@Service
public class TaskService {

  @Autowired
  private UserService userService;

  @Autowired
  private TaskRepository taskRepository;
  

  public Task findById(Long id) {

    Optional<Task> task = this.taskRepository.findById(id);

    return task.orElseThrow(() -> new RuntimeException(
        "Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()));
  }


  public List<Task> findAllByUserId(Long userId) {
    return taskRepository.findByUser_Id(userId);
 }
  
  @Transactional
  public Task create(Task obj) {

    User user = this.userService.findById(obj.getUser().getId());

    obj.setId(null);

    obj.setUser(user);
    obj = this.taskRepository.save(obj);

    return obj;
  }
  
  @Transactional
  public Task update(Task obj) {

    Task newObj = findById(obj.getId());
    newObj.setDescription(obj.getDescription());

    return this.taskRepository.save(newObj);
  }
  
   @Transactional
  public void delete(Long id) {
  
    findById(id);

    try {
      this.taskRepository.deleteById(id);

    } catch (Exception e) {

      throw new RuntimeException("Não é possível excluir, possui entidades relacionadas");
    }
  }
}

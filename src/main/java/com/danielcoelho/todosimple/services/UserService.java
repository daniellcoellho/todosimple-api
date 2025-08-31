package com.danielcoelho.todosimple.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielcoelho.todosimple.models.User;
import com.danielcoelho.todosimple.repositories.UserRepository;

import jakarta.transaction.Transactional;
import java.util.Optional;



@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;

  public User findById(Long id) { // Consultar usuário pelo ID

    Optional<User> user = this.userRepository.findById(id);

    return user.orElseThrow(() -> new RuntimeException( // Caso não localizar, retornar o erro!
        "Usuário não encontrado Id: " + id + ", Tipo: " + User.class.getName()));
  }
  
  @Transactional   
  public User create(User obj) {

    obj.setId(null);
    obj = this.userRepository.save(obj);

    return obj;
  }
  
  @Transactional  
  public User update(User obj) {

    User newObj = findById(obj.getId());
    newObj.setPassword(obj.getPassword());

    return this.userRepository.save(newObj);
  }
  
  public void delete(Long id) {
    
    findById(id);

    try {
      this.userRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("Não é possível excluir, possui entidades relacionadas");
    }
  }
}

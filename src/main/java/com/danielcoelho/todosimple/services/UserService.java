package com.danielcoelho.todosimple.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.danielcoelho.todosimple.models.User;
import com.danielcoelho.todosimple.models.enums.ProfileEnum;
import com.danielcoelho.todosimple.repositories.UserRepository;
import com.danielcoelho.todosimple.services.exceptions.DataBindingViolationException;
import com.danielcoelho.todosimple.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Service
public class UserService {
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private UserRepository userRepository;

  public User findById(Long id) { // Consultar usuário pelo ID

    Optional<User> user = this.userRepository.findById(id);

    return user.orElseThrow(() -> new ObjectNotFoundException( // Caso não localizar, retornar o erro!
        "Usuário não encontrado Id: " + id + ", Tipo: " + User.class.getName()));
  }
  
  @Transactional   
  public User create(User obj) {

    obj.setId(null); 
    obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
    obj.setProfiles(Stream.of(ProfileEnum.USER.getCode())
                    .collect(Collectors.toSet()));
    obj = this.userRepository.save(obj);

    return obj;
  }
  
  @Transactional  
  public User update(User obj) {

    User newObj = findById(obj.getId());
    newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
  
    return this.userRepository.save(newObj);
  }
  
  public void delete(Long id) {
    
    findById(id);

    try {
      this.userRepository.deleteById(id);
    } catch (Exception e) {
      throw new DataBindingViolationException("Não é possível excluir, possui entidades relacionadas");
    }
  }
}

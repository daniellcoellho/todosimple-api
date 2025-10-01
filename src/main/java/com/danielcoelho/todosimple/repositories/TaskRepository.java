package com.danielcoelho.todosimple.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danielcoelho.todosimple.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser_Id(Long id);
}

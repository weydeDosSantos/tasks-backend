package br.ce.wcaquino.taskbackend.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

	@Mock  
	private TaskRepo taskrepo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void naoDeveSalverTarefaSemDescricao() {
		Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar neste ponto");
		} catch (ValidationException e) {
			assertEquals("Fill the task description", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalverTarefaSemData() {
		Task todo = new Task();
		todo.setTask("Descricao");
		// todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar neste ponto");
		} catch (ValidationException e) {
			assertEquals("Fill the due date", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalverTarefaComDataPassada() {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.of(2010, 01, 01));
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar neste ponto");
		} catch (ValidationException e) {
			assertEquals("Due date must not be in past", e.getMessage());
		}
	}

	@Test
	public void DeveSalverTarefaComSucesso() throws ValidationException {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		
	}
}

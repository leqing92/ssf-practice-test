package sg.edu.nus.iss.practicetest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.practicetest.Model.Todo;
import sg.edu.nus.iss.practicetest.Service.TodoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping(path = "/todos")
public class TodoController {
    
    @Autowired
    TodoService todoService;

//Create
    @GetMapping("/add")
    public String getAddPage(Model model) {
        Todo todo = new Todo();
        todo.setId(todoService.generateUniqueId());
        model.addAttribute("todo", todo);
        return "add";
    }
    

    @PostMapping(path = "/add")
    public String postTodo(Model model, @ModelAttribute("todo") @Valid Todo todo, BindingResult bindings) {
        if (bindings.hasErrors()) {
            model.addAttribute("todo", todo);            
            
            System.out.println("Global error: " + bindings.getGlobalErrors());
            System.out.println("Field error:" + bindings.getFieldErrors());
            return "add";
        }else{
            Todo TodoWithId = todoService.createTodoWithoutDate(todo);
            model.addAttribute("todo", TodoWithId);
    
            return "success";
        }
    } 

//Read
    @GetMapping(path = "/list")
    public String getTodoList(Model model, HttpSession httpSession, HttpServletResponse response) {
        if(null != httpSession.getAttribute("fullname") && null != httpSession.getAttribute("age")){
            List <Todo> todoList = todoService.getTodoList();
            model.addAttribute("todos", todoList);
            return "listing";
        }
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "refused";
        }
    }

//Update
    @GetMapping("/edit/{id}")
    public String gameToBeEdit (@PathVariable("id") String id, HttpServletResponse response, Model model){
        
        Todo todo = todoService.getTodoById(id);
        if(null != todo){            
            model.addAttribute("todo", todo);
            return "edit";
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "idnotfound";
        }
    }

    @PostMapping("/edit")
    public String editGame (HttpServletResponse response, Model model, @ModelAttribute("todo") @Valid Todo todo, BindingResult bindings){
        
        if(bindings.hasErrors()){            
            return "edit";
        }else{
            todo.setCreateAt(todoService.getTodoById(todo.getId()).getCreateAt());
            todoService.updateTodo(todo);
            model.addAttribute("todo",todo);
            response.setStatus(HttpServletResponse.SC_CREATED);
            return "success";
        }
    }

//Delete
    @GetMapping("/delete/{id}")
    public String deleteTodo (@PathVariable("id") String id){
        todoService.deleteTodoById(id);

        return "redirect:/todos/list";
    }

//Filter
    @GetMapping("/filter/{status}")
    public String filterByStatus(@PathVariable("status") String status, Model model) {
        List<Todo> filteredTodos = todoService.getTodoListByStatus(status);
        model.addAttribute("todos", filteredTodos);
        return "listing";
    }

}

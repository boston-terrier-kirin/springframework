import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { Link } from 'react-router-dom';
import todoService from '../api/todosService';
import Heading from '../components/Heading';
import AuthContext from '../context/AuthContext';

const TodosRoute = () => {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    const getAllTodos = async () => {
      const data = await todoService.getAllTodos(user.username);
      setTodos(data);
    };

    getAllTodos();
  }, [user.username]);

  const deleteTodo = async (id) => {
    await todoService.deleteTodo(user.username, id);
    const updatedTodos = todos.filter((t) => t.id !== id);
    setTodos(updatedTodos);
  };

  const updateTodo = (id) => {
    navigate(`/todo/${id}`);
  };

  const todosToRender = todos.map((todo) => {
    return (
      <tr key={todo.id}>
        <td>{todo.id}</td>
        <td>{todo.description}</td>
        <td>{todo.done.toString()}</td>
        <td>{todo.targetDate}</td>
        <td>
          <button
            onClick={() => deleteTodo(todo.id)}
            className="btn btn-danger"
          >
            Delete
          </button>
        </td>
        <td>
          <button
            onClick={() => updateTodo(todo.id)}
            className="btn btn-primary"
          >
            Update
          </button>
        </td>
      </tr>
    );
  });

  return (
    <>
      <Heading>Things you want to do</Heading>

      <Link to="/todo" className="btn btn-primary mb-3">
        Add a New Task
      </Link>

      <table className="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Description</th>
            <th>Done?</th>
            <th>Target Date</th>
            <th></th>
            <th></th>
          </tr>
        </thead>
        <tbody>{todosToRender}</tbody>
      </table>
    </>
  );
};

export default TodosRoute;

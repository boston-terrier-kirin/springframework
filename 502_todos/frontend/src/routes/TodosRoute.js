import React from 'react';
import Header from '../components/Header';
import Heading from '../components/Heading';

const todos = [
  {
    id: 1,
    description: 'Learn AWS',
    done: false,
    targetDate: '2023/2/28',
  },
  {
    id: 2,
    description: 'Learn Docker',
    done: false,
    targetDate: '2023/4/30',
  },
  {
    id: 3,
    description: 'Learn Spring Boot',
    done: false,
    targetDate: '2023/2/10',
  },
];

const TodosRoute = () => {
  const todosToRender = todos.map((todo) => {
    return (
      <tr key={todo.id}>
        <td>{todo.id}</td>
        <td>{todo.description}</td>
        <td>{todo.done.toString()}</td>
        <td>{todo.targetDate}</td>
      </tr>
    );
  });

  return (
    <>
      <Heading>Things you want to do</Heading>

      <table className="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Description</th>
            <th>Done?</th>
            <th>Target Date</th>
          </tr>
        </thead>
        <tbody>{todosToRender}</tbody>
      </table>
    </>
  );
};

export default TodosRoute;

import { useFormik } from 'formik';
import { useContext, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import todoService from '../api/todosService';
import Heading from '../components/Heading';
import AuthContext from '../context/AuthContext';

const TodoRoute = () => {
  const { user } = useContext(AuthContext);
  const { id } = useParams();
  const navigate = useNavigate();

  const form = useFormik({
    initialValues: {
      description: '',
      targetDate: '',
      done: false,
    },
    onSubmit: async (values) => {
      const todo = {
        ...values,
        id,
        username: user.username,
      };

      if (id) {
        await todoService.updateTodo(user.username, id, todo);
      } else {
        await todoService.addTodo(user.username, todo);
      }

      navigate('/todos');
    },
  });

  useEffect(
    () => {
      const getTodo = async (id) => {
        const todo = await todoService.getTodo(user.username, id);

        form.setValues({
          description: todo.description,
          targetDate: todo.targetDate,
          done: todo.done,
        });
      };

      if (id) {
        getTodo(id);
      }
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [id, user.username]
  );

  return (
    <div>
      {id ? <Heading>Update Todo</Heading> : <Heading>Add Todo</Heading>}

      <form onSubmit={form.handleSubmit}>
        <div className="mb-3">
          <label htmlFor="description" className="form-label">
            Description
          </label>
          <input
            type="text"
            className="form-control"
            id="description"
            placeholder="Description"
            value={form.values.description}
            onChange={form.handleChange}
          />
        </div>

        <div className="mb-4">
          <label htmlFor="targetDate" className="form-label">
            Target Date
          </label>
          <input
            type="date"
            className="form-control"
            id="targetDate"
            placeholder="Target Date"
            value={form.values.targetDate}
            onChange={form.handleChange}
          />
        </div>

        <div className="mb-4">
          <label htmlFor="done" className="form-label">
            Done?
          </label>
          <input
            type="text"
            className="form-control"
            id="done"
            placeholder="Done?"
            value={form.values.done}
            onChange={form.handleChange}
          />
        </div>
        <button className="btn btn-primary">Update</button>
      </form>
    </div>
  );
};

export default TodoRoute;

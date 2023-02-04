import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

const healthCheck = async () => {
  const res = await api.get('/test');
  return res.data;
};

const getAllTodos = async (username) => {
  const res = await api.get(`/users/${username}/todos`);
  return res.data;
};

const getTodo = async (username, id) => {
  const res = await api.get(`/users/${username}/todos/${id}`);
  return res.data;
};

const updateTodo = async (username, id, todo) => {
  const res = await api.put(`/users/${username}/todos/${id}`, todo);
  return res.data;
};

const deleteTodo = async (username, id) => {
  const res = await api.delete(`/users/${username}/todos/${id}`);
  return res.data;
};

const todosSerive = {
  healthCheck,
  getAllTodos,
  getTodo,
  updateTodo,
  deleteTodo,
};

export default todosSerive;

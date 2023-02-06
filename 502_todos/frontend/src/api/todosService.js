import apiClient from './apiClient';

const healthCheck = async () => {
  const res = await apiClient.get('/test');
  return res.data;
};

const getAllTodos = async (username) => {
  const res = await apiClient.get(`/users/${username}/todos`);
  return res.data;
};

const getTodo = async (username, id) => {
  const res = await apiClient.get(`/users/${username}/todos/${id}`);
  return res.data;
};

const addTodo = async (username, todo) => {
  const res = await apiClient.post(`/users/${username}/todos`, todo);
  return res.data;
};

const updateTodo = async (username, id, todo) => {
  const res = await apiClient.put(`/users/${username}/todos/${id}`, todo);
  return res.data;
};

const deleteTodo = async (username, id) => {
  const res = await apiClient.delete(`/users/${username}/todos/${id}`);
  return res.data;
};

const todoService = {
  healthCheck,
  getAllTodos,
  getTodo,
  addTodo,
  updateTodo,
  deleteTodo,
};

export default todoService;

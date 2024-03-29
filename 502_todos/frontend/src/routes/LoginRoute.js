import { useState, useContext } from 'react';
import { useNavigate } from 'react-router';
import authService from '../api/authService';
import Heading from '../components/Heading';
import Message from '../components/Message';
import AuthContext from '../context/AuthContext';

const LoginRoute = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(false);

  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const token = await authService.basicAuth(username, password);
      login(username, token);
      navigate('/home');
    } catch (err) {
      setError(true);
    }
  };

  return (
    <>
      <Heading>Login</Heading>

      {error && (
        <Message type="error">
          Authentication failed. Please check credentials.
        </Message>
      )}

      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="username" className="form-label">
            Username
          </label>
          <input
            type="text"
            className="form-control"
            id="username"
            placeholder="Username"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
          />
        </div>
        <div className="mb-4">
          <label htmlFor="password" className="form-label">
            Password
          </label>
          <input
            type="password"
            className="form-control"
            id="password"
            placeholder="Password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
          />
        </div>
        <button className="btn btn-primary">Login</button>
      </form>
    </>
  );
};

export default LoginRoute;

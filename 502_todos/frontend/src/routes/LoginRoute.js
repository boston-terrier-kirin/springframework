import { useState } from 'react';
import { useNavigate } from 'react-router';
import Heading from '../components/Heading';
import Message from '../components/Message';

const LoginRoute = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();

    if (username === 'kirin' && password === 'test') {
      navigate('/home');
      return;
    }

    setError(true);
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

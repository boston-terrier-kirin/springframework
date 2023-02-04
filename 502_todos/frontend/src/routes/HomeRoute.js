import { useState } from 'react';
import { Link } from 'react-router-dom';

import Heading from '../components/Heading';
import todosSerive from '../api/todosService';

const HomeRoute = () => {
  const [test, setTest] = useState('');

  const handleTest = async () => {
    const data = await todosSerive.healthCheck('/test');
    console.log(data);
    setTest(data);
  };

  return (
    <>
      <Heading>Manage your todos</Heading>
      <p className="d-flex align-items-center">
        Welcome back.
        <Link to="/todos" className="btn btn-link">
          Here's your todos.
        </Link>
      </p>
      <button className="btn btn-primary me-3" onClick={handleTest}>
        TEST
      </button>
      {test}
    </>
  );
};

export default HomeRoute;

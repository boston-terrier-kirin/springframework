import { Link } from 'react-router-dom';
import Heading from '../components/Heading';

const HomeRoute = () => {
  return (
    <>
      <Heading>Manage your todos</Heading>
      <p className="d-flex align-items-center">
        Welcome back.
        <Link to="/todos" className="btn btn-link">
          Here's your todos.
        </Link>
      </p>
    </>
  );
};

export default HomeRoute;

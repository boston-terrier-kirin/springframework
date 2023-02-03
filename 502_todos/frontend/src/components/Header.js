import { computeHeadingLevel } from '@testing-library/react';
import React from 'react';
import { useContext } from 'react';
import { Link } from 'react-router-dom';
import AuthContext from '../context/AuthContext';

const Header = () => {
  const { user } = useContext(AuthContext);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container-fluid">
        <Link to="/home" className="navbar-brand">
          Todo App
        </Link>
        {user.isAuthenticated && (
          <>
            <div className="navbar-nav me-auto">
              <Link to="/home" className="nav-link active">
                Home
              </Link>
              <Link to="/todos" className="nav-link active">
                Todos
              </Link>
            </div>
            <div className="navbar-nav">
              <Link to="/logout" className="nav-link active">
                Logout
              </Link>
            </div>
          </>
        )}
      </div>
    </nav>
  );
};

export default Header;

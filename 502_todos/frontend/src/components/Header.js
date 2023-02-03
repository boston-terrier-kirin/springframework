import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <nav className="navbar navbar-dark bg-dark">
      <div className="container-fluid">
        <Link to="/home" className="navbar-brand">
          Todo App
        </Link>
        <div className="ms-auto">
          <Link to="/home" className="btn btn-light me-2">
            Home
          </Link>
          <Link to="/login" className="btn btn-light">
            Logout
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default Header;

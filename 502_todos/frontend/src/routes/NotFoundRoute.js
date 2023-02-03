import React from 'react';
import { Link } from 'react-router-dom';
import Heading from '../components/Heading';

const NotFoundRoute = () => {
  return (
    <div className="row">
      <div className="com-md-12 text-center">
        <Heading>404</Heading>
        <div className="mb-4 lead">
          Oops! We can't seem to find the page you are looking for.
        </div>
        <Link to="/" className="btn btn-link">
          Back to Home
        </Link>
      </div>
    </div>
  );
};

export default NotFoundRoute;

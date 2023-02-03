import React from 'react';
import { Outlet } from 'react-router';
import Header from '../components/Header';

const RootRoute = () => {
  return (
    <>
      <Header />
      <div className="container mt-2">
        <Outlet />
      </div>
    </>
  );
};

export default RootRoute;

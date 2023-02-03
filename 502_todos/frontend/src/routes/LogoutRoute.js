import { useEffect, useContext } from 'react';
import { useNavigate } from 'react-router';
import AuthContext from '../context/AuthContext';

const LogoutRoute = () => {
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    logout();
    navigate('/');
  }, [logout, navigate]);

  return null;
};

export default LogoutRoute;

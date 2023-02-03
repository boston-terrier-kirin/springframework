import { createContext, useReducer } from 'react';
import authReducer from './AuthReducer';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const initialState = { username: null, isAuthenticated: false };
  const [state, dispatch] = useReducer(authReducer, initialState);

  const login = (username, password) => {
    dispatch({
      type: 'LOGIN',
      payload: { username, password },
    });
  };

  const logout = () => {
    dispatch({
      type: 'LOGOUT',
      payload: null,
    });
  };

  return (
    <AuthContext.Provider value={{ user: state, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;

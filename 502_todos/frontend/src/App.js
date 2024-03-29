import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import LoginRoute from './routes/LoginRoute';
import HomeRoute from './routes/HomeRoute';
import TodosRoute from './routes/TodosRoute';
import NotFoundRoute from './routes/NotFoundRoute';
import RootRoute from './routes/RootRoute';
import { AuthProvider } from './context/AuthContext';
import LogoutRoute from './routes/LogoutRoute';
import PrivateRoute from './routes/AuthenticatedRoute';
import TodoRoute from './routes/TodoRoute';

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<RootRoute />}>
            <Route path="" element={<LoginRoute />} />
            <Route path="/login" element={<LoginRoute />} />
            <Route path="/logout" element={<LogoutRoute />} />
            <Route path="/home" element={<PrivateRoute />}>
              <Route path="/home" element={<HomeRoute />} />
            </Route>
            <Route path="/todos" element={<PrivateRoute />}>
              <Route path="/todos" element={<TodosRoute />} />
            </Route>
            <Route path="/todo/:id" element={<PrivateRoute />}>
              <Route path="/todo/:id" element={<TodoRoute />} />
            </Route>
            <Route path="/todo" element={<PrivateRoute />}>
              <Route path="/todo" element={<TodoRoute />} />
            </Route>
            <Route path="*" element={<NotFoundRoute />} />
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
};

export default App;

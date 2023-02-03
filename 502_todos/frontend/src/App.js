import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import LoginRoute from './routes/LoginRoute';
import HomeRoute from './routes/HomeRoute';
import TodosRoute from './routes/TodosRoute';
import NotFoundRoute from './routes/NotFoundRoute';
import RootRoute from './routes/RootRoute';

const App = () => {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<RootRoute />}>
            <Route path="" element={<LoginRoute />} />
            <Route path="/login" element={<LoginRoute />} />
            <Route path="/home" element={<HomeRoute />} />
            <Route path="/todos" element={<TodosRoute />} />
            <Route path="*" element={<NotFoundRoute />} />
          </Route>
        </Routes>
      </Router>
    </>
  );
};

export default App;

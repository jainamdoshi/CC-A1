import './App.css';
import { Routes, Route } from 'react-router-dom';
import Login from './login';
import Signup from './signup';
import Home from './home';

function App() {
  return (
    <Routes>
      <Route path='/' element={<Home />}></Route>
      <Route path='/login' element={<Login />}></Route>
      <Route path='/register' element={<Signup />}></Route>
    </Routes>
  );
}

export default App;

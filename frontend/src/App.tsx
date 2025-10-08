import './App.css'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import RegisterForm from './components/RegisterForm'
import LoginForm from './components/LoginForm'

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/register" replace />} />
        <Route path="/register" element={<RegisterForm />} />
        <Route path="/login" element={<LoginForm />} />
        <Route path="*" element={<Navigate to="/register" replace />} />
      </Routes>
    </BrowserRouter>
  )
}

import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import MainLayout from './pages/MainLayout'
import Dashboard from './pages/Dashboard'
import Clients from './pages/Clients'
import ClientData from './pages/ClientData'
import Login from './pages/Login'
import React from "react";

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path='/' element={<Dashboard />} />
          <Route path='/dashboard' element={<Dashboard />} />
          <Route path='/clients' element={<Clients />} />
          <Route path='/clientData' element={<ClientData />} />
          <Route path='/clientData/:clientId' element={<ClientData />} />
          <Route path='/login' element={<Login />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App

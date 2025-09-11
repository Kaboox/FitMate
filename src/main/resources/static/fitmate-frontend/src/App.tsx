import { Routes, Route } from "react-router-dom";
import { NavbarProvider } from "./context/NavbarContext";
import Discover from "./pages/Discover";
import Register from "./pages/Register";
import Login from "./pages/Login";
import ExerciseDetails from "./pages/ExerciseDetails";
import { AuthProvider } from "./context/AuthContext";
import Profile from "./pages/Profile";

function App() {
  return (
    <AuthProvider>
      <NavbarProvider>
        <Routes>
          <Route path="/" element={<Discover />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/exercise/:id" element={<ExerciseDetails />} />
          <Route path="/profile" element={<Profile />} />
        </Routes>
      </NavbarProvider>
    </AuthProvider>
  );
}

export default App;

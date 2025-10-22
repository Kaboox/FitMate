import { Routes, Route } from "react-router-dom";
import { NavbarProvider } from "./context/NavbarContext";
import Discover from "./pages/Discover";
import Register from "./pages/Register";
import Login from "./pages/Login";
import ExerciseDetails from "./pages/ExerciseDetails";
import { AuthProvider } from "./context/AuthContext";
import Profile from "./pages/Profile";
import { UserProvider } from "./context/UserContext";
import Templates from "./pages/Templates";
import { TemplateProvider } from "./context/TemplateContext";
import TemplateDetails from "./pages/TemplateDetails";
import TemplateCreatePage from "./pages/TemplateCreatePage";
import { ExerciseProvider } from "./context/ExerciseContext";

function App() {
  return (
    <AuthProvider>
      <NavbarProvider>
        <UserProvider>
          <ExerciseProvider>
            <TemplateProvider>
              <Routes>
                <Route path="/" element={<Discover />} />
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
                <Route path="/exercise/:id" element={<ExerciseDetails />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/templates" element={<Templates />} />
                <Route path="/templates/:id" element={<TemplateDetails />} />
                <Route path="/templates/new" element={<TemplateCreatePage />} />
              </Routes>
            </TemplateProvider>
          </ExerciseProvider>
        </UserProvider>
      </NavbarProvider>
    </AuthProvider>
  );
}

export default App;

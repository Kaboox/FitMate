import {
  createContext,
  useContext,
  useEffect,
  useState,
  type ReactNode,
} from "react";

interface User {
  email: string;
  name: string;
  role: string;
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  register: (name: string, email: string, password: string) => Promise<void>;
  error: string | null;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    const storedUser = localStorage.getItem("user");
    setToken(storedToken);
    setUser(storedUser ? JSON.parse(storedUser) : null);
  }, []);

  const login = async (email: string, password: string) => {
    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        alert(`Login failed: ${errorData.message || "Unknown error"}`);
        console.error("Login error:", errorData);
        return;
      }

      const data = await response.json();
      setToken(data.token);
      setUser({ email: data.email, name: data.name, role: data.role });
      localStorage.setItem("token", data.token);
      localStorage.setItem(
        "user",
        JSON.stringify({ email: data.email, name: data.name, role: data.role })
      );
      setError(null);
      window.location.href = "/";
    } catch (err) {
      setError("Login failed");
      console.error("Login error:", err);
    }
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    window.location.href = "/login";
  };

  const register = async (name: string, email: string, password: string) => {
    try {
      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password }),
      });
      if (!response.ok) {
        const errorData = await response.json();
        alert(`Registration failed: ${errorData.message || "Unknown error"}`);
        console.error("Registration error:", errorData);
        return;
      }
      const data = await response.json();
      alert("Registration successful! Please log in.");
      window.location.href = "/login";
    } catch (err) {
      setError("Registration failed");
      console.error("Registration error:", err);
    }
  };

  return (
    <AuthContext.Provider
      value={{ user, token, login, logout, register, error }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
}

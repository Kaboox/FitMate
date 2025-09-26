import { createContext, useContext, useEffect, useState } from "react";

interface User {
  id: number;
  username: string;
  email: string;
  avatarUrl?: string;
  favorites: number[];
}

interface UserContextType {
  user: User | null;
  setUser: (user: User | null) => void;
  refreshUser: () => Promise<void>;
  toggleFavorites: (id: number) => Promise<void>;
  getFavorites: () => number[];
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);

  const getFavorites = () => {
    if (user) return user.favorites;
    return JSON.parse(localStorage.getItem("favorites") || "[]");
  };

  const toggleFavorites = async (id: number) => {
    // if user not logged in, use localStorage
    if (!user) {
      const favs = JSON.parse(localStorage.getItem("favorites") || "[]");
      if (favs.includes(id)) {
        const updated = favs.filter((f: number) => f !== id);
        localStorage.setItem("favorites", JSON.stringify(updated));
      } else {
        favs.push(id);
        localStorage.setItem("favorites", JSON.stringify(favs));
      }
      return;
    }
    
    // if user logged in, update on server
    const token = localStorage.getItem("token");

    try {
      const method = user.favorites.includes(id) ? "DELETE" : "POST";
      const res = await fetch(
        `http://localhost:8080/users/me/favorites/${id}`,
        {
          method,
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (!res.ok) throw new Error("Failed to update favorites");
      const updatedUser = await res.json();
      setUser(updatedUser);
    } catch (err) {
      console.error(err);
    }
  };

  const refreshUser = async () => {
    const token = localStorage.getItem("token");
    if (!token) return;

    try {
      const res = await fetch("http://localhost:8080/users/me", {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok) throw new Error("Failed to fetch user");
      const data = await res.json();
      setUser(data);
    } catch (err) {
      console.error(err);
      setUser(null);
    }
  };

  useEffect(() => {
    refreshUser();
  }, []);

  return (
    <UserContext.Provider
      value={{ user, setUser, refreshUser, toggleFavorites, getFavorites }}
    >
      {children}
    </UserContext.Provider>
  );
};

export const useUser = () => {
  const ctx = useContext(UserContext);
  if (!ctx) throw new Error("useUser must be used inside UserProvider");
  return ctx;
};

/* eslint-disable react-refresh/only-export-components */
import { createContext, useEffect, useState } from "react";

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

export const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider = ({ children }: { children: React.ReactNode }) => {
  
  // 1. To jest Twój adres backendu
  const API_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

  const [user, setUser] = useState<User | null>(null);
  const [localFavorites, setLocalFavorites] = useState<number[]>(() => {
    return JSON.parse(localStorage.getItem("favorites") || "[]");
  });

  // 2. NOWA FUNKCJA POMOCNICZA
  // Naprawia URL avatara zanim trafi do stanu aplikacji
  const processUser = (userData: User): User => {
    if (userData.avatarUrl && !userData.avatarUrl.startsWith("http")) {
      // Jeśli URL jest relatywny (np. /uploads/...), doklejamy domenę
      return {
        ...userData,
        avatarUrl: `${API_URL}${userData.avatarUrl}`,
      };
    }
    return userData;
  };

  const getFavorites = () => {
    if (user) return user.favorites;
    return localFavorites;
  };

  const toggleFavorites = async (id: number) => {
    if (!user) {
      setLocalFavorites((prev) => {
        let updated;
        if (prev.includes(id)) {
          updated = prev.filter((favId) => favId !== id);
        } else {
          updated = [...prev, id];
        }
        localStorage.setItem("favorites", JSON.stringify(updated));
        return updated;
      });
      return;
    }

    const token = localStorage.getItem("token");

    try {
      const method = user.favorites.includes(id) ? "DELETE" : "POST";
      const res = await fetch(
        `${API_URL}/users/me/favorites/${id}`,
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
      
      // 3. UŻYCIE POPRAWKI TUTAJ
      setUser(processUser(updatedUser)); 

    } catch (err) {
      console.error(err);
    }
  };

  const refreshUser = async () => {
    const token = localStorage.getItem("token");
    if (!token) return;

    try {
      const res = await fetch(`${API_URL}/users/me`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok) throw new Error("Failed to fetch user");
      
      const data = await res.json();
      
      // 4. I TUTAJ TEŻ
      setUser(processUser(data)); 

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
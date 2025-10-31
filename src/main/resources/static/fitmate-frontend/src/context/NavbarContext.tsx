/* eslint-disable react-refresh/only-export-components */
import { createContext,  useState, type ReactNode } from "react";

export type ActiveTab =
  | "discover"
  | "search"
  | "filter"
  | "favorites"
  | "profile"
  | "templates";


type NavbarContextType = {
  activeTab: ActiveTab;
  setActiveTab: (tab: ActiveTab) => void;
  toggleActiveTab: (tab: ActiveTab) => void;
  searchTerm: string;
  setSearchTerm: (term: string) => void;
  selectedCategory: string;
  setSelectedCategory: (cat: string) => void;

  favorites: number[];
  toggleFavorite: (id: number) => void;
};

export const NavbarContext = createContext<NavbarContextType | undefined>(undefined);

export function NavbarProvider({ children }: { children: ReactNode }) {
  const [activeTab, setActiveTab] = useState<ActiveTab>("discover");
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [favorites, setFavorites] = useState<number[]>(() => {
    const stored = localStorage.getItem("favorites");
    return stored ? JSON.parse(stored) : [];
  });

  const toggleActiveTab = (tab: ActiveTab) => {
    if (activeTab === tab) {
      // clicking the same tab resets to default
      setActiveTab("discover");
      setSearchTerm("");
      setSelectedCategory("");
    } else {
      setActiveTab(tab);
      // clear values when switching tabs
      if (tab !== "search") setSearchTerm("");
      if (tab !== "filter") setSelectedCategory("");
    }
  };

  const toggleFavorite = (id: number) => {
    setFavorites((prev) => {
      const newFavs = prev.includes(id)
        ? prev.filter((f) => f !== id)
        : [...prev, id];
      localStorage.setItem("favorites", JSON.stringify(newFavs));
      return newFavs;
    });
  };

  return (
    <NavbarContext.Provider
      value={{
        activeTab,
        setActiveTab,
        toggleActiveTab,
        searchTerm,
        setSearchTerm,
        selectedCategory,
        setSelectedCategory,
        favorites,
        toggleFavorite,
      }}
    >
      {children}
    </NavbarContext.Provider>
  );
}



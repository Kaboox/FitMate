import { createContext, useContext, useState, ReactNode } from "react";

type ActiveTab = "discover" | "search" | "filter" | "favorites" | "trending";

type NavbarContextType = {
    activeTab: ActiveTab;
    setActiveTab: (tab: ActiveTab) => void;
    toggleActiveTab: (tab: ActiveTab) => void;
    searchTerm: string;
    setSearchTerm: (term: string) => void;
    selectedCategory: string;
    setSelectedCategory: (cat: string) => void;
}


const NavbarContext = createContext<NavbarContextType | undefined>(undefined);

export function NavbarProvider({ children }: { children: ReactNode }) {
    const [activeTab, setActiveTab] = useState<ActiveTab>("discover");
    const [searchTerm, setSearchTerm] = useState("");
    const [selectedCategory, setSelectedCategory] = useState("");

    const toggleActiveTab = (tab: ActiveTab) => {
        if (activeTab === tab) {
        // kliknięcie drugi raz → wyłączamy + resetujemy wartości
        setActiveTab("discover");
        setSearchTerm("");
        setSelectedCategory("");
        } else {
        setActiveTab(tab);
        // jak przełączamy między zakładkami, też czyścimy
        if (tab !== "search") setSearchTerm("");
        if (tab !== "filter") setSelectedCategory("");
        }
    };

    

    return (
        <NavbarContext.Provider value={{activeTab, setActiveTab, toggleActiveTab, searchTerm, setSearchTerm, selectedCategory, setSelectedCategory}}>
            {children}
        </NavbarContext.Provider>
    )
}

export function useNavbar() {
  const ctx = useContext(NavbarContext);
  if (!ctx) throw new Error("useNavbar must be used within NavbarProvider");
  return ctx;
}
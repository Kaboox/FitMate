import { createContext, useContext, useState, ReactNode } from "react";

type ActiveTab = "discover" | "search" | "filter" | "favorites" | "trending";

type NavbarContextType = {
    activeTab: ActiveTab;
    setActiveTab: (tab: ActiveTab) => void;
    searchTerm: string;
    setSearchTerm: (term: string) => void;
}

const NavbarContext = createContext<NavbarContextType | undefined>(undefined);

export function NavbarProvider({ children }: { children: ReactNode }) {
    const [activeTab, setActiveTab] = useState<ActiveTab>("discover");
    const [searchTerm, setSearchTerm] = useState("");

    return (
        <NavbarContext.Provider value={{activeTab, setActiveTab, searchTerm, setSearchTerm}}>
            {children}
        </NavbarContext.Provider>
    )
}

export function useNavbar() {
  const ctx = useContext(NavbarContext);
  if (!ctx) throw new Error("useNavbar must be used within NavbarProvider");
  return ctx;
}
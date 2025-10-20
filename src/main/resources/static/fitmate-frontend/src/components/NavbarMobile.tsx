import { useState } from "react";
import {
  Menu,
  X,
  Search,
  Star,
  LayoutTemplate,
  ListFilter,
  User,
  LogOut,
  LogIn,
} from "lucide-react";
import { useNavbar } from "../context/NavbarContext";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const navItems = [
  { id: "search", icon: <Search size={24} />, label: "Discover" },
  { id: "filter", icon: <ListFilter size={24} />, label: "Category" },
  { id: "favorites", icon: <Star size={24} />, label: "Favorites" },
] as const;

export default function NavbarMobile() {
  const [isOpen, setIsOpen] = useState(false);
  const { activeTab, setActiveTab, toggleActiveTab } = useNavbar();
  const { user, logout } = useAuth();

  const handleClick = (option: string) => {
    toggleActiveTab(option);
    setIsOpen(false);
  };

  return (
    <div className="w-full sticky top-0 bg-neutral-800 px-4 py-3 flex justify-between items-center">
      {/* Logo */}
      <Link to="/" title="FitMate"><h1 className="text-xl font-mono text-white">FitMate</h1></Link>

      {/* Hamburger */}
      <button onClick={() => setIsOpen(true)} className="text-white">
        <Menu size={28} />
      </button>

      {/* Overlay */}
      {isOpen && (
        <div
          onClick={() => setIsOpen(false)}
          className="fixed inset-0 bg-black bg-opacity-50 z-40"
        />
      )}

      {/* Slide-out menu */}
      <div
        className={`fixed top-0 right-0 h-full w-2/3 bg-neutral-900 shadow-lg transform transition-transform duration-300 z-50 ${
          isOpen ? "translate-x-0" : "translate-x-full"
        }`}
      >
        {/* Header */}
        <div className="flex justify-between items-center px-4 py-3 border-b border-neutral-700">
          <h2 className="text-lg text-white font-mono">Menu</h2>
          <button onClick={() => setIsOpen(false)} className="text-white">
            <X size={28} />
          </button>
        </div>

        {/* Nav items */}
        <div className="flex flex-col items-center gap-6 py-10">
          {navItems.map((item) => (
            <div
              key={item.id}
              onClick={() => handleClick(item.id)}
              className={`p-2 cursor-pointer flex gap-2 items-center ${
                activeTab === item.id ? "text-green-400" : "text-white"
              }`}
            >
              {item.icon}
              <span className="text-sm">{item.label}</span>
            </div>
          ))}

          {/* Auth-related items */}
          <div className="border-t border-neutral-700 w-full pt-6 flex flex-col items-center gap-6">
            {user ? (
              <>
                <Link
                  to="/profile"
                  onClick={() => setIsOpen(false)}
                  className={`p-2 cursor-pointer flex gap-2 items-center ${
                    activeTab === "profile" ? "text-green-400" : "text-white"
                  }`}
                >
                  <User size={24} />
                  <span className="text-sm">Profile</span>
                </Link>
                <Link
                  to="/templates"
                  title="Temaplates"
                  onClick={() => setIsOpen(false)}
                  className={`p-2 cursor-pointer flex gap-2 items-center ${
                    activeTab === "templates" ? "text-green-400" : "text-white"
                  }`}
                >
                  <LayoutTemplate
                    size={24}
                    className="hover:text-green-300 transition cursor-pointer"
                  />
                  <span>Templates</span>
                </Link>
                <div
                  onClick={() => {
                    logout();
                    setIsOpen(false);
                  }}
                  className="p-2 cursor-pointer flex gap-2 items-center text-white hover:text-red-400"
                >
                  <LogOut size={24} />
                  <span className="text-sm">Logout</span>
                </div>
              </>
            ) : (
              <Link
                to="/login"
                onClick={() => setIsOpen(false)}
                className="p-2 cursor-pointer flex gap-2 items-center text-white"
              >
                <LogIn size={24} />
                <span className="text-sm">Login</span>
              </Link>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

import {
  ListFilter,
  Search,
  Star,
  LayoutTemplate,
  LogIn,
  LogOut,
  User,
} from "lucide-react";
import { useNavbar } from "../context/NavbarContext";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const navItems = [
  { id: "search", icon: <Search size={24} />, label: "Discover" },
  { id: "filter", icon: <ListFilter size={24} />, label: "Filter" },
  { id: "favorites", icon: <Star size={24} />, label: "Favorites" },
] as const;

export default function NavbarDesktop() {
  const { activeTab, setActiveTab, toggleActiveTab } = useNavbar();
  const { user, logout } = useAuth();

  return (
    <div className="flex flex-col justify-between items-center gap-8 w-full h-screen sticky top-0 py-10 bg-neutral-800 rounded-md">
      <Link to="/" title="FitMate"><h1 className="text-xl font-mono text-white">FitMate</h1></Link>

      <div className="flex flex-col gap-8">
        {navItems.map((item) => (
          <div
            key={item.id}
            onClick={() => toggleActiveTab(item.id)}
            className={`p-2 rounded-xl cursor-pointer transition ${
              activeTab === item.id
                ? "bg-neutral-700 text-green-400"
                : "text-white hover:text-green-300"
            }`}
          >
            {item.icon}
          </div>
        ))}
      </div>

      {!user && (
        <Link to="/login" title="Login">
          <div className="p-2 rounded-xl cursor-pointer text-white hover:text-green-300 transition">
            <LogIn size={24} />
          </div>
        </Link>
      )}

      {user && (
        <div className="p-2 flex flex-col gap-10 justify-center items-center rounded-xl text-white">
          <Link to="/profile" title="Profile">
            <User
              size={24}
              className="hover:text-green-300 transition cursor-pointer"
            />
          </Link>
          <Link to="/templates" title="Temaplates">
            <LayoutTemplate
              size={24}
              className="hover:text-green-300 transition cursor-pointer"
            />
          </Link>
          <LogOut
            size={24}
            className="hover:text-green-300 transition cursor-pointer"
            onClick={() => {
              if (confirm("Are you sure you want to log out?")) {
                window.location.href = "/login";
                logout();
              }
            }}
          />
        </div>
      )}
    </div>
  );
}

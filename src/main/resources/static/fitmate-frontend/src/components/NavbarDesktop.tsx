import { useState } from "react";
import { ListFilter, Search, Star, Flame } from "lucide-react"; // ✅ biblioteka ikon

const navItems = [
  { id: 1, icon: <Search size={24} />, label: "Discover" },
  { id: 2, icon: <ListFilter size={24} />, label: "Filter" },
  { id: 3, icon: <Star size={24} />, label: "Favorites" },
  { id: 4, icon: <Flame size={24} />, label: "Trending" },
];

export default function NavbarDesktop() {
  const [active, setActive] = useState(0);

  return (
    <div className="flex flex-col justify-between items-center gap-8 w-full h-screen sticky top-0 py-10 bg-neutral-800 rounded-md">
    
      <h1 className="text-xl font-mono text-white">FitMate</h1>

      
      <div className="flex flex-col gap-8">
        {navItems.map((item) => (
          <div
            key={item.id}
            onClick={() => setActive(item.id)}
            className={`p-2 rounded-xl cursor-pointer transition ${
              active === item.id
                ? "bg-neutral-700 text-green-400"
                : "text-white hover:text-green-300"
            }`}
          >
            {item.icon}
          </div>
        ))}
      </div>

      {/* Hidden for now (when logged in) */}
      <div className="hidden">{/* logged-in options */}</div>
    </div>
  );
}

import { Undo } from "lucide-react";
import NavbarDesktop from "../components/NavbarDesktop";
import NavbarMobile from "../components/NavbarMobile";
import TemplateList from "../components/TemplateList";
import { useNavigate } from "react-router-dom";

export default function Templates() {
  const navigate = useNavigate();

  return (
    <div className="relative flex flex-col md:flex-row bg-black w-full min-h-screen text-white">
      
      <div
        className="absolute top-4 left-4 flex items-center gap-2 text-xl md:text-2xl font-mono text-white cursor-pointer hover:text-green-400 transition"
        style={{ textShadow: "2px 2px 6px rgba(0,0,0,0.8)" }}
        onClick={() => navigate(-1)}
      >
        <Undo size={24} />
        <p>FitMate</p>
      </div>

      
      <div className="flex-1 pt-20 px-6">
        <TemplateList />
      </div>
    </div>
  );
}

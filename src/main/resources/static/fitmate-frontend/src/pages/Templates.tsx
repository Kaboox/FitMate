import { Undo } from "lucide-react";
import TemplateList from "../components/TemplateList";
import { useNavigate } from "react-router-dom";

export default function Templates() {
  const navigate = useNavigate();

  return (
    <div className="bg-black text-white min-h-screen flex flex-col p-6 md:p-12">
      <div
        className="flex items-center gap-2 font-mono text-white cursor-pointer hover:text-green-400 transition mb-8"
        style={{ textShadow: "2px 2px 6px rgba(0,0,0,0.8)" }}
        onClick={() => navigate("/")}
      >
        <Undo size={24} />
        <p className="text-lg">FitMate</p>
      </div>

      <div className="flex-1 flex justify-center">
        <div className="w-full max-w-5xl px-4">
          <h1 className="text-3xl font-bold mb-6">Workout Templates</h1>
          <button
            onClick={() => navigate("/templates/new")}
            className="bg-green-500 hover:bg-green-600 text-black font-semibold px-4 py-2 rounded-lg transition"
          >
            + Add Template
          </button>
          <TemplateList />
        </div>
      </div>
    </div>
  );
}

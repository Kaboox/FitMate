import TemplateForm from "../components/TemplateForm";
import { useNavigate } from "react-router-dom";
import { useTemplate } from "../hooks/useTemplate";


interface ExerciseInTemplate {
  exerciseId: number | null;
  sets: number;
  reps: number;
}


interface TemplatePayload {
  name: string;
  description?: string; 
  exercises: ExerciseInTemplate[];
}

export default function TemplateCreatePage() {

  const API_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const { fetchTemplates } = useTemplate();

  const handleSubmit = async (payload: TemplatePayload) => {
    await fetch(`${API_URL}/workout-template`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(payload),
    });
    await fetchTemplates();
    navigate("/templates");
  };

  return (
    <div className="bg-black text-white min-h-screen p-6 md:p-12">
      <TemplateForm
        title="Create New Template"
        onSubmit={handleSubmit}
        onCancel={() => navigate("/templates")}
      />
    </div>
  );
}

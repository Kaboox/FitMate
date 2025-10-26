import TemplateForm from "../components/TemplateForm";
import { useNavigate } from "react-router-dom";
import { useTemplate } from "../context/TemplateContext";

export default function TemplateCreatePage() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const { fetchTemplates } = useTemplate();

  const handleSubmit = async (payload: any) => {
    await fetch("http://localhost:8080/workout-template", {
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

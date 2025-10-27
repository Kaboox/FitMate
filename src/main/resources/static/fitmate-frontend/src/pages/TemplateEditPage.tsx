import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import TemplateForm from "../components/TemplateForm";
import { useTemplate } from "../context/TemplateContext";

export default function TemplateEditPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const { fetchTemplates, templates } = useTemplate();

  const [templateData, setTemplateData] = useState<any>(null);

  useEffect(() => {
    const found = templates.find((t) => t.id === Number(id));
    if (found) {
      console.log("found template in context:", found);
      setTemplateData(found);
    } else {
      fetch(`http://localhost:8080/workout-template/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => res.json())
        .then((data) => setTemplateData(data));
    }
  }, [id, templates]);

  const handleSubmit = async (payload: any) => {
    await fetch(`http://localhost:8080/workout-template/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(payload),
    });

    await fetchTemplates();
    navigate("/templates");
  };

  if (!templateData) return <div className="text-white p-6">Loading...</div>;

  return (
    <div className="bg-black text-white min-h-screen p-6 md:p-12">
      <TemplateForm
        title="Edit Template"
        initialValues={templateData}
        onSubmit={handleSubmit}
        onCancel={() => navigate("/templates")}
      />
    </div>
  );
}

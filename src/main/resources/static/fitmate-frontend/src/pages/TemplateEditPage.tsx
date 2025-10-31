import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import TemplateForm from "../components/TemplateForm";
import { useTemplate } from "../hooks/useTemplate";
import toast from "react-hot-toast";


interface ExerciseInTemplate {
  exerciseId: number;
  sets: number;
  reps: number;
}

interface TemplateData {
  id: number;
  name: string;
  description: string;
  exercises: ExerciseInTemplate[];
 
}


interface TemplatePayload {
  name: string;
  description?: string;
  exercises: ExerciseInTemplate[];
}


const API_URL = "http://localhost:8080";

export default function TemplateEditPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const { fetchTemplates, templates } = useTemplate();

 
  const [templateData, setTemplateData] = useState<TemplateData | null>(null);

  useEffect(() => {
    if (!id) {
        navigate("/templates");
        return;
    }

    const found = templates.find((t) => t.id === Number(id));
    if (found) {
      console.log("found template in context:", found);
      setTemplateData(found as TemplateData); 
      return;
    }

    
    if (!token) {
        navigate("/login");
        return;
    }
    
    
    const fetchTemplate = async () => {
        try {
            const res = await fetch(`${API_URL}/workout-template/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (!res.ok) {
                toast.error("Nie znaleziono szablonu lub sesja wygasła.");
                navigate("/templates");
                return;
            }

            const data: TemplateData = await res.json();
            setTemplateData(data);
        } catch (error) {
            console.error(error);
            toast.error("Błąd podczas pobierania danych szablonu.");
        }
    };

    fetchTemplate();
    
  }, [id, templates, token, navigate]);

  
  const handleSubmit = async (payload: TemplatePayload) => {
    if (!token || !id) return;

    try {
        const res = await fetch(`${API_URL}/workout-template/${id}`, { 
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(payload),
        });

        if (!res.ok) {
            const err = await res.json().catch(() => ({}));
            toast.error(`Błąd aktualizacji: ${err.message || res.statusText}`);
            return;
        }

        // Jeśli sukces:
        toast.success("Szablon zaktualizowany pomyślnie!");
        await fetchTemplates(); 
        navigate("/templates");

    } catch (error) {
        console.error(error);
        toast.error("Wystąpił nieoczekiwany błąd serwera.");
    }
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
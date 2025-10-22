import { useTemplate } from "../context/TemplateContext";
import { useNavigate } from "react-router-dom";

export interface WorkoutTemplateExercise {
  id: number;
  exerciseId: number;
  exerciseName: string;
  sets: number;
  reps: number;
}

export interface WorkoutTemplate {
  id: number;
  name: string;
  description: string;
  exercises: WorkoutTemplateExercise[];
}

const TemplateList = () => {
  const { templates } = useTemplate();

  const navigate = useNavigate();

  return (
    <div className="flex-1 p-4 text-white">
      <h2 className="text-2xl font-bold mb-4">Templates</h2>
      {templates.length === 0 ? (
        <p>No templates found.</p>
      ) : (
        templates.map((template) => (
          <div
            onClick={() => navigate(`/templates/${template.id}`)}
            key={template.id}
            className="border border-neutral-700 rounded-lg p-4 mb-4 bg-neutral-900 hover:bg-neutral-800 transition cursor-pointer"
          >
            <h3 className="text-xl font-semibold mb-2">{template.name}</h3>
            <p className="text-gray-400">{template.description}</p>
          </div>
        ))
      )}
    </div>
  );
};

export default TemplateList;

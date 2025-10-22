import { Undo, Trash2, Edit3 } from "lucide-react";
import { useNavigate, useParams } from "react-router-dom";
import { useTemplate } from "../context/TemplateContext";
import { useEffect } from "react";

export default function TemplateDetails() {
  const { id } = useParams<{ id: string }>();
  const { templateDetails, fetchTemplateDetails, deleteTemplate } =
    useTemplate();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      fetchTemplateDetails(Number(id));
    }
  }, [id]);

  return (
    <div className="bg-black text-white min-h-screen flex flex-col p-6 md:p-12">
      <div
        className="flex items-center gap-2 font-mono text-white cursor-pointer hover:text-green-400 transition mb-8"
        style={{ textShadow: "2px 2px 6px rgba(0,0,0,0.8)" }}
        onClick={() => navigate(-1)}
      >
        <Undo size={24} />
        <p className="text-lg">FitMate</p>
      </div>

      <div className="flex-1 flex justify-center">
        <div className="w-full max-w-5xl px-4">
          <div className="flex justify-between items-center mb-8">
            <h1 className="text-3xl font-bold">{templateDetails?.name}</h1>
            <div className="flex gap-4">
              <button className="hover:text-yellow-400 transition">
                <Edit3 size={24} />
              </button>
              <button
                className="hover:text-red-500 transition"
                onClick={async () => {
                  if (templateDetails) {
                    await deleteTemplate(templateDetails.id);
                  }
                }}
              >
                <Trash2 size={24} />
              </button>
            </div>
          </div>

          <p className="text-gray-400 mb-6">{templateDetails?.description}</p>

          <div className="overflow-x-auto">
            <table className="w-full border border-gray-700 rounded-lg overflow-hidden">
              <thead className="bg-gray-800">
                <tr>
                  <th className="p-3 text-left">#</th>
                  <th className="p-3 text-left">Exercise</th>
                  <th className="p-3 text-center">Sets</th>
                  <th className="p-3 text-center">Reps</th>
                </tr>
              </thead>
              <tbody>
                {templateDetails?.exercises.map((ex, i) => (
                  <tr
                    key={ex.id}
                    className="border-t border-gray-700 hover:bg-gray-800 transition"
                  >
                    <td className="p-3">{i + 1}</td>
                    <td className="p-3">{ex.exerciseName}</td>
                    <td className="p-3 text-center">{ex.sets}</td>
                    <td className="p-3 text-center">{ex.reps}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="flex justify-center mt-10 gap-4">
            <button className="bg-green-500 hover:bg-green-600 text-black font-semibold px-6 py-2 rounded-lg transition">
              Start Workout
            </button>
            <button className="bg-gray-700 hover:bg-gray-600 px-6 py-2 rounded-lg transition">
              Save As New
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

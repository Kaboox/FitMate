import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Plus, Trash2, Undo } from "lucide-react";
import { useExercises } from "../context/ExerciseContext";

interface ExerciseInput {
  id: number;
  exerciseId: number | null;
  sets: number;
  reps: number;
  search: string;
}

export default function TemplateCreatePage() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [exercises, setExercises] = useState<ExerciseInput[]>([]);
  const { exercises: allExercises } = useExercises();
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const addExercise = () => {
    setExercises([
      ...exercises,
      { id: Date.now(), exerciseId: null, sets: 3, reps: 10, search: "" },
    ]);
  };

  const removeExercise = (id: number) => {
    setExercises(exercises.filter((ex) => ex.id !== id));
  };

  const handleChange = (
    id: number,
    field: keyof ExerciseInput,
    value: string | number | null
  ) => {
    setExercises((prev) =>
      prev.map((ex) => (ex.id === id ? { ...ex, [field]: value } : ex))
    );
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const payload = {
      name,
      description,
      exercises: exercises.map((ex) => ({
        exerciseId: ex.exerciseId,
        sets: ex.sets,
        reps: ex.reps,
      })),
    };

    try {
      const response = await fetch("http://localhost:8080/workout-template", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        navigate("/templates");
      } else {
        console.error("Failed to create template:", await response.text());
      }
    } catch (error) {
      console.error("Error creating template:", error);
    }
  };

  return (
    <div className="bg-black text-white min-h-screen p-6 md:p-12">
      {/* Header */}
      <div className="flex items-center justify-between mb-8">
        <button
          onClick={() => navigate(-1)}
          className="flex items-center gap-2 text-gray-300 hover:text-green-400 transition"
        >
          <Undo size={20} /> Back
        </button>
        <h1 className="text-3xl font-bold">Create New Template</h1>
        <div />
      </div>

      {/* Form */}
      <form
        onSubmit={handleSubmit}
        className="max-w-3xl mx-auto bg-neutral-900 p-6 rounded-xl shadow-lg"
      >
        {/* Name */}
        <div className="mb-4">
          <label className="block mb-2 font-semibold">Template Name</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
            className="w-full p-2 rounded bg-neutral-800 text-white border border-neutral-700 focus:border-green-400 outline-none"
          />
        </div>

        {/* Description */}
        <div className="mb-6">
          <label className="block mb-2 font-semibold">Description</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows={3}
            className="w-full p-2 rounded bg-neutral-800 text-white border border-neutral-700 focus:border-green-400 outline-none resize-none"
          />
        </div>

        {/* List of exercises */}
        <div className="mb-6">
          <div className="flex justify-between items-center mb-3">
            <h2 className="text-xl font-semibold">Exercises</h2>
            <button
              type="button"
              onClick={addExercise}
              className="flex items-center gap-2 bg-green-500 hover:bg-green-600 text-black font-semibold px-3 py-1 rounded-lg transition"
            >
              <Plus size={18} /> Add Exercise
            </button>
          </div>

          {exercises.length === 0 && (
            <p className="text-gray-400">No exercises added yet.</p>
          )}

          {exercises.map((ex) => {
            const filtered = allExercises.filter((e) =>
              e.name.toLowerCase().includes(ex.search.toLowerCase())
            );

            return (
              <div
                key={ex.id}
                className="flex flex-col md:flex-row md:items-center gap-3 bg-neutral-800 p-3 rounded-lg mb-3"
              >
                {/* Search and select */}
                <div className="flex-1">
                  <input
                    type="text"
                    placeholder="Search exercise..."
                    value={ex.search}
                    onChange={(e) =>
                      handleChange(ex.id, "search", e.target.value)
                    }
                    className="w-full p-2 mb-2 rounded bg-neutral-900 text-white border border-neutral-700"
                  />
                  <select
                    value={ex.exerciseId ?? ""}
                    onChange={(e) =>
                      handleChange(ex.id, "exerciseId", Number(e.target.value))
                    }
                    className="w-full p-2 rounded bg-neutral-900 text-white border border-neutral-700"
                  >
                    <option value="">Select exercise</option>
                    {filtered.map((exercise) => (
                      <option key={exercise.id} value={exercise.id}>
                        {exercise.name}
                      </option>
                    ))}
                  </select>
                </div>

                {/* Sets/Reps */}
                <input
                  type="number"
                  min={1}
                  value={ex.sets}
                  onChange={(e) =>
                    handleChange(ex.id, "sets", Number(e.target.value))
                  }
                  className="w-20 text-center p-2 rounded bg-neutral-900 text-white border border-neutral-700"
                />
                <input
                  type="number"
                  min={1}
                  value={ex.reps}
                  onChange={(e) =>
                    handleChange(ex.id, "reps", Number(e.target.value))
                  }
                  className="w-20 text-center p-2 rounded bg-neutral-900 text-white border border-neutral-700"
                />

                {/* Delete */}
                <button
                  type="button"
                  onClick={() => removeExercise(ex.id)}
                  className="text-red-500 hover:text-red-400 transition"
                >
                  <Trash2 size={22} />
                </button>
              </div>
            );
          })}
        </div>

        <div className="flex justify-end gap-4">
          <button
            type="button"
            onClick={() => navigate("/templates")}
            className="bg-gray-700 hover:bg-gray-600 px-6 py-2 rounded-lg transition"
          >
            Cancel
          </button>
          <button
            type="submit"
            className="bg-green-500 hover:bg-green-600 text-black font-semibold px-6 py-2 rounded-lg transition"
          >
            Save Template
          </button>
        </div>
      </form>
    </div>
  );
}

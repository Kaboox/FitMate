import { useState } from "react";
import { Plus, Trash2 } from "lucide-react";
import { useExercises } from "../context/ExerciseContext";

interface ExerciseInput {
  id: number;
  exerciseId: number | null;
  sets: number;
  reps: number;
  search: string;
}

interface TemplateFormProps {
  initialName?: string;
  initialDescription?: string;
  initialExercises?: ExerciseInput[];
  onSubmit: (data: {
    name: string;
    description: string;
    exercises: { exerciseId: number | null; sets: number; reps: number }[];
  }) => void;
  onCancel: () => void;
  title: string;
  initialValues?: any;
}

export default function TemplateForm({
  initialName = "",
  initialDescription = "",
  initialExercises = [],
  onSubmit,
  onCancel,
  title,
  initialValues
}: TemplateFormProps) {
  const [name, setName] = useState(initialName);
  const [description, setDescription] = useState(initialDescription);
  //const [exercises, setExercises] = useState<ExerciseInput[]>(initialExercises);
  const { exercises: allExercises } = useExercises();
  const [templateName, setTemplateName] = useState(initialValues?.name ?? "");
  const [exercises, setExercises] = useState(initialValues?.exercises ?? []);

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

  const submit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({
      name,
      description,
      exercises: exercises.map((ex) => ({
        exerciseId: ex.exerciseId,
        sets: ex.sets,
        reps: ex.reps,
      })),
    });
  };

  return (
    <form
      onSubmit={submit}
      className="max-w-3xl mx-auto bg-neutral-900 p-6 rounded-xl shadow-lg"
    >
      <h1 className="text-3xl font-bold mb-6 text-center">{title}</h1>

      {/* Name */}
      <div className="mb-4">
        <label className="block mb-2 font-semibold">Template Name</label>
        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
          className="w-full p-2 rounded bg-neutral-800 text-white border border-neutral-700"
        />
      </div>

      {/* Description */}
      <div className="mb-6">
        <label className="block mb-2 font-semibold">Description</label>
        <textarea
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          rows={3}
          className="w-full p-2 rounded bg-neutral-800 text-white border border-neutral-700 resize-none"
        />
      </div>

      {/* Exercises */}
      <div className="mb-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold">Exercises</h2>
          <button
            type="button"
            onClick={addExercise}
            className="flex items-center gap-2 bg-green-500 hover:bg-green-600 text-black px-3 py-1 rounded-lg"
          >
            <Plus size={18} /> Add
          </button>
        </div>

        {exercises.map((ex) => {
          const filtered = allExercises.filter((e) =>
            e.name.toLowerCase().includes(ex.search.toLowerCase())
          );

          return (
            <div
              key={ex.id}
              className="flex flex-col md:flex-row gap-3 bg-neutral-800 p-3 rounded-lg mb-3"
            >
              <div className="flex-1">
                <input
                  placeholder="Search..."
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

              <input
                type="number"
                min={1}
                value={ex.sets}
                onChange={(e) =>
                  handleChange(ex.id, "sets", Number(e.target.value))
                }
                className="w-20 text-center p-2 rounded bg-neutral-900 border border-neutral-700"
              />

              <input
                type="number"
                min={1}
                value={ex.reps}
                onChange={(e) =>
                  handleChange(ex.id, "reps", Number(e.target.value))
                }
                className="w-20 text-center p-2 rounded bg-neutral-900 border border-neutral-700"
              />

              <button
                type="button"
                onClick={() => removeExercise(ex.id)}
                className="text-red-500 hover:text-red-400"
              >
                <Trash2 size={20} />
              </button>
            </div>
          );
        })}
      </div>

      <div className="flex justify-end gap-4">
        <button
          type="button"
          onClick={onCancel}
          className="bg-gray-700 hover:bg-gray-600 px-6 py-2 rounded-lg"
        >
          Cancel
        </button>
        <button
          type="submit"
          className="bg-green-500 hover:bg-green-600 text-black px-6 py-2 rounded-lg"
        >
          Save
        </button>
      </div>
    </form>
  );
}

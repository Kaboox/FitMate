import  { useEffect, useState } from "react";
import {  useNavigate, useParams } from "react-router-dom";
import { Undo } from "lucide-react";

interface Muscle {
  id: number;
  name: string;
  category: string;
}

interface Exercise {
  id: number;
  name: string;
  description: string;
  videoUrl: string;
  imageUrl: string;
  primaryMuscle: Muscle;
  secondaryMuscles: Muscle[];
  mistakes: string[];
}

export default function ExerciseDetail() {

  const API_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  const [exerciseData, setExerciseData] = useState<Exercise | null>(null);

  useEffect(() => {
    if (!id) return;

    fetch(`${API_URL}/exercises/${id}`)
      .then((response) => response.json())
      .then((data) => setExerciseData(data))
      .catch((error) => console.error("Error fetching exercise data:", error));
    document.title = "Exercise Details - FitMate";
  }, [id]);

  if (!exerciseData) {
    return (
      <div className="flex items-center justify-center min-h-screen text-white">
        Loading exercise...
      </div>
    );
  }

  return (
    <div className="flex flex-col items-center bg-neutral-900 min-h-screen text-white">
      {/* Hero section */}
      <div className="relative w-full h-64 md:h-96">
        <img
          src={exerciseData.imageUrl}
          alt={exerciseData.name}
          className="w-full h-full object-cover"
        />
        <div>
          <p
            className="absolute top-4 left-4 flex items-center gap-2 text-xl md:text-2xl font-mono text-white cursor-pointer hover:text-green-400 transition"
            style={{ textShadow: "2px 2px 6px rgba(0,0,0,0.8)" }}
            onClick={() => navigate(-1)}
          >
            <Undo size={24} />
            FitMate
          </p>
        </div>
        <div className="absolute bottom-4 left-6 bg-black bg-opacity-60 px-4 py-2 rounded-lg">
          <h1 className="text-3xl md:text-5xl font-bold">
            {exerciseData.name}
          </h1>
          <h3 className="text-xl md:text-2xl font-bold">
            {exerciseData.primaryMuscle.category}
          </h3>
        </div>
      </div>

      {/* Muscles */}
      <div className="mt-6 flex gap-2 flex-wrap justify-center">
        {exerciseData.secondaryMuscles.map((muscle, idx) => (
          <span
            key={idx}
            className="bg-green-600 px-3 py-1 rounded-full text-sm font-medium"
          >
            {muscle.name}
          </span>
        ))}
      </div>

      {/* Description */}
      <div className="max-w-3xl w-full px-6 mt-8 space-y-4">
        <h2 className="text-2xl font-semibold">Description</h2>
        <p className="text-gray-300">{exerciseData.description}</p>

        <h3 className="text-xl font-semibold mt-6">Common mistakes</h3>
        <ul className="list-disc list-inside text-gray-400">
          {exerciseData.mistakes.map((m, idx) => (
            <li key={idx}>{m}</li>
          ))}
        </ul>
      </div>

      {/* Video */}
      <div className="mt-10 w-full max-w-3xl px-6">
        <h2 className="text-2xl font-semibold mb-4">Video Tutorial</h2>
        <div className="aspect-video">
          <iframe
            width="100%"
            height="100%"
            src={exerciseData.videoUrl}
            title="Exercise tutorial"
            allowFullScreen
            className="rounded-lg"
          ></iframe>
        </div>
      </div>

      {/* CTA - future feature */}
      <div className="mt-10 mb-16">
        <button className="bg-green-600 hover:bg-green-700 px-6 py-3 rounded-lg font-bold">
          Add to Workout Plan
        </button>
      </div>
    </div>
  );
}

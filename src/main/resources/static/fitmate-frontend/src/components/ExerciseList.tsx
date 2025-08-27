import { useNavbar } from "../context/NavbarContext";
import type { Exercise } from "../types/Exercise";
import ExerciseCard from "./ExerciseCard";
import { useEffect, useState } from "react";


export default function ExerciseList() {
  const [exercises, setExercises] = useState<Exercise[]>([]);
  const { activeTab, searchTerm, setSearchTerm } = useNavbar();

  useEffect(() => {
    fetch("http://localhost:8080/exercises")
      .then((res) => res.json())
      .then((data) => setExercises(data))
      .catch((err) => console.log("Błąd przy pobieraniu danych", err));
  }, [])

  const filtered = exercises.filter((ex) => 
    ex.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    ex.description.toLowerCase().includes(searchTerm.toLowerCase())
  )

  return (
    <div className="flex flex-col gap-10 overflow-y-auto p-6">
      {activeTab === "search" && (
        <div>
        <input type="text" 
          placeholder="Szukaj ćwiczenia..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full p-2 rounded-md bg-neutral-800 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-green-500"
        />
      </div>
      )}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {filtered.map((ex) => (
          <ExerciseCard
            key={ex.id}
            name={ex.name}
            category={ex.primaryMuscle?.name || "Brak"}
            description={ex.description}
            imageUrl={ex.imageUrl}
          />
        ))}
      </div>
    </div>
  )
}

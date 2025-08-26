import type { Exercise } from "../types/Exercise";
import ExerciseCard from "./ExerciseCard";
import { useEffect, useState } from "react";


export default function ExerciseList() {
  const [exercises, setExercises] = useState<Exercise[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/exercises")
      .then((res) => res.json())
      .then((data) => setExercises(data))
      .catch((err) => console.log("Błąd przy pobieraniu danych", err));
  }, [])

  return (
     <div className="flex-1 overflow-y-auto p-6">
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      {exercises.map((ex) => (
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

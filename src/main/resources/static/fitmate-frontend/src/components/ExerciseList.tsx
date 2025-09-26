import { useNavbar } from "../context/NavbarContext";
import { useUser } from "../context/UserContext";
import type { Exercise } from "../types/Exercise";
import ExerciseCard from "./ExerciseCard";
import { useEffect, useState } from "react";

export default function ExerciseList() {
  const [exercises, setExercises] = useState<Exercise[]>([]);
  const {
    activeTab,
    searchTerm,
    setSearchTerm,
    selectedCategory,
    setSelectedCategory,
  } = useNavbar();
  const { getFavorites } = useUser();
  const favorites = getFavorites();


  useEffect(() => {
    fetch("http://localhost:8080/exercises")
      .then((res) => res.json())
      .then((data) => setExercises(data))
      .catch((err) => console.log("Error while fetching data", err));
  }, []);

  const filtered = exercises.filter((ex) => {
    const matchesSearch =
      ex.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      ex.description.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesCategory =
      selectedCategory === "" ||
      ex.primaryMuscle?.category === selectedCategory;

    const matchesFavorites =
      activeTab !== "favorites" || favorites.includes(ex.id);

    return matchesSearch && matchesCategory && matchesFavorites;
  });

  return (
    <div className="flex flex-col gap-10 overflow-y-auto p-6">
      {activeTab === "search" && (
        <div>
          <input
            type="text"
            placeholder="Search exercises..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full p-2 rounded-md bg-neutral-800 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-green-500"
          />
        </div>
      )}
      {activeTab === "filter" && (
        <div>
          <select
            name="category"
            value={selectedCategory}
            onChange={(e) => setSelectedCategory(e.target.value)}
            id="category"
            className="w-full p-2 rounded-md bg-neutral-800 text-white 
                 focus:outline-none focus:ring-2 focus:ring-green-500
                 border border-neutral-700"
          >
            <option value="">All</option>
            <option value="Chest">Chest</option>
            <option value="Back">Back</option>
            <option value="Arms">Arms</option>
            <option value="Shoulders">Shoulders</option>
            <option value="Legs">Legs</option>
            <option value="Core">Core</option>
          </select>
        </div>
      )}

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {filtered.map((ex) => (
          <ExerciseCard
            id={ex.id}
            key={ex.id}
            name={ex.name}
            category={ex.primaryMuscle?.name || "Brak"}
            muscleCategory={ex.primaryMuscle?.category || "Brak"}
            description={ex.description}
            imageUrl={ex.imageUrl}
          />
        ))}
      </div>
    </div>
  );
}

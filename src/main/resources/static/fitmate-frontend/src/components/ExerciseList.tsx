import ExerciseCard from "./ExerciseCard";

const mockExercises = [
  {
    name: "Bench Press",
    category: "Klatka",
    description: "Klasyczne ćwiczenie na rozwój mięśni klatki piersiowej.",
    imageUrl: "https://via.placeholder.com/150",
  },
  {
    name: "Deadlift",
    category: "Plecy",
    description: "Jedno z najbardziej kompleksowych ćwiczeń na całe ciało.",
    imageUrl: "https://via.placeholder.com/150",
  },
];

export default function ExerciseList() {
  return (
    <div className="flex flex-col gap-4">
      {mockExercises.map((ex, i) => (
        <ExerciseCard key={i} {...ex} />
      ))}
    </div>
  );
}

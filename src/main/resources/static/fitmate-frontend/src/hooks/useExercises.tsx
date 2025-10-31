import { useContext } from "react";
import { ExerciseContext } from "../context/ExerciseContext";

export function useExercises() {
  const ctx = useContext(ExerciseContext);
  if (!ctx) throw new Error("useExercise must be used within ExerciseProvider");
  return ctx;
}

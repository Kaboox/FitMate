/* eslint-disable react-refresh/only-export-components */
import { createContext, useCallback, useEffect, useState } from "react";

interface Exercise {
  id: number;
  name: string;
  category?: string;
  videoUrl?: string;
}

interface ExerciseContextType {
  exercises: Exercise[];
  loading: boolean;
  refreshExercises: () => void;
}

export const ExerciseContext = createContext<ExerciseContextType>({
  exercises: [],
  loading: false,
  refreshExercises: () => {},
});



export const ExerciseProvider = ({ children }: { children: React.ReactNode }) => {
  const [exercises, setExercises] = useState<Exercise[]>([]);
  const [loading, setLoading] = useState(true);

  const token = localStorage.getItem("token");

  const API_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

  const fetchExercises = useCallback(async () => {
    if (!token) {
      setLoading(false);
      setExercises([]); 
      return;
    }

    setLoading(true);
    try {
      const res = await fetch(`${API_URL}/exercises`, { 
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok) {
        throw new Error("Failed to fetch exercises");
      }
      const data = await res.json();
      setExercises(data);
    } catch (err) {
      console.error("Error fetching exercises:", err);
      setExercises([]); 
    } finally {
      setLoading(false);
    }
  }, [token]); 

  useEffect(() => {
    fetchExercises();
  }, [fetchExercises]);

  return (
    <ExerciseContext.Provider
      value={{ exercises, loading, refreshExercises: fetchExercises }}
    >
      {children}
    </ExerciseContext.Provider>
  );
};

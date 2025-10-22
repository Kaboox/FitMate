import { createContext, useContext, useEffect, useState } from "react";

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

const ExerciseContext = createContext<ExerciseContextType>({
  exercises: [],
  loading: false,
  refreshExercises: () => {},
});

export const useExercises = () => useContext(ExerciseContext);

export const ExerciseProvider = ({ children }: { children: React.ReactNode }) => {
  const [exercises, setExercises] = useState<Exercise[]>([]);
  const [loading, setLoading] = useState(true);

  const token = localStorage.getItem("token");

  const fetchExercises = async () => {
    setLoading(true);
    try {
      const res = await fetch("http://localhost:8080/exercises", {
        headers: { Authorization: `Bearer ${token}` },
      });
      const data = await res.json();
      setExercises(data);
    } catch (err) {
      console.error("Error fetching exercises:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchExercises();
  }, []);

  return (
    <ExerciseContext.Provider
      value={{ exercises, loading, refreshExercises: fetchExercises }}
    >
      {children}
    </ExerciseContext.Provider>
  );
};

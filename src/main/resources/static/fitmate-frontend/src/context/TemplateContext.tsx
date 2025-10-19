import { createContext, useContext, useEffect, useState } from "react";
import { useUser } from "./UserContext";

export interface WorkoutTemplateExercise {
  id: number;
  exerciseId: number;
  exerciseName: string;
  sets: number;
  reps: number;
}

export interface WorkoutTemplate {
  id: number;
  name: string;
  description: string;
  exercises: WorkoutTemplateExercise[];
}

export interface TemplateContextType {
  templates: WorkoutTemplate[];
  fetchTemplates: () => Promise<void>;
}

const TemplateContext = createContext<TemplateContextType | undefined>(
  undefined
);

export const TemplateProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  
  const { user } = useUser();

  const [templates, setTemplates] = useState<WorkoutTemplate[]>([]);

  const token = localStorage.getItem("token");

  const fetchTemplates = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/workout-template/me",
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      const data = await response.json();
      setTemplates(data);
    } catch (error) {
      console.error("Error fetching templates:", error);
    }
  };

  useEffect(() => {
    fetchTemplates();
  }, []);

  return (
    <TemplateContext.Provider value={{templates, fetchTemplates}}>
      {children}
    </TemplateContext.Provider>
  );
};

export const useTemplate = () => {
  const ctx = useContext(TemplateContext);
  if (!ctx) throw new Error("useTemplate must be used inside TemplateProvider");
  return ctx;
};

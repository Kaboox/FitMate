/* eslint-disable react-refresh/only-export-components */
import { createContext, useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import toast from "react-hot-toast";

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
  templateDetails: WorkoutTemplate | null;
  fetchTemplates: () => Promise<void>;
  fetchTemplateDetails: (templateId: number) => Promise<void>;
  deleteTemplate: (templateId: number) => Promise<void>;
}

export const TemplateContext = createContext<TemplateContextType | undefined>(
  undefined
);

export const TemplateProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  

  const [templates, setTemplates] = useState<WorkoutTemplate[]>([]);

  const [templateDetails, setTemplateDetails] =
    useState<WorkoutTemplate | null>(null);

  const token = localStorage.getItem("token");

  const API_URL = "http://localhost:8080";

  const navigate = useNavigate();

  const fetchTemplates = useCallback(async () => {
    if (!token) { 
        setTemplates([]);
        return;
    }
    try {
      const response = await fetch(
        `localhost:8080/workout-template/me`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (!response.ok) throw new Error("Failed to fetch templates");
      const data = await response.json();
      setTemplates(data);
    } catch (error) {
      console.error("Error fetching templates:", error);
      toast.error("Could not load templates.");
    }
  }, [token]);

  const fetchTemplateDetails = useCallback(async (templateId: number) => {
    if (!token) return;
    try {
      const response = await fetch(
        `${API_URL}/workout-template/${templateId}`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
       if (!response.ok) throw new Error("Failed to fetch details");
      const data = await response.json();
      console.log("Fetched template details:", data);
      setTemplateDetails(data);
    } catch (error) {
      console.error("Error fetching template details:", error);
      toast.error("Could not load template details.");
    }
  }, [token]); 

  const deleteTemplate = useCallback(async (templateId: number) => {
    if (!token) return;
    try {
      const response = await fetch(`${API_URL}/workout-template/${templateId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        toast.error("Failed to delete template.");
        console.error("Failed to delete template:", response.status);
        return;
      }

      toast.success("Template deleted.");
      await fetchTemplates(); 
      navigate("/templates");

    } catch (error) {
      console.error("Error deleting template:", error);
      toast.error("An error occurred while deleting.");
    }
  }, [token, fetchTemplates, navigate]); 

  useEffect(() => {
    fetchTemplates();
  }, [fetchTemplates]);

  return (
    <TemplateContext.Provider
      value={{
        templates,
        fetchTemplates,
        templateDetails,
        fetchTemplateDetails,
        deleteTemplate
      }}
    >
      {children}
    </TemplateContext.Provider>
  );
};

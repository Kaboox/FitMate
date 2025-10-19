import { useEffect, useState } from "react";
import { useTemplate } from "../context/TemplateContext";

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

const TemplateList = () => {

    const {templates} = useTemplate();

        






  return (
    <div className="flex-1 p-4 text-white">
      <h2 className="text-2xl font-bold mb-4">Templates</h2>
      {templates.map((template) => (
        <div
          key={template.id}
            className="border border-neutral-700 rounded-lg p-4 mb-4 bg-neutral-900"
        >
          <h3 className="text-xl font-semibold mb-2">{template.name}</h3>
          <p className="mb-4">{template.description}</p>
    </div>))}
    </div>
  );
};

export default TemplateList;

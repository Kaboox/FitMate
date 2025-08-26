export type Muscle = {
  id: number;
  name: string;
};

export type Exercise = {
  id: number;
  name: string;
  description: string;
  videoUrl: string;
  imageUrl: string;
  primaryMuscle?: Muscle;
  secondaryMuscles?: Muscle[];
};

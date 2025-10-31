import { Heart } from "lucide-react";
import { Link } from "react-router-dom";
import { useUser } from "../hooks/useUser";

type ExerciseCardProps = {
  id: number;
  name: string;
  category: string;
  muscleCategory: string;
  description: string;
  imageUrl: string;
};

export default function ExerciseCard({
  id,
  name,
  category,
  description,
  imageUrl,
}: ExerciseCardProps) {
  const { toggleFavorites } = useUser();
  const { getFavorites } = useUser();
  const favorites = getFavorites();
  const isFav = favorites.includes(id);

  return (
    <div className="max-w-xl bg-neutral-800 text-white rounded-xl shadow-md p-4 flex gap-4 hover:bg-neutral-700 transition relative">
      {/* Cover image */}
      <img
        src={imageUrl}
        alt={name}
        className="w-24 h-24 object-cover rounded-lg"
      />

      {/* Text */}
      <div className="flex flex-col justify-between flex-1">
        <div>
          <h3 className="text-lg font-semibold">{name}</h3>
          <p className="text-sm text-green-400">{category}</p>
          <p className="text-xs text-gray-300 mt-2 line-clamp-2">
            {description}
          </p>
        </div>

        <Link to={`/exercise/${id}`}>
          <button className="mt-2 text-sm text-green-400 hover:underline self-start">
            Details
          </button>
        </Link>
      </div>

      {/* Heart Icon */}
      <button
        onClick={() => toggleFavorites(id)}
        className="absolute top-2 right-2 text-red-400"
      >
        <Heart
          size={20}
          className={isFav ? "fill-red-500" : "hover:fill-red-400"}
        />
      </button>
    </div>
  );
}
